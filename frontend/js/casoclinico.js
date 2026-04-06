// ============================================
// Variable global donde guardaremos el caso
// ============================================
let casoActual = null;

// ============================================
// Función auxiliar para pintar un campo en el HTML
// ============================================
function pintar(id, valor) {
  const elemento = document.getElementById(id);
  if (elemento) {
    elemento.textContent = valor || "—";
  }
}

// ============================================
// Función que llama al backend y carga el caso
// ============================================
async function cargarCasoClinico() {
  try {
    const response = await fetch(`${ENV.API_BASE}/api/casos/random`, {
      credentials: "include",
    });

    if (response.status === 401) {
      if (ENV.requiresAuth) {
        console.warn("No autenticado. Redirigiendo a Google...");
        window.location.href = `${ENV.API_BASE.replace("/api", "")}/oauth2/authorization/google`;
      } else {
        console.warn("401 en modo dev — ¿está activo dev-mode en el backend?");
      }
      return;
    }

    if (!response.ok) {
      throw new Error(`Error en la respuesta: ${response.status}`);
    }

    const caso = await response.json();
    casoActual = caso;

    // 🩺 Datos del Paciente
    pintar("edad", caso.edad);
    pintar("sexo", caso.sexo);
    pintar("alergias", caso.alergias);
    pintar("factores_sociales", caso.factores_sociales);

    // 📚 Antecedentes
    pintar("antecedentes_medicos", caso.antecedentes_medicos);
    pintar("antecedentes_quirurgicos", caso.antecedentes_quirurgicos);
    pintar("antecedentes_familiares", caso.antecedentes_familiares);

    // 🧬 Situación Clínica
    pintar("habitos", caso.habitos);
    pintar("situacion_basal", caso.situacion_basal);
    pintar("medicacion_actual", caso.medicacion_actual);
    pintar("motivo", caso.motivo);
    pintar("sintomas", caso.sintomas);

    // 🔍 Exploración y Pruebas
    pintar("exploracion_general", caso.exploracion_general);
    pintar("signos", caso.signos);
    pintar("resultados_pruebas", caso.resultados_pruebas);

    // 🧠 Juicio Clínico
    pintar("razonamiento_clinico", caso.razonamiento_clinico);
    pintar("diagnostico_final", caso.diagnostico_final);

    // 💊 Tratamiento
    pintar("tratamiento_farmacologico", caso.tratamiento_farmacologico);
    pintar("tratamiento_no_farmacologico", caso.tratamiento_no_farmacologico);

    // 📖 Información Adicional
    pintar("referencias_bibliograficas", caso.referencias_bibliograficas);
    pintar("categoria", caso.categoria);
    pintar("keywords", caso.keywords);
    pintar("codigo_cie_10", caso.codigo_cie_10);

  } catch (error) {
    console.error("Error cargando caso clínico:", error);
  }
}

// ============================================
// Función para enviar el caso al ChatBot
// ============================================
function abrirEnChatbot() {
  if (casoActual) {
    localStorage.setItem("caso_clinico_activo", JSON.stringify(casoActual));
    window.location.href = "chat.html";
  }
}

// ============================================
// Carga automática al abrir la página
// ============================================
cargarCasoClinico();