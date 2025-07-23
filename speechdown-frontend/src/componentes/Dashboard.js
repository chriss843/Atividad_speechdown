import React, { useEffect, useState } from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import axios from "axios";
import "../styles/Dashboard.css";

export default function Dashboard() {
  const [chartData, setChartData] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/progreso")
      .then((response) => {
        // Convertimos la respuesta para el gráfico
        const data = response.data.map((item) => ({
          nombre: item.nino.nombre,
          cuentos: item.totalCuentos,
          actividades: item.totalActividades,
        }));
        setChartData(data);
      })
      .catch((error) => {
        console.error("Error al obtener los datos del progreso:", error);
      });
  }, []);

  return (
    <div className="dashboard-container">
      <h2>Panel de Progreso</h2>

      {chartData.length > 0 ? (
        <ResponsiveContainer width="100%" height={400}>
          <BarChart
            data={chartData}
            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
          >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="nombre" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="cuentos" fill="#8884d8" />
            <Bar dataKey="actividades" fill="#82ca9d" />
          </BarChart>
        </ResponsiveContainer>
      ) : (
        <p>No hay datos de progreso disponibles.</p>
      )}
    </div>
  );
}
