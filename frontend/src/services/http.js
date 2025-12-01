export const fetchPost = async (path, body) => {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}

export const fetchGet = async (path) => {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
  })
}

export const fetchDelete = async (path, body) => {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}

export const fetchPut = async (path, body) => {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}

export const fetchPatch = async (path, body) => {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}
