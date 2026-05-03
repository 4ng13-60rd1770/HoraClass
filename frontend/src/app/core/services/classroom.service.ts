import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ClassroomRequest, ClassroomResponse } from '../models/classroom.models';

@Injectable({ providedIn: 'root' })
export class ClassroomService {
  private url = `${environment.apiUrl}/admin/salones`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<ClassroomResponse[]> {
    return this.http.get<ClassroomResponse[]>(this.url);
  }

  getById(id: number): Observable<ClassroomResponse> {
    return this.http.get<ClassroomResponse>(`${this.url}/${id}`);
  }

  create(request: ClassroomRequest): Observable<ClassroomResponse> {
    return this.http.post<ClassroomResponse>(`${this.url}/create`, request);
  }

  update(id: number, request: ClassroomRequest): Observable<ClassroomResponse> {
    return this.http.put<ClassroomResponse>(`${this.url}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
