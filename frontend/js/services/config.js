export const ENV = (() => {
  const host = window.location.hostname;
  const params = new URLSearchParams(window.location.search);

  if (params.get("mode") === "prod") {
    localStorage.setItem("app_mode", "prod");
  }

  const mode = localStorage.getItem("app_mode");

  if (mode === "prod" && host === "localhost") {
    const newUrl = window.location.href.replace("localhost", "127.0.0.1");
    window.location.replace(newUrl);
  }

  if (host === "sanitycode.riberadeltajo.es") {
    return {
      nombre: "deploy",
      API_BASE: "https://sanitycode.riberadeltajo.es",
      requiresAuth: true
    };
  }

  if (mode === "prod" || host === "127.0.0.1") {
    return {
      nombre: "prod-local",
      API_BASE: "http://127.0.0.1:8080",
      requiresAuth: true
    };
  }

  return {
    nombre: "dev",
    API_BASE: "http://localhost:8080",
    requiresAuth: false
  };
})();
