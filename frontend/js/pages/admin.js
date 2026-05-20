import { apiService } from '../services/ApiService.js';
import { ENV } from '../services/config.js';

class AdminPage {
  constructor() {
    this.seccionActiva = 'usuarios';
    this.init();
  }

  async init() {
    await this.verificarAdmin();
    this.setupNav();
    this.navegarA(location.hash.replace('#', '') || 'usuarios');
  }

  async verificarAdmin() {
    try {
      const me = await apiService.getMe();
      if (!me?.rol || me.rol.toUpperCase() !== 'ADMIN') {
        alert('Acceso restringido a administradores.');
        window.location.href = './casosclinico.html';
      }
    } catch {
      window.location.href = './index.html';
    }
  }

  setupNav() {
    document.querySelectorAll('.admin-nav a').forEach(a => {
      a.addEventListener('click', e => {
        e.preventDefault();
        const seccion = a.dataset.seccion;
        this.navegarA(seccion);
        history.replaceState(null, '', `#${seccion}`);
      });
    });
  }

  navegarA(seccion) {
    this.seccionActiva = seccion;
    document.querySelectorAll('.admin-nav a').forEach(a => {
      a.classList.toggle('active', a.dataset.seccion === seccion);
    });
    document.querySelectorAll('.seccion').forEach(s => {
      s.classList.toggle('hidden', s.id !== seccion);
    });
    const loaders = {
      usuarios: () => this.cargarUsuarios(),
      whitelist: () => this.cargarWhitelist(),
      metricas: () => this.cargarMetricas(),
      dashboard: () => this.cargarDashboard(),
    };
    loaders[seccion]?.();
  }

  //  USUARIOS 

  async cargarUsuarios() {
    const cont = document.getElementById('tabla-usuarios');
    cont.innerHTML = '<p class="loading">Cargando...</p>';
    try {
      const usuarios = await apiService.getUsuarios();
      if (!usuarios.length) { cont.innerHTML = '<p>No hay usuarios registrados.</p>'; return; }

      cont.innerHTML = `
        <table class="admin-table">
          <thead>
            <tr>
              <th>ID</th><th>Nombre</th><th>Email</th><th>Rol</th>
              <th>Fecha registro</th><th>Último acceso</th>
            </tr>
          </thead>
          <tbody>
            ${usuarios.map(u => `
              <tr>
                <td>${u.id}</td>
                <td>${u.nombre}</td>
                <td>${u.email}</td>
                <td><span class="badge ${u.rol === 'ADMIN' ? 'badge-admin' : 'badge-medico'}">${u.rol}</span></td>
                <td>${this.formatFecha(u.fechaCreacion)}</td>
                <td>${this.formatFecha(u.ultimoAcceso)}</td>
              </tr>`).join('')}
          </tbody>
        </table>`;
    } catch (e) {
      cont.innerHTML = `<p class="error">Error: ${e.message}</p>`;
    }
  }

  //  WHITELIST 

  async cargarWhitelist() {
    const cont = document.getElementById('tabla-whitelist');
    cont.innerHTML = '<p class="loading">Cargando...</p>';
    try {
      const lista = await apiService.getWhitelist();
      this.renderWhitelist(lista, cont);
    } catch (e) {
      cont.innerHTML = `<p class="error">Error: ${e.message}</p>`;
    }

    document.getElementById('form-whitelist').onsubmit = async (e) => {
      e.preventDefault();
      const input = document.getElementById('nuevo-email');
      const email = input.value.trim();
      if (!email) return;
      try {
        await apiService.addWhitelist(email);
        input.value = '';
        this.cargarWhitelist();
      } catch (err) {
        alert('Error: ' + err.message);
      }
    };
  }

  renderWhitelist(lista, cont) {
    if (!lista.length) { cont.innerHTML = '<p>La whitelist está vacía.</p>'; return; }
    cont.innerHTML = `
      <table class="admin-table">
        <thead><tr><th>ID</th><th>Email</th><th>Fecha alta</th><th>Acción</th></tr></thead>
        <tbody>
          ${lista.map(w => `
            <tr>
              <td>${w.id}</td>
              <td>${w.email}</td>
              <td>${this.formatFecha(w.fechaAlta)}</td>
              <td>
                <button class="btn-delete" data-id="${w.id}">Eliminar</button>
              </td>
            </tr>`).join('')}
        </tbody>
      </table>`;

    cont.querySelectorAll('.btn-delete').forEach(btn => {
      btn.onclick = async () => {
        if (!confirm('¿Eliminar este email de la whitelist?')) return;
        try {
          await apiService.deleteWhitelist(btn.dataset.id);
          this.cargarWhitelist();
        } catch (err) {
          alert('Error: ' + err.message);
        }
      };
    });
  }

  //  MÉTRICAS 

  async cargarMetricas() {
    const cont = document.getElementById('contenido-metricas');
    cont.innerHTML = '<p class="loading">Cargando métricas...</p>';

    try {
      const [resumen, porAgente, topCasos, perfectosPrecision, perfectosClaridad] =
        await Promise.all([
          apiService.getResumenGlobal(),
          apiService.getMetricasPorAgente(),
          apiService.getTopCasos(),
          apiService.getPerfectosPrecision(),
          apiService.getPerfectosClaridad(),
        ]);

      cont.innerHTML = `
        ${this.htmlResumen(resumen)}
        ${this.htmlTablaAgentes(porAgente)}
        ${this.htmlTopCasos(topCasos)}
        ${this.htmlCasosPerfectos('Casos con precisión diagnóstica perfecta (5/5)', perfectosPrecision)}
        ${this.htmlCasosPerfectos('Casos con claridad textual perfecta (5/5)', perfectosClaridad)}
      `;

      //hacer clicables las filas de las tablas para ver detalles del caso
      this.activarClicksCasos();

    } catch (e) {
      cont.innerHTML = `<p class="error">Error al cargar métricas: ${e.message}</p>`;
    }
  }

  htmlResumen(r) {
    if (!r) return '';
    const criterios = r.mediaGlobalCriterios || {};
    const nombres = {
      precisionDiagnostica: 'Precisión diagnóstica',
      claridadTextual: 'Claridad textual',
      relevanciaClinica: 'Relevancia clínica',
      adecuacionContextual: 'Adecuación contextual',
      nivelTecnico: 'Nivel técnico',
    };
    const mejorLabel = nombres[r.mejorCriterio] || r.mejorCriterio;

    return `
      <section class="metrica-bloque">
        <h2>Resumen global</h2>
        <div class="kpi-grid">
          <div class="kpi"><span class="kpi-valor">${r.totalValidaciones}</span><span class="kpi-label">Validaciones</span></div>
          <div class="kpi"><span class="kpi-valor">${r.totalCasosValidados}</span><span class="kpi-label">Casos validados</span></div>
          <div class="kpi"><span class="kpi-valor">${r.totalUsuarios}</span><span class="kpi-label">Usuarios</span></div>
        </div>
        <h3 style="margin:20px 0 10px">Media global por criterio</h3>
        <div class="barras-criterios">
          ${Object.entries(criterios).map(([k, v]) => `
            <div class="barra-item">
              <span class="barra-label">${nombres[k] || k}</span>
              <div class="barra-track">
                <div class="barra-fill" style="width:${(v / 5) * 100}%"></div>
              </div>
              <span class="barra-valor">${v}/5</span>
            </div>`).join('')}
        </div>
      </section>`;
  }

  htmlTablaAgentes(agentes) {
    if (!agentes?.length) return '<p>No hay datos por agente aún.</p>';
    return `
      <section class="metrica-bloque">
        <h2>Puntuaciones por modelo de IA</h2>
        <table class="admin-table">
          <thead>
            <tr>
              <th>Modelo</th><th>Precisión</th><th>Claridad</th>
              <th>Relevancia</th><th>Adecuación</th><th>Nivel técnico</th>
              <th>Media total</th><th>Validaciones</th>
            </tr>
          </thead>
          <tbody>
            ${agentes.map(a => `
              <tr>
                <td><strong>${a.agente || 'Sin nombre'}</strong></td>
                <td>${this.estrella(a.avgPrecision)}</td>
                <td>${this.estrella(a.avgClaridad)}</td>
                <td>${this.estrella(a.avgRelevancia)}</td>
                <td>${this.estrella(a.avgAdecuacion)}</td>
                <td>${this.estrella(a.avgNivel)}</td>
                <td><strong>${a.avgTotal}</strong></td>
                <td>${a.totalValidaciones}</td>
              </tr>`).join('')}
          </tbody>
        </table>
      </section>`;
  }

  htmlTopCasos(casos) {
    if (!casos?.length) return '';
    return `
      <section class="metrica-bloque">
        <h2>Top 10 casos mejor valorados</h2>
        ${this.tablaCasos(casos)}
      </section>`;
  }

  htmlCasosPerfectos(titulo, casos) {
    if (!casos?.length) return `<section class="metrica-bloque"><h2>${titulo}</h2><p>Ninguno aún.</p></section>`;
    return `
      <section class="metrica-bloque">
        <h2>${titulo}</h2>
        ${this.tablaCasos(casos)}
      </section>`;
  }

  tablaCasos(casos) {
    return `
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Agente IA</th>
            <th>Categoría</th>
            <th>Diagnóstico</th>
            <th>Puntuación</th>
          </tr>
        </thead>

        <tbody>
          ${casos.map(c => `
            <tr class="fila-caso" data-id="${c.idCaso}">
              <td>${c.idCaso}</td>
              <td>${c.agente || '-'}</td>
              <td>${c.categoria || '-'}</td>
              <td>${this.truncar(c.diagnostico, 70)}</td>
              <td><strong>${c.puntuacion}</strong></td>
            </tr>
          `).join('')}
        </tbody>
      </table>
    `;
  }

  //  DASHBOARD (gráficas) 

  async cargarDashboard() {
    const cont = document.getElementById('contenido-dashboard');
    cont.innerHTML = '<p class="loading">Cargando dashboard...</p>';
    try {
      const [resumen, porAgente] = await Promise.all([
        apiService.getResumenGlobal(),
        apiService.getMetricasPorAgente(),
      ]);

      cont.innerHTML = `
        <div class="dashboard-grid">
          <section class="metrica-bloque">
            <h2>Media total por modelo de IA</h2>
            <div class="chart-wrap">
              <canvas id="chart-agentes"></canvas>
            </div>
          </section>
          <section class="metrica-bloque">
            <h2>Comparativa por criterio y modelo</h2>
            <div class="chart-wrap chart-wrap--radar">
              <canvas id="chart-criterios"></canvas>
            </div>
          </section>
        </div>`;

      this.dibujarBarrasAgentes(porAgente);
      this.dibujarRadarCriterios(porAgente);
    } catch (e) {
      cont.innerHTML = `<p class="error">Error: ${e.message}</p>`;
    }
  }

  dibujarBarrasAgentes(agentes) {
    const ctx = document.getElementById('chart-agentes').getContext('2d');
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: agentes.map(a => a.agente || 'Sin nombre'),
        datasets: [{
          label: 'Media total (sobre 5)',
          data: agentes.map(a => a.avgTotal),
          backgroundColor: '#4285f4cc',
          borderColor: '#4285f4',
          borderWidth: 2,
          borderRadius: 6,
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
          y: { min: 0, max: 5, ticks: { stepSize: 1 } }
        },
        plugins: { legend: { display: false } }
      }
    });
  }

  dibujarRadarCriterios(agentes) {
    const ctx = document.getElementById('chart-criterios').getContext('2d');
    const colores = ['#4285f4', '#ea4335', '#fbbc04', '#34a853', '#9c27b0', '#ff6d00'];
    new Chart(ctx, {
      type: 'radar',
      data: {
        labels: ['Precisión', 'Claridad', 'Relevancia', 'Adecuación', 'Nivel técnico'],
        datasets: agentes.map((a, i) => ({
          label: a.agente || 'Sin nombre',
          data: [a.avgPrecision, a.avgClaridad, a.avgRelevancia, a.avgAdecuacion, a.avgNivel],
          borderColor: colores[i % colores.length],
          backgroundColor: colores[i % colores.length] + '22',
          borderWidth: 2,
          pointRadius: 3,
        }))
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        scales: {
          r: {
            min: 0,
            max: 5,
            ticks: { stepSize: 1, font: { size: 10 } },
            pointLabels: { font: { size: 12 } }
          }
        },
        plugins: {
          legend: {
            position: 'bottom',
            labels: { font: { size: 11 }, boxWidth: 12 }
          }
        }
      }
    });
  }

  //  Helpers 

  estrella(val) {
    const n = parseFloat(val) || 0;
    return `${n} <span style="color:#fbbc04">${'★'.repeat(Math.round(n))}${'☆'.repeat(5 - Math.round(n))}</span>`;
  }

  formatFecha(iso) {
    if (!iso) return '-';
    return new Date(iso).toLocaleDateString('es-ES', {
      day: '2-digit', month: '2-digit', year: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  }

  truncar(str, max) {
    if (!str) return '-';
    return str.length > max ? str.substring(0, max) + '…' : str;
  }

  // CLICKS EN FILAS DE CASOS PARA VER DETALLES
  activarClicksCasos() {
    document.querySelectorAll('.fila-caso').forEach(fila => {
      fila.addEventListener('click', async () => {
        const id = fila.dataset.id;
        await this.mostrarDetalleCaso(id);
      });
    });
  }

  async mostrarDetalleCaso(id) {
    try {
      const caso = await apiService.getCasoDetalle(id);
      const modal = document.createElement('div');
      modal.className = 'modal-caso';

      modal.innerHTML = `
        <div class="modal-caso-contenido">
          
          <button class="cerrar-modal">✕</button>

          <div class="modal-header-caso">
            <div>
              <h2>Caso #${caso.id}</h2>
              <p class="modal-subtitle">
                ${caso.categoria || '-'}
              </p>
            </div>

            <div class="badge-modal">
              ${caso.agente || 'IA'}
            </div>
          </div>

          <!-- DATOS PACIENTE -->
          <section class="detalle-card">
            <h3>🩺 Datos del paciente</h3>

            <div class="detalle-grid">
              <div>
                <strong>Edad</strong>
                <p>${caso.edad || '-'}</p>
              </div>

              <div>
                <strong>Sexo</strong>
                <p>${caso.sexo || '-'}</p>
              </div>

              <div>
                <strong>Alergias</strong>
                <p>${caso.alergias || '-'}</p>
              </div>

              <div>
                <strong>Situación basal</strong>
                <p>${caso.situacion_basal || '-'}</p>
              </div>
            </div>

            <div class="bloque-texto">
              <strong>Factores sociales</strong>
              <p>${caso.factores_sociales || '-'}</p>
            </div>
          </section>

          <!-- ANTECEDENTES -->
          <section class="detalle-card">
            <h3>📚 Antecedentes</h3>

            <div class="bloque-texto">
              <strong>Antecedentes médicos</strong>
              <p>${caso.antecedentes_medicos || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Antecedentes quirúrgicos</strong>
              <p>${caso.antecedentes_quirurgicos || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Antecedentes familiares</strong>
              <p>${caso.antecedentes_familiares || '-'}</p>
            </div>
          </section>

          <!-- SITUACIÓN CLÍNICA -->
          <section class="detalle-card">
            <h3>🧬 Situación clínica</h3>

            <div class="bloque-texto">
              <strong>Hábitos</strong>
              <p>${caso.habitos || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Medicación actual</strong>
              <p>${caso.medicacion_actual || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Motivo de consulta</strong>
              <p>${caso.motivo || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Síntomas</strong>
              <p>${caso.sintomas || '-'}</p>
            </div>
          </section>

          <!-- EXPLORACIÓN -->
          <section class="detalle-card">
            <h3>🔍 Exploración y pruebas</h3>

            <div class="bloque-texto">
              <strong>Exploración general</strong>
              <p>${caso.exploracion_general || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Signos</strong>
              <p>${caso.signos || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Resultados de pruebas</strong>
              <p>${caso.resultados_pruebas || '-'}</p>
            </div>
          </section>

          <!-- JUICIO CLÍNICO -->
          <section class="detalle-card">
            <h3>🧠 Juicio clínico</h3>

            <div class="bloque-texto">
              <strong>Razonamiento clínico</strong>
              <p>${caso.razonamiento_clinico || '-'}</p>
            </div>

            <div class="bloque-texto diagnostico-final">
              <strong>Diagnóstico final</strong>
              <p>${caso.diagnostico_final || '-'}</p>
            </div>
          </section>

          <!-- TRATAMIENTO -->
          <section class="detalle-card">
            <h3>💊 Tratamiento</h3>

            <div class="bloque-texto">
              <strong>Tratamiento farmacológico</strong>
              <p>${caso.tratamiento_farmacologico || '-'}</p>
            </div>

            <div class="bloque-texto">
              <strong>Tratamiento no farmacológico</strong>
              <p>${caso.tratamiento_no_farmacologico || '-'}</p>
            </div>
          </section>

          <!-- INFO EXTRA -->
          <section class="detalle-card">
            <h3>📖 Información adicional</h3>

            <div class="detalle-grid">
              <div>
                <strong>Categoría</strong>
                <p>${caso.categoria || '-'}</p>
              </div>

              <div>
                <strong>Código CIE-10</strong>
                <p>${caso.codigo_cie10 || '-'}</p>
              </div>

              <div>
                <strong>Keywords</strong>
                <p>${caso.keywords || '-'}</p>
              </div>
            </div>

            <div class="bloque-texto">
              <strong>Referencias bibliográficas</strong>
              <p>${caso.referencias_bibliograficas || '-'}</p>
            </div>
          </section>

          <!-- VALIDACIONES -->
          <section class="detalle-card">
            <h3>Validaciones</h3>

            ${caso.validaciones?.length
          ? caso.validaciones.map(v => `
                <div class="validacion-card">
                  <div class="validacion-header">
                    <strong>${v.usuario || 'Usuario'}</strong>
                    <span>${new Date(v.fechaValidacion).toLocaleString()}</span>
                  </div>

                  <div class="validacion-grid">
                    <div>Precisión: ⭐ ${v.precisionDiagnostica}</div>
                    <div>Claridad: ⭐ ${v.claridadTextual}</div>
                    <div>Relevancia: ⭐ ${v.relevanciaClinica}</div>
                    <div>Adecuación: ⭐ ${v.adecuacionContextual}</div>
                    <div>Nivel técnico: ⭐ ${v.nivelTecnico}</div>
                  </div>

                  <div class="bloque-texto">
                    ${v.observaciones || 'Sin observaciones'}
                  </div>
                </div>
              `).join('')
          : '<p>No hay validaciones.</p>'
        }
          </section>

        </div>
      `;


      document.body.appendChild(modal);

      modal.querySelector('.cerrar-modal')
        .addEventListener('click', () => modal.remove());

      modal.addEventListener('click', e => {
        if (e.target === modal) {
          modal.remove();
        }
      });

    } catch (e) {
      console.error(e);
      alert('Error cargando el caso');
    }

  }
}

document.addEventListener('DOMContentLoaded', () => new AdminPage());