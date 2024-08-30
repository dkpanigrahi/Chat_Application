import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { ChatComponent } from './component/chat/chat.component';
import { RegisterComponent } from './component/register/register.component';
import { authGuard } from '../services/auth.guard';


export const routes: Routes = [

    {
        path:'',
        'component':HomeComponent,
    },
    {
        path:'login',
        'component':LoginComponent,
    },
    {
        path:'chat',
        'component':ChatComponent,
        canActivate: [authGuard]
    },
    {
        path:'register',
        'component':RegisterComponent,
    }
    
];
