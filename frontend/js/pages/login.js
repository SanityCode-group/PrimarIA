import { ENV } from '../services/config.js';

document.addEventListener("DOMContentLoaded", () => {
    const backendBase = ENV.API_BASE.replace("/api", "");
    const googleLink = document.querySelector(".google-link");
    
    if (googleLink) {
        googleLink.href = `${backendBase}/oauth2/authorization/google`;
    }

    // Check for error parameter
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    if (error === 'whitelist') {
        const errorMsg = document.createElement('p');
        errorMsg.textContent = 'Tu correo no está en la lista blanca. Contacta con el administrador.';
        errorMsg.style.color = '#ff4b2b';
        errorMsg.style.marginTop = '15px';
        errorMsg.style.fontWeight = 'bold';
        errorMsg.style.textAlign = 'center';
        
        const form = document.querySelector('#formRegistro');
        if (form) form.appendChild(errorMsg);
    }
});
