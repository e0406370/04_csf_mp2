import { Injectable } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class ZavigationService {
  
  getMenuItems(): MenuItem[] {
    const zavItems: MenuItem[] = [];

    zavItems.push(
      {
        routerLink: '/',
      },
      {
        label: 'Find Events',
        icon: 'pi pi-search',
        routerLink: '/',
      },
      {
        label: 'Create Events',
        icon: 'pi pi-calendar-plus',
        routerLink: '/',
      },
      {
        label: 'Log In',
        icon: 'pi pi-sign-in',
        routerLink: '/login',
      },
      {
        label: 'Register',
        icon: 'pi pi-user-plus',
        routerLink: '/register',
      }
    );

    return zavItems;
  }
}
