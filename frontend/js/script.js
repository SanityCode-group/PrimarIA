// ===========================
//   LOCAL STORAGE
// ===========================

function cargarChats() {
  return JSON.parse(localStorage.getItem("mis_chats")) || [];
}

function guardarChats(chats) {
  localStorage.setItem("mis_chats", JSON.stringify(chats));
}

// ===========================
//   NUEVO CHAT
// ===========================

function nuevoChat() {
  const chats = cargarChats();

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
      texto: `📋 Caso clínico cargado:\nPaciente ${caso.paciente.nombre}, ${caso.paciente.edad} años.\nMotivo: ${caso.descripcion}`
    });
  }

  chats.push(nuevo);
  guardarChats(chats);
  cargarChat(nuevo.id);
}

// ===========================
//   MENSAJES
// ===========================

function agregarMensaje(chatId, rol, texto) {
  const chats = cargarChats();
  const chat = chats.find(c => c.id === chatId);
  if (!chat) return;

  chat.mensajes.push({ rol, texto });

  if (chat.mensajes.length === 1) {
    chat.titulo = texto.substring(0, 30) + "...";
  }

  guardarChats(chats);
}

// ===========================
//   HISTORIAL
// ===========================

function mostrarHistorial() {
  const chats = cargarChats();
  const cont = document.querySelector("#historial");
  cont.innerHTML = "";

  chats.forEach(chat => {
    const btn = document.createElement("button");
    btn.className = "chat-item";
    if (chat.id === window.chatActivo) btn.classList.add("active");

    btn.innerHTML = `
      <span>${chat.titulo}</span>
      <span class="delete-btn">✕</span>
    `;

    btn.onclick = () => cargarChat(chat.id);

    btn.querySelector(".delete-btn").onclick = e => {
      e.stopPropagation();
      eliminarChat(chat.id);
    };

    cont.appendChild(btn);
  });
}

// ===========================
//   CARGAR CHAT
// ===========================

function cargarChat(id) {
  window.chatActivo = id;

  const chats = cargarChats();
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

  mostrarHistorial();
}

// ===========================
//   ELIMINAR CHAT
// ===========================

function eliminarChat(id) {
  let chats = cargarChats();
  chats = chats.filter(c => c.id !== id);
  guardarChats(chats);

  if (chats.length) cargarChat(chats[0].id);
  else mostrarHistorial();
}

// ===========================
//   ENVIAR MENSAJE
// ===========================

document.addEventListener("DOMContentLoaded", () => {
  document.querySelector("#btn-enviar").onclick = () => {
    const input = document.querySelector("#input-texto");
    const texto = input.value.trim();
    if (!texto || !window.chatActivo) return;

    agregarMensaje(window.chatActivo, "user", texto);
    cargarChat(window.chatActivo);
    input.value = "";
  };

  const chats = cargarChats();
  if (chats.length === 0) nuevoChat();
  else cargarChat(chats[0].id);
});
