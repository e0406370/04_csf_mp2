import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UtilityService } from '../utility/utility.service';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-user-logout',
  templateUrl: './user-logout.component.html',
  styleUrl: './user-logout.component.css',
  animations: [
    trigger('progressBarAnimation', [
      transition(':enter', [
        style({ width: '0%' }),
        animate('3500ms', style({ width: '100%' })),
      ]),
    ]),
  ],
})
  
export class UserLogoutComponent implements OnInit {

  private router = inject(Router);
  private utilitySvc = inject(UtilityService);

  ngOnInit() {

    setTimeout(() => {
      this.router.navigate(['/login']);
      this.utilitySvc.generateSuccessMessage('You have logged out successfully. Hope to see you again!');
    }, 3500);
  }
}
