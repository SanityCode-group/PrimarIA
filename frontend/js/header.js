class AppHeader extends HTMLElement {
  static get observedAttributes() { return ['usuario']; }

  attributeChangedCallback() {
    this.connectedCallback();
  }

  connectedCallback() {
    const usuario = this.getAttribute('usuario') || '';
    this.innerHTML = `
      <header>
        <h1>PrimarIA</h1>
        <nav>
          <a href="./casosclinico.html">Casos clínicos</a>
          <a href="./chat.html">ChatBot</a>
          <div style="display: flex; align-items: center; gap: 15px;">
            ${usuario ? `<span style="color: #6c757d; font-size: 14px;">👤 ${usuario}</span>` : ''}
            <a href="./login.html" style="text-decoration: none; color: #dc3545; font-weight: 500; padding: 5px 10px; border: 1px solid #dc3545; border-radius: 4px;">Cerrar sesión</a>
            </div>
        </nav>
      </header>`;
  }
}
customElements.define('app-header', AppHeader);

// Carga el usuario nada más definir el componente
async function cargarUsuario() {
  try {
    const res = await fetch(`${ENV.API_BASE}/api/usuario/me`, { credentials: 'include' });
    const data = await res.json();
    document.querySelector('app-header').setAttribute('usuario', data.nombre);
  } catch (e) {
    console.error('Error cargando usuario:', e);
  }
}

cargarUsuario();