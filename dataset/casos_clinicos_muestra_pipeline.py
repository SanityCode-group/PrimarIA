import pandas as pd
import mysql.connector
import os
from dotenv import load_dotenv


class MuestreoCasosClinicos:

    def __init__(self):
        load_dotenv()

        self.conn = mysql.connector.connect(
            host=os.getenv("DB_HOST"),
            port=os.getenv("DB_PORT"),
            user=os.getenv("DB_USER"),
            password=os.getenv("DB_PASS"),
            database=os.getenv("DB_NAME")
        )
        self.cursor = self.conn.cursor(dictionary=True)

    # =========================
    # 1. CARGAR DATOS
    # =========================
    def fetch_data(self):
        query = """
        SELECT id, agente, dificultad
        FROM casos_clinicos_originales
        WHERE agente IN ('openAI', 'grok', 'deep_seek')
        """
        self.df = pd.read_sql(query, self.conn)

        # normalizar dificultad
        self.df["dificultad"] = self.df["dificultad"].str.upper().str.strip()

        print(f"Registros cargados: {len(self.df)}")

    # =========================
    # 2. MUESTREO
    # =========================
    def sample_data(self):
        valid_dificultades = ["FACIL", "MEDIA", "DIFICIL"]

        df = self.df[self.df["dificultad"].isin(valid_dificultades)]

        samples = []

        for agente in ["openAI", "grok", "deep_seek"]:
            df_agente = df[df["agente"] == agente]

            for dificultad in valid_dificultades:
                df_subset = df_agente[df_agente["dificultad"] == dificultad]

                if len(df_subset) < 20:
                    print(f"⚠️ {agente} - {dificultad}: solo {len(df_subset)}")
                    sample = df_subset
                else:
                    sample = df_subset.sample(n=20, random_state=42)

                samples.append(sample)

        self.sampled_df = pd.concat(samples).reset_index(drop=True)

        print(f"Muestra total: {len(self.sampled_df)}")

    # =========================
    # 3. INSERT EN TABLA
    # =========================
    def insert_muestra(self):
        insert_sql = """
        INSERT INTO casos_clinicos_muestra (id_original)
        VALUES (%s)
        """

        check_sql = """
        SELECT 1 FROM casos_clinicos_muestra
        WHERE id_original = %s
        """

        inserted = 0
        skipped = 0

        for _, row in self.sampled_df.iterrows():
            id_original = int(row["id"])

            # evitar duplicados
            self.cursor.execute(check_sql, (id_original,))
            if self.cursor.fetchone():
                skipped += 1
                continue

            try:
                self.cursor.execute(insert_sql, (id_original,))
                inserted += 1
            except Exception as e:
                print(f"❌ Error insertando {id_original}: {e}")

        self.conn.commit()

        print(f"\n✔ Insertados: {inserted}")
        print(f"⏭️ Omitidos (ya existentes): {skipped}")

    # =========================
    # 4. CLOSE
    # =========================
    def close(self):
        self.cursor.close()
        self.conn.close()


# =========================
# MAIN
# =========================
if __name__ == "__main__":
    pipeline = MuestreoCasosClinicos()

    pipeline.fetch_data()
    pipeline.sample_data()
    pipeline.insert_muestra()
    pipeline.close()

    print("\n🚀 MUESTRA GUARDADA EN BD")