import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../../../services/login.service';
import { UserService } from '../../../services/user.service';
import { User } from '../../Model/user.model';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule] 
})
export class NavbarComponent {
  public loggedIn = false;
  currentUser: User | null = null;

  constructor(private loginService: LoginService,private router:Router,private userService: UserService) {}


  ngOnInit(): void {
    this.loggedIn = this.loginService.isLoggedIn();
    
    this.userService.getCurrentUser().subscribe(
      data => this.currentUser = data,
      error => console.error('Error loading users', error)
    );
  }
  
  logout() {
    this.loginService.logout();
    this.loggedIn = false; 
    this.currentUser = null; 
    this.router.navigate(['/login']);
  }
  
}
