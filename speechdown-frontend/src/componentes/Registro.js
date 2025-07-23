import React, { useState } from "react";
import '../styles/Registro.css';

export default function Registro({ cambiarVista }) {
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [rol, setRol] = useState("TERAPEUTA");
  const [error, setError] = useState(null);
  const [exito, setExito] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setExito(null);
    try {
      const res = await fetch("http://localhost:8080/api/auth/registrar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, contrasena, rol }),
      });
      const data = await res.json();
      if (res.ok) {
        setExito("Registro exitoso. Ahora puedes iniciar sesión.");
        setEmail("");
        setContrasena("");
        setRol("TERAPEUTA");
      } else {
        setError(data.mensaje || "Error en el registro");
      }
    } catch (err) {
      setError("Error de conexión");
    }
  };

  return (
    <div className="login-container">
      <h2>📝 Registrar Usuario</h2>
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
            autoComplete="new-password"
          />
        </div>
        <div className="form-group">
          <label htmlFor="rol">Rol:</label>
          <select
            id="rol"
            name="rol"
            className="form-control"
            value={rol}
            onChange={(e) => setRol(e.target.value)}
            required
          >
            <option value="TERAPEUTA">Terapeuta</option>
            <option value="PADRE">Padre</option>
          </select>
        </div>
        {error && <div className="alert alert-danger">⚠️ {error}</div>}
        {exito && <div className="alert alert-success">{exito}</div>}
        <button type="submit" className="btn btn-primary" style={{ width: "100%" }}>
          ✅ Registrar
        </button>
      </form>
      <span
        className="link"
        style={{ marginTop: "15px", display: "block", textAlign: "center", cursor: "pointer", color: "#3498db" }}
        onClick={() => cambiarVista("login")}
      >
        ¿Ya tienes cuenta? Inicia sesión
      </span>
    </div>
  );
}
