import { Component } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    HttpClientModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user = {
    fullName: '',
    email: '',
    phoneNo: '',
    password: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    if (this.user.fullName && this.user.email && this.user.phoneNo && this.user.password) {
      this.http.post('http://localhost:8080/auth/register', this.user, { responseType: 'text' })
        .subscribe(
          (response) => {
            console.log(response);
            this.router.navigate(['/login']);
          },
          (error) => {
            console.error('Registration failed', error);
          }
        );
    } else {
      console.log('Please fill in all the fields');
    }
  }
}