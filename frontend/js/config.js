const ENV = (() => {
  const host = window.location.hostname;
  const params = new URLSearchParams(window.location.search);

  // 1. Detectar si forzamos modo prod via URL
  if (params.get("mode") === "prod") {
    localStorage.setItem("app_mode", "prod");
  }

  const mode = localStorage.getItem("app_mode");

  // 2. Redirección automática si estamos en localhost pero queremos modo prod
  // (Evita que el usuario pierda la sesión por desajuste de origen)
  if (mode === "prod" && host === "localhost") {
    console.warn("Redirigiendo a 127.0.0.1 para mantener consistencia de sesión (PROD)");
    const newUrl = window.location.href.replace("localhost", "127.0.0.1");
    window.location.replace(newUrl);
  }

  // DEPLOY — dominio real
  if (host === "sanitycode.riberadeltajo.es") {
    return {
      nombre: "deploy",
      API_BASE: "https://sanitycode.riberadeltajo.es",
      requiresAuth: true
    };
  }

  // PROD LOCAL — con login de Google (forzamos 127.0.0.1)
  if (mode === "prod" || host === "127.0.0.1") {
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