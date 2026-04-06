const ENV = (() => {
  const host = window.location.hostname;
  const params = new URLSearchParams(window.location.search);

  // Si viene ?mode=prod en la URL, lo guardamos para que sobreviva la redirección de Google
  if (params.get("mode") === "prod") {
    sessionStorage.setItem("app_mode", "prod");
  }

  const mode = sessionStorage.getItem("app_mode");

  // DEPLOY — dominio real
  if (host === "sanitycode.riberadeltajo.es") {
    return {
      nombre: "deploy",
      API_BASE: "https://sanitycode.riberadeltajo.es",
      requiresAuth: true
    };
  }

  // PROD LOCAL — con login de Google
  if (mode === "prod") {
    return {
      nombre: "prod-local",
      API_BASE: "http://127.0.0.1:8080",
      requiresAuth: true
    };
  }

  // DEV — sin login
  return {
    nombre: "dev",
    API_BASE: "http://localhost:8080",
    requiresAuth: false
  };
})();

console.log(`[Config] Entorno: ${ENV.nombre} → ${ENV.API_BASE}`);