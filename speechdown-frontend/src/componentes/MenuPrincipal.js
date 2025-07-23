import React, { useState } from "react";
import Cuentos from "./Cuentos";
import Actividades from "./Actividades"; 
import Perfiles from "./Perfiles";
import Dashboard from "./Dashboard";


import "../styles/MenuPrincipal.css";

export default function MenuPrincipal({ usuario, onLogout }) {
  const [seccionActiva, setSeccionActiva] = useState("inicio");
  const [ninoSeleccionado, setNinoSeleccionado] = useState([]); 

  return (
    <div className="app-layout">
      {/* Sidebar */}
      <nav className="sidebar">
        <div className="sidebar-header">
          <h2>Sistema</h2>
          <p>👤 {usuario.email}</p>
          <p>Rol: {usuario.rol}</p>
        </div>
        <ul className="sidebar-menu">
          <li
            className={seccionActiva === "perfil" ? "active" : ""}
            onClick={() => setSeccionActiva("perfil")}
            
          >
            Perfil
          </li>
          <li
            className={seccionActiva === "dashboard" ? "active" : ""}
            onClick={() => setSeccionActiva("dashboard")}
          >
            Dashboard
          </li>
          <li
            className={seccionActiva === "perfiles" ? "active" : ""}
            onClick={() => setSeccionActiva("perfiles")}
          >
            Perfiles
          </li>
          <li
            className={seccionActiva === "actividades" ? "active" : ""}
            onClick={() => setSeccionActiva("actividades")}
          >
            Actividades
          </li>
          <li
            className={seccionActiva === "cuentos" ? "active" : ""}
            onClick={() => setSeccionActiva("cuentos")}
          >
            Cuentos
          </li>
          <li className="logout" onClick={onLogout}>
            Cerrar Sesión
          </li>
        </ul>
      </nav>

      {/* Main area */}
      <div className="main-area">
        <header className="header">
          <h1>
            {seccionActiva.charAt(0).toUpperCase() + seccionActiva.slice(1)}
          </h1>
        </header>

        <section className="content">
          {seccionActiva === "inicio" && <p>Bienvenido al sistema.</p>}
          {seccionActiva === "perfil" && (
            <div className="profile-card">
              <h3 className="profile-title">Tu perfil</h3>
              <div className="profile-info">
                <div className="info-item">
                  <span className="info-label">Email:</span>
                  <span className="info-value">{usuario.email}</span>
                </div>
                <div className="info-item">
                  <span className="info-label">Rol:</span>
                  <span className="info-value">{usuario.rol}</span>
                </div>
              </div>
            </div>
          )}
          
          {seccionActiva === "actividades" && <Actividades ninoId={ninoSeleccionado} />}
          {seccionActiva === "cuentos" && <Cuentos ninoId={ninoSeleccionado} />}
          {seccionActiva === "perfiles" && <Perfiles onSelectNino={setNinoSeleccionado} />}
          {seccionActiva === "dashboard" && <Dashboard ninoId={ninoSeleccionado} />}
        </section>

        <footer className="footer">
          <p>© 2025 Sistema de Gestión. Todos los derechos reservados.</p>
        </footer>
      </div>
    </div>
  );
}
