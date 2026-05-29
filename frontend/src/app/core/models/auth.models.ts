export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  jwtToken: string;
  username: string;
  roles: string[];
}

export interface StoredUser {
  username: string;
  roles: string[];
}

export interface MessageResponse {
  message: string;
}

export interface ResetPasswordRequest {
  token: string;
  newPassword: string;
}
