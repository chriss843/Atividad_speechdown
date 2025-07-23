import React, { useState } from "react";
import Login from "./componentes/Login";
import Registro from "./componentes/Registro";
import MenuPrincipal from "./componentes/MenuPrincipal";
import "./styles.css";

function App() {
  const [vista, setVista] = useState("login"); // "login" o "registro" o "menu"
  const [usuario, setUsuario] = useState(null);

  const manejarLoginExitoso = (data) => {
    // Suponiendo que la respuesta tiene { usuario: {...}, token: '...' }
    setUsuario(data.usuario);
    setVista("menu");
  };

  const manejarLogout = () => {
    setUsuario(null);
    setVista("login");
  };

  return (
    <>
      {vista === "login" && (
        <Login onLoginSuccess={manejarLoginExitoso} cambiarVista={setVista} />
      )}
      {vista === "registro" && <Registro cambiarVista={setVista} />}
      {vista === "menu" && usuario && (
        <MenuPrincipal usuario={usuario} onLogout={manejarLogout} />
      )}
    </>
  );
}

export default App;
