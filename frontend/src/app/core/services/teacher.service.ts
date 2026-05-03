import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TeacherRequest, TeacherResponse } from '../models/teacher.models';

@Injectable({ providedIn: 'root' })
export class TeacherService {
  private url = `${environment.apiUrl}/admin/profesores`;

  constructor(private http: HttpClient) {}

  create(request: TeacherRequest): Observable<TeacherResponse> {
    return this.http.post<TeacherResponse>(this.url, request);
  }

  update(id: number, request: Partial<TeacherRequest>): Observable<TeacherResponse> {
    return this.http.put<TeacherResponse>(`${this.url}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  getAll(): Observable<TeacherResponse[]> {
    return this.http.get<TeacherResponse[]>(this.url);
  }

  getById(id: number): Observable<TeacherResponse> {
    return this.http.get<TeacherResponse>(`${this.url}/${id}`);
  }
}
