import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../app/Model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/user'; 

  constructor(private http: HttpClient) { }

  // Fetch all users except the logged-in user
  getAllUsersExcept(): Observable<User[]> {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<User[]>(`${this.baseUrl}/all`, { headers });
  }

  //Get Current User
  getCurrentUser(): Observable<User>{
    const token = localStorage.getItem('token');
    
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<User>(`${this.baseUrl}/currentUser`, { headers });
  }

}
