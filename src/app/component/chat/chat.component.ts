import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../../services/message.service';
import { UserService } from '../../../services/user.service';
import { User } from '../../Model/user.model';
import { Message } from '../../Model/message.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
  standalone: true, 
  imports: [FormsModule, CommonModule]
})
export class ChatComponent implements OnInit {
  users: User[] = [];
  selectedUser: User | null = null;
  messages: Message[] = [];
  newMessage: string = '';
  loggedInUser: User | null = null;  // Define the loggedInUser variable

  constructor(private messageService: MessageService, private userService: UserService) {}

  ngOnInit(): void {
    this.loadLoggedInUser();  // Load the logged-in user information
    this.loadUsers();
  }

  loadLoggedInUser(): void {
    this.userService.getCurrentUser().subscribe(
      data => this.loggedInUser = data,
      error => console.error('Error loading users', error)
    );
  }

  loadUsers(): void {
    this.userService.getAllUsersExcept().subscribe(
      data => this.users = data,
      error => console.error('Error loading users', error)
    );
  }

  selectUser(user: User): void {
    this.selectedUser = user;
    this.loadMessages(user.id);
  }

  loadMessages(userId: number): void {
    this.messageService.loadMessagesByUser(userId).subscribe(
      data => this.messages = data,
      error => console.error('Error loading messages', error)
    );
  }

  sendMessage(): void {
    if (this.selectedUser) {
      this.messageService.sendMessage(this.selectedUser.id, this.newMessage).subscribe(
        message => {
          this.messages.push(message);
          this.newMessage = ''; // Clear the input field after sending
        },
        error => console.error('Error sending message', error)
      );
    }
  }
}
