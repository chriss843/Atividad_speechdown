import React, { useEffect, useState } from "react";
import "../styles/Perfiles.css";

export default function Perfiles({ onSelectNino }) {
  const [ninos, setNinos] = useState([]);
  const [nuevoNombre, setNuevoNombre] = useState("");
  const [nuevaEdad, setNuevaEdad] = useState(6);

  // Cargar niños desde el backend
  useEffect(() => {
    fetch("http://localhost:8080/api/ninos")
      .then((res) => res.json())
      .then((data) => setNinos(data))
      .catch((err) => console.error("Error cargando niños:", err));
  }, []);

  // Agregar nuevo niño
  const agregarNino = async () => {
    if (!nuevoNombre) return alert("Escribe un nombre");

    try {
      const response = await fetch("http://localhost:8080/api/ninos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre: nuevoNombre, edad: nuevaEdad }),
      });

      const data = await response.json();
      setNinos((prev) => [...prev, data]);
      setNuevoNombre("");
      setNuevaEdad(6);
    } catch (error) {
      console.error("Error agregando niño:", error);
    }
  };

  // Eliminar niño
  const eliminarNino = async (id) => {
    if (!window.confirm("¿Eliminar este perfil?")) return;

    try {
      await fetch(`http://localhost:8080/api/ninos/${id}`, {
        method: "DELETE",
      });
      setNinos((prev) => prev.filter((n) => n.id !== id));
    } catch (error) {
      console.error("Error eliminando niño:", error);
    }
  };

  return (
    <div className="perfiles-container">
      <h1>Perfiles de Niños</h1>
      <p className="subtitulo">Añadir un niño para las actividades.</p>

      {/* Lista de perfiles */}
      <div className="lista-perfiles">
        {ninos.map((nino) => (
          <div
            key={nino.id}
            className="perfil-card"
            onClick={() => onSelectNino(nino.id)}
          >
            <h3>{nino.nombre} ({nino.edad} años)</h3>
            <button
              className="btn-eliminar"
              onClick={(e) => {
                e.stopPropagation();
                eliminarNino(nino.id);
              }}
            >
              Eliminar
            </button>
          </div>
        ))}
      </div>

      {/* Formulario para nuevo perfil */}
      <div className="nuevo-perfil">
        <h2>Crear nuevo perfil</h2>
        <input
          type="text"
          placeholder="Nombre"
          value={nuevoNombre}
          onChange={(e) => setNuevoNombre(e.target.value)}
        />
        <h5>Ingrese la edad del niño:</h5>
        <input
          type="number"
          min="3"
          max="12"
          placeholder="Edad"
          value={nuevaEdad}
          onChange={(e) => setNuevaEdad(parseInt(e.target.value) || 6)}
        />
        <button className="btn-agregar" onClick={agregarNino}>
          Agregar Niño
        </button>
      </div>
    </div>
  );
}
