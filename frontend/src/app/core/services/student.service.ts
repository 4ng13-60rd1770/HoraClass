import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { StudentRequest, StudentResponse } from '../models/student.models';

@Injectable({ providedIn: 'root' })
export class StudentService {
  private url = `${environment.apiUrl}/admin/estudiantes`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<StudentResponse[]> {
    return this.http.get<StudentResponse[]>(this.url);
  }

  /** Perfil del estudiante autenticado (ROLE_ESTUDIANTE) */
  getProfile(): Observable<StudentResponse> {
    return this.http.get<StudentResponse>(`${environment.apiUrl}/estudiante/perfil`);
  }

  getById(id: number): Observable<StudentResponse> {
    return this.http.get<StudentResponse>(`${this.url}/${id}`);
  }

  create(request: StudentRequest): Observable<StudentResponse> {
    return this.http.post<StudentResponse>(`${this.url}/create`, request);
  }

  update(id: number, request: StudentRequest): Observable<StudentResponse> {
    return this.http.put<StudentResponse>(`${this.url}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
