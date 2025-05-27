import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Register from './pages/Register';
import UserSubscriptions from './pages/UserSubscriptions';

function App() {
  const [token, setToken] = useState(null);
  const [role, setRole] = useState(null);

  const logout = () => {
    setToken(null);
    setRole(null);
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={
          token ? (
            role === 'ADMIN' ? <Dashboard token={token} role={role} logout={logout} /> :
            role === 'USER' ? <UserSubscriptions token={token} /> :
            <Navigate to="/" />
          ) : <Login setToken={setToken} setRole={setRole} />
        } />
        <Route path="/register" element={<Register token={token} />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;