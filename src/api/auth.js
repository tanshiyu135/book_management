import axios from 'axios'

export function login(data) {
  return axios.post('/api/user/login', data).then(res => res.data)
}

export function register(data) {
  return axios.post('/api/user/register', data).then(res => res.data)
}
