import React, { useState } from "react";
import '../styles/Login.css';


export default function Login({ onLoginSuccess, cambiarVista }) {
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, contrasena }),
      });
      const data = await res.json();
      if (res.ok) {
        const usuario = {
          email: email,
          rol: data.rol,
        };
        onLoginSuccess({ usuario, mensaje: data.mensaje });
      } else {
        setError(data.mensaje || "Error al iniciar sesión");
      }
    } catch (err) {
      setError("Error de conexión");
    }
  };

  return (
    <div className="login-container">
      <h2>🔐 Iniciar Sesión</h2>
      <form className="form-container" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">👤 Usuario:</label>
          <input
            type="email"
            id="email"
            name="email"
            className="form-control"
            placeholder="correo@ejemplo.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            autoComplete="username"
          />
        </div>
        <div className="form-group">
          <label htmlFor="contrasena">🔒 Contraseña:</label>
          <input
            type="password"
            id="contrasena"
            name="contrasena"
            className="form-control"
            placeholder="Contraseña"
            value={contrasena}
            onChange={(e) => setContrasena(e.target.value)}
            required
            autoComplete="current-password"
          />
        </div>
        {error && <div className="alert alert-danger">⚠️ {error}</div>}
        <button type="submit" className="btn btn-primary" style={{ width: "100%" }}>
          🚀 Ingresar al Sistema
        </button>
      </form>
      <span
        className="link"
        style={{ marginTop: "15px", display: "block", textAlign: "center", cursor: "pointer", color: "#3498db" }}
        onClick={() => cambiarVista("registro")}
      >
        ¿No tienes cuenta? Regístrate
      </span>
    </div>
  );
}
