import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_URL } from '../constants';
import { AuditLog, AuditLogEventType } from '../models/models';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  private apiUrl = `${API_URL}/audit`;

  constructor(private http: HttpClient) {}

  getLogs(): Observable<AuditLog[]> {
    return this.http.get<AuditLog[]>(`${this.apiUrl}/logs`);
  }

  getLogsByType(eventType: AuditLogEventType): Observable<AuditLog[]> {
    return this.http.get<AuditLog[]>(`${this.apiUrl}/logs/filter?eventType=${eventType}`);
  }
}
