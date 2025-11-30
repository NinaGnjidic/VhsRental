export async function fetchPost(path, body) {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}

export async function fetchGet(path) {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
  })
}

export async function fetchDelete(path, body) {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}

export async function fetchPut(path, body) {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}

export async function fetchPatch(path, body) {
  return await fetch(import.meta.env.VITE_API_URL + path, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    },
    body: JSON.stringify(body),
  })
}
