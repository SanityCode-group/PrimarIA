import os
import pandas as pd
import mysql.connector
from dotenv import load_dotenv

# Cargar variables del archivo .env
load_dotenv()

DB_HOST = os.getenv("DB_HOST")
DB_PORT = os.getenv("DB_PORT")
DB_USER = os.getenv("DB_USER")
DB_PASS = os.getenv("DB_PASS")
DB_NAME = os.getenv("DB_NAME")

# Conectar a MariaDB
conexion = mysql.connector.connect(
    host=DB_HOST,
    port=DB_PORT,
    user=DB_USER,
    password=DB_PASS,
    database=DB_NAME
)

cursor = conexion.cursor()

# Leer CSV generado por HuggingFace
df = pd.read_csv("casos_clinicos.csv")

# Asegurar que el campo ia_generadora existe
if "ia_generadora" not in df.columns:
    df["ia_generadora"] = "HuggingFace"   

# Query de inserción adaptada a tu tabla
sql = """
INSERT INTO casos_clinicos_originales (
    edad, sexo, antecedentes_medicos, antecedentes_quirurgicos, habitos,
    situacion_basal, medicacion_actual, antecedentes_familiares, motivo,
    sintomas, exploracion_general, signos, resultados_pruebas,
    razonamiento_clinico, diagnostico_final, tratamiento_farmacologico,
    tratamiento_no_farmacologico, factores_sociales, alergias,
    referencias_bibliograficas, categoria, keywords, codigo_cie_10,
    dificultad, chunk_id, chunk, ia_generadora
) VALUES (
    %(edad)s, %(sexo)s, %(antecedentes_medicos)s, %(antecedentes_quirurgicos)s,
    %(habitos)s, %(situacion_basal)s, %(medicacion_actual)s,
    %(antecedentes_familiares)s, %(motivo)s, %(sintomas)s,
    %(exploracion_general)s, %(signos)s, %(resultados_pruebas)s,
    %(razonamiento_clinico)s, %(diagnostico_final)s,
    %(tratamiento_farmacologico)s, %(tratamiento_no_farmacologico)s,
    %(factores_sociales)s, %(alergias)s, %(referencias_bibliograficas)s,
    %(categoria)s, %(keywords)s, %(codigo_cie_10)s, %(dificultad)s,
    %(chunk_id)s, %(chunk)s, %(ia_generadora)s
)
"""

# Insertar fila por fila
for _, fila in df.iterrows():
    cursor.execute(sql, fila.to_dict())

conexion.commit()
cursor.close()
conexion.close()

print("Importación completada correctamente.")
