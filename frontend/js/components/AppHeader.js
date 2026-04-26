import { apiService } from '../services/ApiService.js';

class AppHeader extends HTMLElement {
  static get observedAttributes() { return ['usuario']; }

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
      if (nombre) {
        this.setAttribute('usuario', nombre);
      }
    } catch (e) {
      console.warn('Usuario no autenticado o error al cargar:', e.message);
    }
  }

  render() {
    const usuarioAttr = this.getAttribute('usuario');
    const usuario = usuarioAttr && usuarioAttr !== 'undefined' && usuarioAttr !== 'null' ? usuarioAttr : '';
    
    const currentPath = window.location.pathname;
    const isCasos = currentPath.includes('casosclinico.html');
    const isChat = currentPath.includes('chat.html');

    this.innerHTML = `
      <header>
        <div class="logo-container" style="display: flex; align-items: center; gap: 12px; cursor: pointer;" onclick="window.location.href='./casosclinico.html'">
          <img src="./assets/img/logo-primaria-sanitycode.svg" alt="Logo" style="height: 40px; width: auto;">
          <h1>PrimarIA</h1>
        </div>
        <nav>
          <a href="./casosclinico.html" class="${isCasos ? 'active' : ''}">Validación</a>
          <a href="./chat.html" class="${isChat ? 'active' : ''}">ChatBot</a>
          <div class="user-info">
            ${usuario ? `<span>👤 ${usuario}</span>` : ''}
            <a href="./index.html" class="btn-logout">Cerrar sesión</a>
          </div>
        </nav>
      </header>`;
  }
}

customElements.define('app-header', AppHeader);
