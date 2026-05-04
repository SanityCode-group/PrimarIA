import os
import json
import pandas as pd
import mysql.connector
from dotenv import load_dotenv
from huggingface_hub import login
from datasets import load_dataset, concatenate_datasets


class CasosClinicosPipeline:

    def __init__(self):
        load_dotenv()

        # ===== HF =====
        self.hf_token = os.getenv("HF_TOKEN")
        if not self.hf_token:
            raise ValueError("Falta HF_TOKEN")
        login(self.hf_token)

        # ===== DB =====
        self.conn = mysql.connector.connect(
            host=os.getenv("DB_HOST"),
            port=os.getenv("DB_PORT"),
            user=os.getenv("DB_USER"),
            password=os.getenv("DB_PASS"),
            database=os.getenv("DB_NAME")
        )
        self.cursor = self.conn.cursor()

        self.df = None

    # =========================
    # 1. CARGAR DATASET
    # =========================
    def fetch_dataset(self):
        dataset_dict = load_dataset("ilopezmon/casos_clinicos_completos")

        all_splits = [split for split in dataset_dict.values()]
        dataset = concatenate_datasets(all_splits)

        self.df = dataset.to_pandas()

        # limpiar nombres raros
        self.df.columns = [str(c).replace("b'", "").replace("'", "") for c in self.df.columns]

        # 🔥 MAPEO CRÍTICO: _id → id (TU BD)
        if "_id" in self.df.columns:
            self.df["id"] = self.df["_id"]
            self.df.drop(columns=["_id"], inplace=True)

        # asegurar agente (tu tabla usa "agente")
        if "agente" not in self.df.columns:
            self.df["agente"] = "HuggingFace"

        print(f"Dataset cargado: {len(self.df)} filas")

    # =========================
    # 2. LIMPIEZA
    # =========================
    def clean_data(self):
        df = self.df.copy()

        # edad limpia (opcional)
        if "edad" in df.columns:
            df["edad"] = df["edad"].astype(str).str.extract(r"(\d+)")[0]

        df = df.fillna("")

        # 🔥 IMPORTANTE: NO generar id
        if "id" not in df.columns:
            raise ValueError("El dataset no contiene _id/id")

        self.df = df
        print("Datos limpiados correctamente")

    # =========================
    # 3. BACKUP JSON
    # =========================
    def save_json(self, path="backup_casos.json"):
        data = self.df.to_dict(orient="records")

        with open(path, "w", encoding="utf-8") as f:
            json.dump(data, f, ensure_ascii=False, indent=2)

        print(f"Backup JSON guardado en {path}")

    # =========================
    # 4. EXISTS
    # =========================
    def exists(self, id_value):
        self.cursor.execute(
            "SELECT 1 FROM casos_clinicos_originales WHERE id = %s",
            (id_value,)
        )
        return self.cursor.fetchone() is not None

    # =========================
    # 5. INSERT
    # =========================
    def insert_data(self):
        sql = """
        INSERT INTO casos_clinicos_originales (
            id, edad, sexo, antecedentes_medicos, antecedentes_quirurgicos, habitos,
            situacion_basal, medicacion_actual, antecedentes_familiares, motivo,
            sintomas, exploracion_general, signos, resultados_pruebas,
            razonamiento_clinico, diagnostico_final, tratamiento_farmacologico,
            tratamiento_no_farmacologico, factores_sociales, alergias,
            referencias_bibliograficas, categoria, keywords, codigo_cie_10,
            dificultad, chunk_id, chunk, agente
        ) VALUES (
            %(id)s, %(edad)s, %(sexo)s, %(antecedentes_medicos)s, %(antecedentes_quirurgicos)s,
            %(habitos)s, %(situacion_basal)s, %(medicacion_actual)s,
            %(antecedentes_familiares)s, %(motivo)s, %(sintomas)s,
            %(exploracion_general)s, %(signos)s, %(resultados_pruebas)s,
            %(razonamiento_clinico)s, %(diagnostico_final)s,
            %(tratamiento_farmacologico)s, %(tratamiento_no_farmacologico)s,
            %(factores_sociales)s, %(alergias)s, %(referencias_bibliograficas)s,
            %(categoria)s, %(keywords)s, %(codigo_cie_10)s, %(dificultad)s,
            %(chunk_id)s, %(chunk)s, %(agente)s
        )
        """

        inserted = 0
        skipped = 0

        for _, row in self.df.iterrows():
            data = row.to_dict()
            row_id = data.get("id")

            try:
                if self.exists(row_id):
                    skipped += 1
                    continue

                self.cursor.execute(sql, data)
                inserted += 1

            except Exception as e:
                print(f"❌ Error en id {row_id}: {e}")

        self.conn.commit()

        print(f"\n✔ Insertados: {inserted}")
        print(f"⏭️ Omitidos: {skipped}")

    # =========================
    # 6. CLOSE
    # =========================
    def close(self):
        self.cursor.close()
        self.conn.close()
        print("Conexión cerrada")


# =========================
# MAIN
# =========================
if __name__ == "__main__":
    pipeline = CasosClinicosPipeline()

    pipeline.fetch_dataset()
    pipeline.clean_data()
    pipeline.save_json()
    pipeline.insert_data()
    pipeline.close()

    print("\n🚀 PIPELINE COMPLETO")