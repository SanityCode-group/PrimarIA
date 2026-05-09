import { ENV } from '../services/config.js';

class ChatService {
  static cargarChats() {
    return JSON.parse(localStorage.getItem("mis_chats")) || [];
  }

  static guardarChats(chats) {
    localStorage.setItem("mis_chats", JSON.stringify(chats));
  }

  static nuevoChat() {
    const chats = this.cargarChats();
    const nuevo = {
      id: "chat_" + Date.now(),
      titulo: "Nuevo chat",
      mensajes: [],
      validado: false
    };

    const caso = JSON.parse(localStorage.getItem("caso_clinico_activo"));
    if (caso) {
      nuevo.mensajes.push({
        rol: "ai",
        texto: `📋 Caso clínico cargado: Paciente de ${caso.edad} años. Motivo: ${caso.motivo}`
      });
    }

    chats.push(nuevo);
    this.guardarChats(chats);
    return nuevo.id;
  }

  static eliminarChat(id) {
    let chats = this.cargarChats();
    chats = chats.filter(c => c.id !== id);
    this.guardarChats(chats);
    return chats.length > 0 ? chats[0].id : null;
  }

  static agregarMensaje(chatId, rol, texto) {
    const chats = this.cargarChats();
    const chat = chats.find(c => c.id === chatId);
    if (!chat) return;

    chat.mensajes.push({ rol, texto });
    if (chat.mensajes.length === 1 || (chat.titulo === "Nuevo chat" && rol === 'user')) {
      chat.titulo = texto.substring(0, 30) + (texto.length > 30 ? "..." : "");
    }

    this.guardarChats(chats);
  }
}

class ChatPage {
  constructor() {
    this.chatActivo = null;
    this.init();
  }

  init() {
    this.setupEventListeners();
    const chats = ChatService.cargarChats();
    if (chats.length === 0) {
      this.cargarChat(ChatService.nuevoChat());
    } else {
      this.cargarChat(chats[0].id);
    }
  }

  setupEventListeners() {
    const btnEnviar = document.querySelector("#btn-enviar");
    const btnNuevo = document.querySelector(".new-chat");
    const input = document.querySelector("#input-texto");

    if (btnEnviar) {
      btnEnviar.onclick = () => this.enviarMensaje();
    }

    if (btnNuevo) {
      btnNuevo.onclick = () => this.cargarChat(ChatService.nuevoChat());
    }

    if (input) {
      input.addEventListener("keydown", (e) => {
        if (e.key === "Enter" && !e.shiftKey) {
          e.preventDefault();
          this.enviarMensaje();
        }
      });
    }
  }

  async enviarMensaje() {
    const input = document.querySelector("#input-texto");
    const texto = input.value.trim();
    if (!texto || !this.chatActivo) return;

    ChatService.agregarMensaje(this.chatActivo, "user", texto);
    this.cargarChat(this.chatActivo);
    input.value = "";

    const chats = ChatService.cargarChats();
    const chat = chats.find(c => c.id === this.chatActivo);
    const historial = chat ? chat.mensajes
      .filter(m => !m.texto.startsWith("📋"))   // excluir mensaje de caso clínico
      .slice(0, -1)                              // excluir el último (que ya va como new_message)
      .map(m => ({
        role: m.rol === "user" ? "user" : "assistant",
        content: m.texto
      })) : [];

    try {
      const res = await fetch(`${ENV.API_BASE}/api/chat/message`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include",
          body: JSON.stringify({ history: historial, message: texto })
      });

      if (!res.ok) throw new Error(`Error ${res.status}`);

      const data = await res.json();
      const respuesta = data.reply || "Sin respuesta";

      ChatService.agregarMensaje(this.chatActivo, "ai", respuesta);
      this.cargarChat(this.chatActivo);

    } catch (err) {
      console.error("Error al enviar mensaje:", err);
      ChatService.agregarMensaje(this.chatActivo, "ai", "⚠️ Error al conectar con el servidor.");
      this.cargarChat(this.chatActivo);
    }
  }


  cargarChat(id) {
    this.chatActivo = id;
    const chats = ChatService.cargarChats();
    const chat = chats.find(c => c.id === id);
    if (!chat) return;

    const area = document.querySelector("#area-chat");
    area.innerHTML = "";

    chat.mensajes.forEach(msg => {
      const div = document.createElement("div");
      div.className = msg.rol === "user" ? "mensaje-user" : "mensaje-ai";
      if (msg.rol === "user") {
        div.textContent = msg.texto;          // usuario: texto plano (seguro)
      } else {
        div.innerHTML = marked.parse(msg.texto);  // IA: renderizar markdown
      }
      area.appendChild(div);
    });

    this.mostrarHistorial();
    area.scrollTop = area.scrollHeight;
  }

  mostrarHistorial() {
    const chats = ChatService.cargarChats();
    const cont = document.querySelector("#historial");
    if (!cont) return;

    cont.innerHTML = "";

    chats.forEach(chat => {
      const btn = document.createElement("button");
      btn.className = "chat-item";
      if (chat.id === this.chatActivo) btn.classList.add("active");

      btn.innerHTML = `
        <span>${chat.titulo}</span>
        <span class="delete-btn">✕</span>
      `;

      btn.onclick = () => this.cargarChat(chat.id);

      btn.querySelector(".delete-btn").onclick = e => {
        e.stopPropagation();
        const nextId = ChatService.eliminarChat(chat.id);
        if (nextId) this.cargarChat(nextId);
        else this.mostrarHistorial();
      };

      cont.appendChild(btn);
    });
  }
}

document.addEventListener("DOMContentLoaded", () => new ChatPage());
