import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UtilityService } from '../utility/utility.service';

@Component({
  selector: 'app-user-logout',
  templateUrl: './user-logout.component.html',
  styleUrl: './user-logout.component.css'
})
  
export class UserLogoutComponent implements OnInit {

  private router = inject(Router);
  private utilitySvc = inject(UtilityService);

  ngOnInit() {

    setTimeout(() => {
      this.router.navigate(['/login']);
      this.utilitySvc.generateSuccessMessage('Logged out successfully.');
    }, 3500);
  }
}
