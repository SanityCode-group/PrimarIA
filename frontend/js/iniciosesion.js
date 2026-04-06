async function cargarUsuario() {
  const res = await fetch(`${ENV.API_BASE}/api/usuario/me`, { credentials: 'include' });
  const data = await res.json();

  document.querySelector('app-header').setAttribute('usuario', data.nombre);
}
cargarUsuario();