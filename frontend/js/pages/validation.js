import { apiService } from '../services/ApiService.js';
import { DOMUtils } from '../utils/DOMUtils.js';

class ValidationPage {
  constructor() {
    this.casoActual = null;
    this.init();
  }

  async init() {
    await this.cargarCasoClinico();
    this.setupEventListeners();
  }

  setupEventListeners() {
    const btnValidar = document.querySelector(".btn-validar");
    if (btnValidar) {
      btnValidar.onclick = () => this.enviarValidacion();
    }
  }

  async cargarCasoClinico() {
    try {
      this.casoActual = await apiService.getRandomCaso();
      this.pintarCaso();
    } catch (error) {
      console.error("Error cargando caso clínico:", error.message);
    }
  }

  pintarCaso() {
    if (!this.casoActual) return;
    
    const fields = [
      'edad', 'sexo', 'alergias', 'factores_sociales',
      'antecedentes_medicos', 'antecedentes_quirurgicos', 'antecedentes_familiares',
      'habitos', 'situacion_basal', 'medicacion_actual', 'motivo', 'sintomas',
      'exploracion_general', 'signos', 'resultados_pruebas',
      'razonamiento_clinico', 'diagnostico_final',
      'tratamiento_farmacologico', 'tratamiento_no_farmacologico',
      'referencias_bibliograficas', 'categoria', 'keywords', 'codigo_cie_10'
    ];

    fields.forEach(field => DOMUtils.setText(field, this.casoActual[field]));
  }

  async enviarValidacion() {
    const data = {
      precision: DOMUtils.getCheckedValue('precision'),
      claridad: DOMUtils.getCheckedValue('claridad'),
      relevancia: DOMUtils.getCheckedValue('relevancia'),
      adecuacion: DOMUtils.getCheckedValue('adecuacion'),
      nivelTecnico: DOMUtils.getCheckedValue('nivel-tecnico'),
    };

    if (Object.values(data).some(v => v === null)) {
      alert("Por favor, puntúa todos los criterios antes de validar.");
      return;
    }

    const payload = {
      idCasoOriginal: this.casoActual.id,
      precisionDiagnostica: parseInt(data.precision),
      claridadTextual: parseInt(data.claridad),
      relevanciaClinica: parseInt(data.relevancia),
      adecuacionContextual: parseInt(data.adecuacion),
      nivelTecnico: parseInt(data.nivelTecnico),
      observaciones: document.getElementById("comentario").value,
    };

    try {
      await apiService.guardarValidacion(payload);
      alert("✅ Validación guardada correctamente.");
      DOMUtils.resetForm("form");
      this.cargarCasoClinico();
      DOMUtils.scrollToTop();
    } catch (error) {
      alert("❌ Error al guardar: " + error.message);
    }
  }
}

// Inicializar página
document.addEventListener("DOMContentLoaded", () => new ValidationPage());
