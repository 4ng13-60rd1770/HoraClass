export const STORAGE_JWT_KEY = 'horaclass.jwt'
export const STORAGE_USER_KEY = 'horaclass.username'
export const STORAGE_ROLES_KEY = 'horaclass.roles'

export function saveSession(data: {
  jwtToken: string
  username: string
  roles: string[]
}): void {
  sessionStorage.setItem(STORAGE_JWT_KEY, data.jwtToken)
  sessionStorage.setItem(STORAGE_USER_KEY, data.username)
  sessionStorage.setItem(STORAGE_ROLES_KEY, JSON.stringify(data.roles))
}

export function clearSession(): void {
  sessionStorage.removeItem(STORAGE_JWT_KEY)
  sessionStorage.removeItem(STORAGE_USER_KEY)
  sessionStorage.removeItem(STORAGE_ROLES_KEY)
}

export function getStoredToken(): string | null {
  return sessionStorage.getItem(STORAGE_JWT_KEY)
}
