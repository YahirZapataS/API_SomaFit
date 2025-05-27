import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login({ setToken, setRole }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      const token = await res.text();
      const payload = JSON.parse(atob(token.split('.')[1]));

      setToken(token);
      setRole(payload.role);
      navigate('/');
    } catch (err) {
      setError('Credenciales inválidas');
    }
  }

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="bg-white p-6 rounded shadow-md w-80">
        <h2 className="text-xl font-semibold mb-4 text-center">SOMAFIT Login</h2>
        <input
          type="text"
          placeholder="Usuario"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full px-3 py-2 border rounded mb-2"
        />
        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full px-3 py-2 border rounded mb-2"
        />
        {error && <p className="text-red-500 text-sm mb-2">{error}</p>}
        <button
          onClick={handleLogin}
          className="bg-blue-600 text-white w-full py-2 rounded hover:bg-blue-700"
        >
          Ingresar
        </button>
        <p className="text-center mt-2 text-sm">
          ¿No tienes cuenta? <a href="/register" className="text-blue-600 hover:underline">Regístrate</a>
        </p>
      </div>
    </div>
  );
}

export default Login;