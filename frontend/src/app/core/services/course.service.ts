import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CourseRequest, CourseResponse } from '../models/course.models';

@Injectable({ providedIn: 'root' })
export class CourseService {
  private url = `${environment.apiUrl}/admin/cursos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<CourseResponse[]> {
    return this.http.get<CourseResponse[]>(this.url);
  }

  getById(id: number): Observable<CourseResponse> {
    return this.http.get<CourseResponse>(`${this.url}/${id}`);
  }

  create(course: CourseRequest): Observable<CourseResponse> {
    return this.http.post<CourseResponse>(`${this.url}/create`, course);
  }

  update(id: number, course: CourseRequest): Observable<CourseResponse> {
    return this.http.put<CourseResponse>(`${this.url}/${id}`, course);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
