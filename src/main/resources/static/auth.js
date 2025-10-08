document.addEventListener("DOMContentLoaded", initAuth);

function initAuth() {
  const form = document.getElementById("form");
  const msg = document.getElementById("msg");
  const toggleText = document.getElementById("toggle-text");
  const confirmGroup = document.getElementById("confirm-password-group");
  const submitBtn = document.getElementById("submit-btn");

  if (!form || !msg || !toggleText || !confirmGroup || !submitBtn) {
    console.error("Error: faltan elementos del formulario de autenticación.");
    return;
  }

  let isRegister = false;

  toggleText.addEventListener("click", (e) => {
    if (e.target && e.target.id === "toggle-btn") {
      isRegister ? toggleToLogin() : toggleToRegister();
    }
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username")?.value.trim();
    const password = document.getElementById("password")?.value.trim();
    const confirmPassword = document.getElementById("confirmPassword")?.value.trim();

    if (!username || !password) {
      return showMsg(msg, "Por favor completa todos los campos.", "error");
    }

    if (isRegister && password !== confirmPassword) {
      return showMsg(msg, "Las contraseñas no coinciden.", "error");
    }

    if (isRegister) {
      await handleRegister(username, password, msg);
    } else {
      await handleLogin(username, password, msg);
    }
  });

  function toggleToRegister() {
    isRegister = true;
    confirmGroup.classList.remove("hidden");
    submitBtn.textContent = "Registrarse";
    toggleText.innerHTML = `
      ¿Ya tienes una cuenta?
      <button id="toggle-btn" class="link-btn">Inicia sesión aquí</button>
    `;
  }

  function toggleToLogin() {
    isRegister = false;
    confirmGroup.classList.add("hidden");
    submitBtn.textContent = "Iniciar sesión";
    toggleText.innerHTML = `
      ¿No tienes una cuenta?
      <button id="toggle-btn" class="link-btn">Regístrate aquí</button>
    `;
  }
}

async function handleRegister(username, password, msg) {
  try {
    const response = await fetch("https://sgrlab07back.duckdns.org:8443/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email: username, password }),
    });

    if (response.ok) {
      showMsg(msg, "Registro exitoso. Ahora inicia sesión.", "success");
      document.getElementById("confirm-password-group")?.classList.add("hidden");
      document.getElementById("submit-btn").textContent = "Iniciar sesión";
      const toggleText = document.getElementById("toggle-text");
      toggleText.innerHTML = `
        ¿No tienes una cuenta?
        <button id="toggle-btn" class="link-btn">Regístrate aquí</button>
      `;
    } else {
      const errorText = await response.text();
      showMsg(msg, "Error en el registro: " + errorText, "error");
    }
  } catch (error) {
    showMsg(msg, "Error en la conexión: " + error.message, "error");
  }
}

async function handleLogin(username, password, msg) {
  try {
    const response = await fetch("https://sgrlab07back.duckdns.org:8443/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email: username, password }),
    });

    if (response.ok) {
      const data = await response.text();
      showMsg(msg, "Inicio de sesión exitoso", "success");
      setTimeout(() => (window.location.href = "index.html"), 1000);
    } else {
      const errorText = await response.text();
      showMsg(msg, "Error en el login: " + errorText, "error");
    }
  } catch (error) {
    showMsg(msg, "Error en la conexión: " + error.message, "error");
  }
}

function showMsg(msgElement, text, type) {
  if (!msgElement) {
    alert(text);
    return;
  }
  msgElement.textContent = text;
  msgElement.className = type;
}
