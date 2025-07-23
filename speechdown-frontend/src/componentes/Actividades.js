import React, { useState } from "react";
import "../styles/Actividades.css";

// ICONOS DE CATEGORÍAS (agrega tus imágenes en /img)
import JuegoIcon from "../img/juego.png";
import CancionIcon from "../img/cancion.png";
import RetoIcon from "../img/reto.png";
import DibujoIcon from "../img/dibujo.png";
import HistoriaIcon from "../img/historia.png";
import PalabraIcon from "../img/palabra.png";

export default function Actividades() {
  const [nombre, setNombre] = useState("");
  const [categorias, setCategorias] = useState([]);
  const [actividadGenerada, setActividadGenerada] = useState("");
  const [loading, setLoading] = useState(false);
  const [edad, setEdad] = useState(6); 

  const opciones = [
    { id: "juego", titulo: "Juego", icono: JuegoIcon },
    { id: "cancion", titulo: "Canción", icono: CancionIcon },
    { id: "reto", titulo: "Reto", icono: RetoIcon },
    { id: "dibujo", titulo: "Dibujo", icono: DibujoIcon },
    { id: "historia", titulo: "Historia", icono: HistoriaIcon },
    { id: "palabra", titulo: "Palabra Clave", icono: PalabraIcon },
  ];

  const toggleCategoria = (id) => {
    setCategorias((prev) =>
      prev.includes(id) ? prev.filter((c) => c !== id) : [...prev, id]
    );
  };

  const generarActividad = async () => {
    if (!nombre || categorias.length === 0) {
      alert("Por favor ingresa el nombre y selecciona al menos una categoría.");
      return;
    }
  
    setLoading(true);
    setActividadGenerada("");
  
    try {
      const response = await fetch("http://localhost:8080/api/actividades/generar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, categorias, edad }),
      });
  
      const data = await response.json();
  
      if (!response.ok) throw new Error(data.error || "Error al generar actividad");
  
      setActividadGenerada({
        contenido: data.actividad,
        esError: false,
        necesitaReintentar: false,
      });
  
      // Guardar en historial
      await fetch("http://localhost:8080/api/historial", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ninoId: 1, // ID del niño seleccionado
          tipo: "actividad",
          categorias: categorias,
          contenido: data.actividad,
        }),
      });
  
      // Actualizar progreso
      await fetch("http://localhost:8080/api/progreso/actualizar/1?tipo=actividad", {
        method: "POST",
      });
  
    } catch (error) {
      console.error("Error generando actividad:", error);
      const esErrorServidor = error.message.includes("503") || error.message.includes("overload");
      setActividadGenerada({
        contenido: esErrorServidor
          ? "¡Ups! Nuestro servidor está muy ocupado ahora. Por favor intenta de nuevo en 1-2 minutos."
          : "Ocurrió un error al generar la actividad. Por favor inténtalo más tarde.",
        esError: true,
        necesitaReintentar: esErrorServidor,
      });
    } finally {
      setLoading(false);
    }
  };
  

  const formatearActividad = (texto) => {
    if (!texto) return null;
  
    // Dividir el texto en líneas y filtrar las vacías
    const lineas = texto.split('\n').filter(linea => linea.trim() !== '');
  
    return lineas.map((linea, index) => {
      linea = linea.trim();
  
      // Detectar y formatear títulos principales
      if (/^(¡Actividad Lista|¡Absolutamente!|Nombre del|Objetivo:|Materiales:|Instrucciones:|Consideraciones)/i.test(linea)) {
        return <h4 key={index} className="section-title">{linea.replace(':', '')}</h4>;
      }
  
      // Detectar y formatear listas con viñetas
      if (/^[•*]\s/.test(linea)) {
        return <li key={index} className="list-item">{linea.replace(/^[•**]\s/, '')}</li>;
      }
  
      // Detectar y formatear instrucciones numeradas
      if (/^\d+\*.\s/.test(linea)) {
        return <li key={index} className="numbered-item">{linea.replace(/^\d+\.\s/, '')}</li>;
      }
  
      // Detectar subtítulos (texto en negrita)
      if (/\*\**.+?\**\*/.test(linea)) {
        const partes = linea.split(/\**\**(.+?)\**\**/);
        return (
          <p key={index} className="subtitle">
            {partes.map((parte, i) => 
              i % 2 === 1 ? <strong key={i}>{parte}</strong> : parte
            )}
          </p>
        );
      }
  
      // Texto normal
      return <p key={index} className="activity-text">{linea}</p>;
    });
  };

  return (
    <div className="actividades-container">
      <h1>Generador de Actividades</h1>
      <p className="subtitulo">
        Crea actividades personalizadas para estimular el aprendizaje y diversión de los niños.
      </p>

      {/* Nombre */}
      <div className="nombre-container">
        <label htmlFor="nombre">Nombre del niño/a:</label>
        <input
          id="nombre"
          type="text"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          placeholder="Escribe el nombre aquí"
        />
      </div>

      {/* Nueva sección para la edad */}
      <div className="edad-container">
        <label htmlFor="edad">Edad del niño/a:</label>
        <input
          id="edad"
          type="number"
          min="3"
          max="12"
          value={edad}
          onChange={(e) => setEdad(parseInt(e.target.value) || 6)}
        />
      </div>

      {/* Selección de categorías */}
      <h2 className="categoria-titulo">Elige los tipos de actividades:</h2>
      <div className="categorias-grid">
        {opciones.map((op) => (
          <div
            key={op.id}
            className={`categoria-card ${
              categorias.includes(op.id) ? "seleccionado" : ""
            }`}
            onClick={() => toggleCategoria(op.id)}
          >
            <img src={op.icono} alt={op.titulo} />
            <h3>{op.titulo}</h3>
          </div>
        ))}
      </div>

      {/* Botón generar actividad */}
      <div className="boton-container">
        <button className="btn-generar" onClick={generarActividad} disabled={loading}>
          {loading ? (
            <>
              <span className="loading-text">Generando...</span>
            </>
          ) : (
            "Crear actividad"
          )}
        </button>
      </div>

      {actividadGenerada && (
        <div className={`mensaje ${actividadGenerada.esError ? "error" : "exito"}`}>
          <h3>{actividadGenerada.esError ? "¡Ups! Algo salió mal" : `¡Actividad Lista para ${nombre} (${edad} años)!`}</h3>
          <div className="actividad-contenido">
            {formatearActividad(actividadGenerada.contenido)}
          </div>
          {actividadGenerada.necesitaReintentar && (
            <button onClick={generarActividad} className="btn-reintentar">
              Reintentar
            </button>
          )}
        </div>
      )}
    </div>
  );
}