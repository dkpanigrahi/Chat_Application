import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Import FormsModule

import { Router } from '@angular/router';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true, 
  imports: [FormsModule] 
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };

  constructor(private loginService: LoginService, private router: Router) {}

  onSubmit() {
    this.loginService.dologin(this.credentials).subscribe(
      (response: any) => {
        console.log('Login successful', response);
        this.loginService.loginUser(response.jwtToken);
        this.router.navigate(['/chat']);
      },
      (error) => {
        console.error('Login failed', error);
        alert('Invalid username or password');
      }
    );
  }
}
