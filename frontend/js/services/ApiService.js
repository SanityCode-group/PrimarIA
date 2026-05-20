import { ENV } from './config.js';

class ApiService {
  constructor() {
    this.baseUrl = ENV.API_BASE;
  }

  async fetch(endpoint, options = {}) {
    const url = `${this.baseUrl}${endpoint}`;
    const defaultOptions = {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
    };

    const response = await fetch(url, { ...defaultOptions, ...options });

    if (response.status === 401) {
      if (ENV.requiresAuth) {
        window.location.href = `${this.baseUrl.replace("/api", "")}/oauth2/authorization/google`;
      }
      throw new Error("No autenticado");
    }

    if (response.status === 403) {
      throw new Error('Acceso denegado');
    }

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || `Error ${response.status}`);
    }

    // Si no hay contenido, devolver que está ok
    if (response.status === 204) return true;

    //return response.json();
    const text = await response.text();

    if (!text) return true; // respuesta vacía

    try {
      return JSON.parse(text);
    } catch {
      return text; // por si devuelve "OK" u otro texto
    }
  }

  // Usuarios
  async getMe() {
    return this.fetch('/api/usuario/me');
  }

  // Casos
  async getRandomCaso() {
    return this.fetch('/api/casos/random');
  }

  // Validaciones
  async guardarValidacion(payload) {
    return this.fetch('/api/validaciones', {
      method: 'POST',
      body: JSON.stringify(payload)
    });
  }

  // Admin: Usuarios
  async getUsuarios() {
    return this.fetch('/api/admin/usuarios');
  }


  // Admin: Whitelist
  async getWhitelist() {
    return this.fetch('/api/admin/whitelist');
  }

  async addWhitelist(email) {
    return this.fetch('/api/admin/whitelist', {
      method: 'POST',
      body: JSON.stringify({ email })
    });
  }

  async deleteWhitelist(id) {
    return this.fetch(`/api/admin/whitelist/${id}`, { method: 'DELETE' });
  }


  // Admin: Métricas
  async getResumenGlobal() {
    return this.fetch('/api/admin/metricas/resumen');
  }

  async getMetricasPorAgente() {
    return this.fetch('/api/admin/metricas/por-agente');
  }

  async getTopCasos() {
    return this.fetch('/api/admin/metricas/top-casos');
  }

  async getPerfectosPrecision() {
    return this.fetch('/api/admin/metricas/perfectos/precision');
  }

  async getPerfectosClaridad() {
    return this.fetch('/api/admin/metricas/perfectos/claridad');
  }

  async getCasoDetalle(id) {
    return this.fetch(`/api/admin/casos/${id}`);
  }
  
}

export const apiService = new ApiService();
