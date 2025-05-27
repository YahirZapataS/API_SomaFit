import { useState } from 'react';

function Register() {
  const [form, setForm] = useState({ name: '', username: '', password: '', role: 'RECEPCIONISTA' });
  const [message, setMessage] = useState(null);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      const res = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form)
      });
      if (res.ok) {
        setMessage('Usuario registrado exitosamente');
        setForm({ name: '', username: '', password: '', role: 'RECEPCIONISTA' });
      } else {
        setMessage('Error al registrar usuario');
      }
    } catch (err) {
      setMessage('Error al conectar con la API');
    }
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="bg-white p-6 rounded shadow-md w-96">
        <h2 className="text-xl font-semibold mb-4 text-center">Registrar Usuario</h2>
        <input name="name" placeholder="Nombre" value={form.name} onChange={handleChange} className="w-full px-3 py-2 border rounded mb-2" />
        <input name="username" placeholder="Usuario" value={form.username} onChange={handleChange} className="w-full px-3 py-2 border rounded mb-2" />
        <input type="password" name="password" placeholder="Contraseña" value={form.password} onChange={handleChange} className="w-full px-3 py-2 border rounded mb-2" />
        <select name="role" value={form.role} onChange={handleChange} className="w-full px-3 py-2 border rounded mb-4">
          <option value="RECEPCIONISTA">Recepcionista</option>
          <option value="ADMIN">Administrador</option>
          <option value="USER">Usuario</option>
        </select>
        {message && <p className="text-center text-sm mb-2">{message}</p>}
        <button onClick={handleSubmit} className="bg-green-600 text-white w-full py-2 rounded hover:bg-green-700">
          Registrar
        </button>
      </div>
    </div>
  );
}

export default Register;