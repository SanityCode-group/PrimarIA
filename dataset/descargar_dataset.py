import os
from dotenv import load_dotenv
from huggingface_hub import login
from datasets import load_dataset, concatenate_datasets

# Cargar variables del archivo .env
load_dotenv()

# Leer token desde variable de entorno
hf_token = os.getenv("HF_TOKEN")

if hf_token is None:
    raise ValueError("No se encontró la variable de entorno HF_TOKEN")

login(hf_token)

# Cargar dataset completo
dataset_dict = load_dataset("ilopezmon/casos_clinicos_completos")

# Unir todos los splits en uno solo
all_splits = []
for split_name, split_data in dataset_dict.items():
    all_splits.append(split_data)

dataset_unificado = concatenate_datasets(all_splits)

# Exportar a CSV
dataset_unificado.to_csv("casos_clinicos.csv", encoding="utf-8-sig") 
print("CSV generado correctamente con", len(dataset_unificado), "filas.")