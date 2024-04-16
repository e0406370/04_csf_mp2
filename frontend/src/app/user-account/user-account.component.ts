import { Component, OnInit, inject } from '@angular/core';
import { SessionStore } from '../utility/session.store';
import { Router } from '@angular/router';
import { ERROR_NOT_LOGGED_IN_MESSAGE } from '../utility/constants';
import { UtilityService } from '../utility/utility.service';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrl: './user-account.component.css'
})
  
export class UserAccountComponent implements OnInit {

  private router = inject(Router);
  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);

  loggedName!: string;
  loggedID!: string;

  ngOnInit(): void {

    if (!this.sessionStore.isLoggedIn()) {
      this.utilitySvc.generateErrorMessage(ERROR_NOT_LOGGED_IN_MESSAGE);
      this.router.navigate(['/login']);
    }

    this.sessionStore.getSessionState.subscribe((sess) => {
      this.loggedName = sess.name;
      this.loggedID = sess.userID;
    });
  }
}
