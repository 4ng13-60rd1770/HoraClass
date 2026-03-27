import { apiUrl } from './baseUrl'

export type LoginResponse = {
  jwtToken: string
  username: string
  roles: string[]
}

export type LoginErrorBody = {
  message?: string
  status?: boolean
}

export async function signIn(
  username: string,
  password: string,
): Promise<LoginResponse> {
  const res = await fetch(apiUrl('/api/auth/signin'), {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
    body: JSON.stringify({ username, password }),
  })

  let body: unknown
  try {
    body = await res.json()
  } catch {
    throw new Error('Respuesta inválida del servidor')
  }

  if (!res.ok) {
    const err = body as LoginErrorBody
    throw new Error(err.message ?? 'No se pudo iniciar sesión')
  }

  return body as LoginResponse
}
