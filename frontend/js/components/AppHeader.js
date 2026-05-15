import { apiService } from '../services/ApiService.js';

class AppHeader extends HTMLElement {
  static get observedAttributes() { return ['usuario', 'rol']; }

  attributeChangedCallback() {
    this.render();
  }

  connectedCallback() {
    this.render();
    this.loadUser();
  }

  async loadUser() {
    try {
      const data = await apiService.getMe();
      const nombre = data?.nombre || data?.name || data?.given_name || '';
      const rol    = data?.rol || '';
      if (nombre) {
        this.setAttribute('usuario', nombre);
      }
      if (rol) { 
        this.setAttribute('rol', rol);
      }

      // Redirigir ADMIN a su panel si no está ya ahí
      const enAdmin = window.location.pathname.includes('admin.html');
      if (rol.toUpperCase() === 'ADMIN' && !enAdmin) {
        window.location.href = './admin.html';
      }
    } catch (e) {
      console.warn('Usuario no autenticado o error al cargar:', e.message);
    }
  }

  render() {
    const usuarioAttr = this.getAttribute('usuario');
    const usuario = usuarioAttr && usuarioAttr !== 'undefined' && usuarioAttr !== 'null' ? usuarioAttr : '';
    const rol = this.getAttribute('rol') || '';
    const esAdmin = rol.toUpperCase() === 'ADMIN';

    const currentPath = window.location.pathname;
    const isCasos = currentPath.includes('casosclinico.html');
    const isChat  = currentPath.includes('chat.html');
    const isAdmin = currentPath.includes('admin.html');

    this.innerHTML = `
      <header>
        <div class="logo-container"
             style="display:flex;align-items:center;gap:12px;cursor:pointer;"
             onclick="window.location.href='${esAdmin ? './admin.html' : './casosclinico.html'}'">
          <img src="./assets/img/logo-primaria-sanitycode.svg"
               alt="Logo" style="height:40px;width:auto;">
          <h1>PrimarIA</h1>
        </div>
        <nav>
          ${esAdmin ? `
            <!-- <a href="#"  class="${isAdmin ? 'active' : ''}">⚙️ Panel administrador</a> -->
          ` : `
            <a href="./casosclinico.html" class="${isCasos ? 'active' : ''}">Validación</a>
            <a href="./chat.html"         class="${isChat  ? 'active' : ''}">ChatBot</a>
          `}

          
          <div class="user-info">
            ${usuario
              ? `<span>👤 ${usuario}${esAdmin
                  ? ' <span class="badge-admin">ADMIN</span>'
                  : ''}</span>`
              : ''}
            <a href="./index.html" class="btn-logout">Cerrar sesión</a>
          </div>
        </nav>
      </header>`;
  }
}

customElements.define('app-header', AppHeader);