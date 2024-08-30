import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message } from '../app/Model/message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private baseUrl = 'http://localhost:8080/message';

  constructor(private http: HttpClient) { }

  // Get the Authorization headers
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); 
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/x-www-form-urlencoded'
    });
  }

  // Load messages by user ID
  loadMessagesByUser(userId: number): Observable<Message[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<Message[]>(`${this.baseUrl}/user/${userId}`, { headers });
  }

  // Send a message
  sendMessage(receiverId: number, content: string): Observable<Message> {
    const body = new URLSearchParams();
    body.set('receiverId', receiverId.toString());
    body.set('content', content);

    const headers = this.getAuthHeaders();
    return this.http.post<Message>(`${this.baseUrl}/send`, body.toString(), { headers });
  }
}
