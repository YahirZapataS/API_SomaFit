// src/pages/UserSubscriptions.jsx
import { useEffect, useState } from 'react';

function UserSubscriptions({ token }) {
  const [subs, setSubs] = useState([]);

  const cargarMisSubs = async () => {
    try {
      const res = await fetch('http://localhost:8080/subscriptions/user/me', {
        headers: { Authorization: `Bearer ${token}` }
      });
      const data = await res.json();
      setSubs(data);
    } catch (err) {
      console.error('Error al cargar suscripciones del usuario', err);
    }
  };

  const renovar = async (id) => {
    const hoy = new Date();
    const nuevaInicio = hoy.toISOString().split('T')[0];
    const nuevaFin = new Date();
    nuevaFin.setMonth(nuevaFin.getMonth() + 1);
    const nuevaFechaFin = nuevaFin.toISOString().split('T')[0];

    try {
      await fetch(`http://localhost:8080/subscriptions/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
          startDate: nuevaInicio,
          endDate: nuevaFechaFin
        })
      });
      cargarMisSubs();
    } catch (err) {
      console.error('Error al renovar suscripción', err);
    }
  };

  useEffect(() => {
    cargarMisSubs();
  }, []);

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Mis Suscripciones</h2>
      <ul className="space-y-3">
        {subs.map(sub => {
          const vencida = new Date(sub.endDate) < new Date();
          return (
            <li key={sub.id} className="bg-white rounded p-4 shadow">
              <p><strong>Tipo:</strong> {sub.type}</p>
              <p><strong>Inicio:</strong> {sub.startDate}</p>
              <p><strong>Fin:</strong> {sub.endDate}</p>
              <p><strong>Monto:</strong> ${sub.amount}</p>
              {vencida && (
                <button onClick={() => renovar(sub.id)} className="mt-2 bg-green-600 text-white px-4 py-1 rounded">
                  Renovar
                </button>
              )}
            </li>
          );
        })}
      </ul>
    </div>
  );
}

export default UserSubscriptions;