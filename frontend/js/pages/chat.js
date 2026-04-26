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

    if (btnEnviar) {
      btnEnviar.onclick = () => this.enviarMensaje();
    }

    if (btnNuevo) {
      btnNuevo.onclick = () => this.cargarChat(ChatService.nuevoChat());
    }
  }

  enviarMensaje() {
    const input = document.querySelector("#input-texto");
    const texto = input.value.trim();
    if (!texto || !this.chatActivo) return;

    ChatService.agregarMensaje(this.chatActivo, "user", texto);
    this.cargarChat(this.chatActivo);
    input.value = "";
  }

  cargarChat(id) {
    this.chatActivo = id;
    const chats = ChatService.cargarChats();
    const chat = chats.find(c => c.id === id);
    if (!chat) return;

    const area = document.querySelector("#area-chat");
    area.innerHTML = "";

    chat.mensajes.forEach(msg => {
      const p = document.createElement("p");
      p.className = msg.rol === "user" ? "mensaje-user" : "mensaje-ai";
      p.textContent = msg.texto;
      area.appendChild(p);
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
