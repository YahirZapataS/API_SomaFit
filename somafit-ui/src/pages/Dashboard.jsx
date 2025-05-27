import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

function Dashboard({ token, role, logout }) {
  const navigate = useNavigate();
  const [users, setUsers] = useState([]);
  const [subscriptions, setSubscriptions] = useState([]);
  const [form, setForm] = useState({ userId: '', type: '', startDate: '', endDate: '', amount: '' });

  const getUserNameByUser = (userObj) => {
    if (!userObj || !userObj.id) return 'Usuario desconocido';
    const user = users.find(u => u.id === userObj.id);
    return user ? user.name : 'Usuario desconocido';
  };

  const cargarUsuarios = async () => {
    try {
      const res = await fetch('http://localhost:8080/users', {
        headers: { Authorization: `Bearer ${token}` }
      });
      const data = await res.json();
      setUsers(data);
    } catch (err) {
      console.error('Error al cargar usuarios', err);
    }
  };

  const eliminarUsuario = async (id) => {
    if (!window.confirm('¿Estás seguro de eliminar este usuario?')) return;
    try {
      await fetch(`http://localhost:8080/users/${id}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` }
      });
      setUsers(users.filter(u => u.id !== id));
    } catch (err) {
      console.error('Error al eliminar usuario', err);
    }
  };

  const cargarSuscripciones = async () => {
    try {
      const res = await fetch('http://localhost:8080/subscriptions', {
        headers: { Authorization: `Bearer ${token}` }
      });
      const data = await res.json();
      setSubscriptions(data);
    } catch (err) {
      console.error('Error al cargar suscripciones', err);
    }
  };

  const registrarSuscripcion = async () => {
    try {
      const res = await fetch(`http://localhost:8080/subscriptions/register/${form.userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
          type: form.type,
          startDate: form.startDate,
          endDate: form.endDate,
          amount: parseFloat(form.amount)
        })
      });
      if (res.ok) {
        cargarSuscripciones();
        setForm({ userId: '', type: '', startDate: '', endDate: '', amount: '' });
        alert('Suscripción registrada');
      } else {
        alert('Error al registrar');
      }
    } catch (err) {
      console.error('Error al registrar suscripción', err);
    }
  };

  useEffect(() => {
    if (role === 'ADMIN') {
      cargarUsuarios();
      cargarSuscripciones();
    }
  }, []);

  return (
    <div className="min-h-screen flex">
      <aside className="w-64 bg-gray-800 text-white p-4">
        <h2 className="text-2xl font-bold mb-6">SOMAFIT</h2>
        <nav className="flex flex-col gap-3">
          <button className="text-left hover:text-blue-300" onClick={() => navigate('/')}>Inicio</button>
          {role === 'ADMIN' && (
            <>
              <button className="text-left hover:text-blue-300" onClick={() => navigate('/register')}>
                Registrar Usuario
              </button>
              <button className="text-left hover:text-blue-300" onClick={cargarUsuarios}>
                Ver Usuarios
              </button>
              <button className="text-left hover:text-blue-300" onClick={cargarSuscripciones}>
                Ver Suscripciones
              </button>
            </>
          )}
          <button className="text-left hover:text-blue-300" onClick={logout}>Cerrar sesión</button>
        </nav>
      </aside>
      <main className="flex-1 p-8">
        <h1 className="text-3xl font-bold mb-4">Bienvenido, {role}</h1>

        {role === 'ADMIN' && users.length > 0 && (
          <div className="mt-6">
            <h2 className="text-xl font-semibold mb-2">Usuarios registrados:</h2>
            <ul className="space-y-2">
              {users.map(u => (
                <li key={u.id} className="bg-white p-4 rounded shadow flex justify-between items-center">
                  <div>
                    <p className="font-medium">{u.name} ({u.username})</p>
                    <p className="text-sm text-gray-600">Rol: {u.role}</p>
                  </div>
                  <button
                    onClick={() => eliminarUsuario(u.id)}
                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                  >
                    Eliminar
                  </button>
                </li>
              ))}
            </ul>
          </div>
        )}

        {role === 'ADMIN' && (
          <div className="mt-10">
            <h2 className="text-xl font-semibold mb-2">Registrar Suscripción</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <select
                className="border px-3 py-2 rounded"
                value={form.userId}
                onChange={e => setForm({ ...form, userId: e.target.value })}
              >
                <option value="">Selecciona usuario</option>
                {users.map(u => (
                  <option key={u.id} value={u.id}>{u.name}</option>
                ))}
              </select>
              <input type="text" placeholder="Tipo" className="border px-3 py-2 rounded" value={form.type} onChange={e => setForm({ ...form, type: e.target.value })} />
              <input type="date" className="border px-3 py-2 rounded" value={form.startDate} onChange={e => setForm({ ...form, startDate: e.target.value })} />
              <input type="date" className="border px-3 py-2 rounded" value={form.endDate} onChange={e => setForm({ ...form, endDate: e.target.value })} />
              <input type="number" placeholder="Monto" className="border px-3 py-2 rounded" value={form.amount} onChange={e => setForm({ ...form, amount: e.target.value })} />
              <button className="bg-green-600 text-white py-2 px-4 rounded" onClick={registrarSuscripcion}>
                Guardar Suscripción
              </button>
            </div>
          </div>
        )}

        {role === 'ADMIN' && subscriptions.length > 0 && (
          <div className="mt-10">
            <h2 className="text-xl font-semibold mb-2">Suscripciones registradas:</h2>
            <ul className="space-y-2">
              {subscriptions.map(s => (
                <li key={s.id} className="bg-white p-4 rounded shadow">
                  <p><span className="font-medium">Usuario:</span> {getUserNameByUser(s.user)}</p>
                  <p><span className="font-medium">Tipo:</span> {s.type}</p>
                  <p><span className="font-medium">Fecha inicio:</span> {s.startDate}</p>
                  <p><span className="font-medium">Fecha fin:</span> {s.endDate}</p>
                  <p><span className="font-medium">Monto:</span> ${s.amount}</p>
                </li>
              ))}
            </ul>
          </div>
        )}

        {role !== 'ADMIN' && <p className="text-gray-600">Selecciona una opción del menú para comenzar.</p>}
      </main>
    </div>
  );
}

export default Dashboard;