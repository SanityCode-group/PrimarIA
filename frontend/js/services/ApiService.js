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

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || `Error ${response.status}`);
    }

    // Si no hay contenido, devolver que está ok
    if (response.status === 204) return true;

    return response.json();
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
}

export const apiService = new ApiService();
