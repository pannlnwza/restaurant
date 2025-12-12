import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api/axios'

export default function Restaurant() {
  const [restaurants, setRestaurants] = useState([])
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    async function init() {
      try {
        const me = await api.get('/api/auth/me')
        setUser(me.data)

        const res = await api.get('/api/restaurants')
        setRestaurants(res.data.content)

      } catch (error) {
        console.error(error)
        navigate('/login') // if not authenticated, go to login
      } finally {
        setLoading(false)
      }
    }

    init()
  }, [navigate])

  async function handleLogout() {
    try {
        await api.post('/api/auth/logout')
    } catch (_) { }
        navigate('/login')
    }

  if (loading) {
    return <div style={{ padding: '2rem' }}>Loading...</div>
  }

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Restaurant List</h1>
      <p>Welcome, <strong>{user?.username}</strong> ({user?.role})</p>

      <div style={{ marginBottom: '1rem', display: 'flex', gap: '0.5rem' }}>
        {user?.role === 'ROLE_ADMIN' && (
          <button onClick={() => navigate('/restaurant/create')}>
            Create New Restaurant
          </button>
        )}
        <button onClick={handleLogout}>
          Logout
        </button>
      </div>

      <table border="1" cellPadding="8" style={{ marginTop: '1rem', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ background: '#eee' }}>
            <th>Name</th>
            <th>Rating</th>
            <th>Location</th>
          </tr>
        </thead>
        <tbody>
          {restaurants.map((r, idx) => (
            <tr key={idx}>
              <td>{r.name}</td>
              <td>{r.rating}</td>
              <td>{r.location}</td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  )
}
