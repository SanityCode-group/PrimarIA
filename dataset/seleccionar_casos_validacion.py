import os
import mysql.connector
from dotenv import load_dotenv

load_dotenv()


# Script para seleccionar 150 casos aleatorios que no estén ya en la tabla muestra y copiarlos a casos_clinicos_muestra.


# Conexión a la BD
conexion = mysql.connector.connect(
    host=os.getenv("DB_HOST"),
    port=os.getenv("DB_PORT"),
    user=os.getenv("DB_USER"),
    password=os.getenv("DB_PASS"),
    database=os.getenv("DB_NAME")
)

cursor = conexion.cursor(dictionary=True)

# 1. Obtener 150 IDs aleatorios que NO estén ya en la tabla muestra
query_ids = """
SELECT id 
FROM casos_clinicos_originales
WHERE id NOT IN (SELECT id_original FROM casos_clinicos_muestra)
ORDER BY RAND()
LIMIT 150;
"""

cursor.execute(query_ids)
ids_seleccionados = [fila["id"] for fila in cursor.fetchall()]

print("IDs seleccionados:", ids_seleccionados)

if len(ids_seleccionados) == 0:
    print("No hay suficientes casos nuevos para seleccionar.")
    exit()

# 2. Insertar los IDs seleccionados en la tabla muestra
insert_query = """
INSERT INTO casos_clinicos_muestra (id_original)
VALUES (%s);
"""

cursor.executemany(insert_query, [(i,) for i in ids_seleccionados])
conexion.commit()

print(f"✔️ Se han insertado {cursor.rowcount} casos en casos_clinicos_muestra.")

cursor.close()
conexion.close()
