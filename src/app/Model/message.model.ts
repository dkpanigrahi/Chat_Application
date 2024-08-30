import { User } from './user.model';

export interface Message {
  id: number;
  content: string;
  sender: User;
  receiver: User;
}