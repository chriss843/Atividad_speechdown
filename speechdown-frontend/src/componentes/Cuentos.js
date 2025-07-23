import React, { useState } from "react";
import "../styles/Cuentos.css";
import AnimalIcon from "../img/Animal.png";
import MontañaIcon from "../img/Montaña.png";
import SolIcon from "../img/Sol.png";
import MarIcon from "../img/Mar.png";
import BosqueIcon from "../img/bosque.png";
import MagoIcon from "../img/mago.png";
import LluviaIcon from "../img/Lluvia.png";
import { useTextToSpeech } from "../hooks/useTextToSpeech";

export default function Cuentos({ ninoId }) {
  const [nombre, setNombre] = useState("");
  const [categorias, setCategorias] = useState([]);
  const [mensaje, setMensaje] = useState("");
  const [cuentoGenerado, setCuentoGenerado] = useState("");


  const { 
    speak, 
    stop, 
    isPlaying, 
    currentWord,
    toggleHighlight,
    highlightEnabled
  } = useTextToSpeech();

  const opciones = [
    { id: "animales", titulo: "Animales", icono: AnimalIcon },
    { id: "montana", titulo: "Montaña", icono: MontañaIcon },
    { id: "soleado", titulo: "Soleado", icono: SolIcon },
    { id: "mar", titulo: "Mar", icono: MarIcon },
    { id: "bosque", titulo: "Bosque", icono: BosqueIcon },
    { id: "magia", titulo: "Magia", icono: MagoIcon },
    { id: "lluvia", titulo: "Lluvia", icono: LluviaIcon },
  ];

  const toggleCategoria = (id) => {
    setCategorias((prev) =>
      prev.includes(id) ? prev.filter((c) => c !== id) : [...prev, id]
    );
  };

  const generarCuento = async () => {
    if (!nombre || categorias.length === 0) {
      alert("Por favor ingresa el nombre y selecciona al menos una categoría.");
      return;
    }
  
    try {
      const response = await fetch("http://localhost:8080/api/cuentos/generar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, categorias }),
      });
  
      const data = await response.json();
      setCuentoGenerado(data.cuento);
      setMensaje("");
  
      // Guardar en historial
      await fetch("http://localhost:8080/api/historial", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ninoId: 1, // ID del niño seleccionado (puede ser dinámico)
          tipo: "cuento",
          categorias: categorias,
          contenido: data.cuento,
        }),
      });
  
      // Actualizar progreso
      await fetch("http://localhost:8080/api/progreso/actualizar/1?tipo=cuento", {
        method: "POST",
      });
      
    } catch (error) {
      console.error("Error generando cuento:", error);
      setMensaje("Ocurrió un error al generar el cuento.");
      setCuentoGenerado("");
    }
  };
  

  const leerCuento = () => {
    if (cuentoGenerado) {
      if (isPlaying) {
        stop();
      } else {
        speak(cuentoGenerado);
      }
    }
  };


  // Función para resaltar palabras
  const renderCuentoConResaltado = () => {
    if (!cuentoGenerado) return null;
    
    return cuentoGenerado.split(' ').map((word, index) => (
      <span 
        key={index} 
        className={highlightEnabled && currentWord === index ? 'palabra-resaltada' : ''}
      >
        {word}{' '}
      </span>
    ));
  };

  return (
    <div className="cuentos-container">
      <h1>Generador de Cuentos</h1>
      <p className="subtitulo-cuento">
        Crea un cuento personalizado para fomentar la imaginación y el desarrollo del lenguaje.
      </p>

      {/* Nombre */}
      <div className="nombre-container-cuento">
        <label htmlFor="nombre">Nombre del niño/a:</label>
        <input
          id="nombre"
          type="text"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          placeholder="Escribe el nombre aquí"
        />
      </div>

      {/* Selección de categorías */}
      <h2 className="categoria-titulo-cuento">Elige los elementos para tu cuento:</h2>
      <div className="categorias-grid-cuento">
        {opciones.map((op) => (
          <div
            key={op.id}
            className={`categoria-card-cuento ${
              categorias.includes(op.id) ? "seleccionado" : ""
            }`}
            onClick={() => toggleCategoria(op.id)}
          >
            <img src={op.icono} alt={op.titulo} />
            <h3>{op.titulo}</h3>
          </div>
        ))}
      </div>

      {/* Botón generar cuento */}
      <div className="boton-container-cuento">
        <button className="btn-generar-cuento" onClick={generarCuento}>
          Crear mi cuento
        </button>
      </div>

      {/* Mensaje de error */}
      {mensaje && <div className="mensaje-cuento">{mensaje}</div>}

      {/* Cuento generado */}
      {cuentoGenerado && (
        <div className="cuento-resultado">
          <div className="controles-audio">
            <button 
              onClick={leerCuento}
              className={`btn-escuchar ${isPlaying ? 'playing' : ''}`}
            >
              {isPlaying ? (
                <>
                  <i className="fas fa-stop"></i> Detener
                </>
              ) : (
                <>
                  <i className="fas fa-volume-up"></i> Escuchar cuento
                </>
              )}
            </button>
            
            <label className="switch">
              <input 
                type="checkbox" 
                checked={highlightEnabled}
                onChange={toggleHighlight}
              />
              <span className="slider round"></span>
              <span className="switch-label">Resaltar palabras</span>
            </label>
          </div>
          
          <h2>Tu cuento especial:</h2>
          <div className="texto-cuento">
            {highlightEnabled ? renderCuentoConResaltado() : cuentoGenerado}
          </div>
        </div>
      )}
    </div>
  );
}