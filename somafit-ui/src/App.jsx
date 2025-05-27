import { useState } from 'react'
import Login from './pages/Login'

function App() {
  const [token, setToken] = useState(null);
  const [role, setRole] = useState(null);

  return (
    <div className="min-h-screen bg-gray-100">
      {!token
        ? <Login setToken={setToken} setRole={setRole} />
        : <Dashboard token={token} role={role} logout={() => setToken(null)} />
      }
    </div>
  )
}

export default App;