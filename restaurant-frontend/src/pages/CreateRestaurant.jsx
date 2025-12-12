import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api/axios'

export default function CreateRestaurant() {
  const [name, setName] = useState('')
  const [rating, setRating] = useState('')
  const [location, setLocation] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [checkingAuth, setCheckingAuth] = useState(true)

  const navigate = useNavigate()

  useEffect(() => {
    async function checkAuth() {
      try {
        const me = await api.get('/api/auth/me')

        // If user is not admin, redirect to restaurant list
        if (me.data.role !== 'ROLE_ADMIN') {
          navigate('/restaurant')
        }
      } catch (error) {
        console.error(error)
        navigate('/login')
      } finally {
        setCheckingAuth(false)
      }
    }

    checkAuth()
  }, [navigate])

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      await api.post('/api/restaurants', {
        name,
        rating: parseFloat(rating),
        location,
      })

      // After successful creation, redirect to restaurant list
      navigate('/restaurant')
    } catch (err) {
      console.error(err)
      const errorMessage = err.response?.data?.message || 'Failed to create restaurant. Please try again.'
      setError(errorMessage)
    } finally {
      setLoading(false)
    }
  }

  if (checkingAuth) {
    return <div style={{ padding: '2rem' }}>Loading...</div>
  }

  return (
    <div style={{ padding: '2rem', maxWidth: 400, margin: '0 auto' }}>
      <h1>Create New Restaurant</h1>

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
        <input
          type="text"
          placeholder="Restaurant Name"
          value={name}
          onChange={e => setName(e.target.value)}
          required
        />

        <input
          type="number"
          placeholder="Rating (1-5)"
          value={rating}
          onChange={e => setRating(e.target.value)}
          min="1"
          max="5"
          step="0.1"
          required
        />

        <input
          type="text"
          placeholder="Location"
          value={location}
          onChange={e => setLocation(e.target.value)}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? 'Creating…' : 'Create Restaurant'}
        </button>

        {error && <div style={{ color: 'red' }}>{error}</div>}
      </form>

      <p style={{ marginTop: '1rem' }}>
        <a href="/restaurant" style={{ textDecoration: 'underline' }}>
          Back to Restaurant List
        </a>
      </p>
    </div>
  )
}
