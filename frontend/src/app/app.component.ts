import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionState } from './models/sessionstate';
import { SessionStore } from './utility/session.store';
import { UtilityService } from './utility/utility.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
  
export class AppComponent implements OnInit {

  private router = inject(Router);
  private sessionStore = inject(SessionStore);
  private utilitySvc = inject(UtilityService);

  currentDateDayTime!: string
  currentSession$!: Observable<SessionState>;
  loggedID!: string;
  loggedName!: string;

  ngOnInit(): void {

    setInterval(() => {
      this.currentDateDayTime = this.utilitySvc.updateDateDayTime();
    }, 1000);

    this.retrieveSession();
  }

  retrieveSession(): void {

    this.currentSession$ = this.sessionStore.getSessionState;

    this.currentSession$.subscribe((sessionState) => {
      if (!sessionState.userID) {
        console.info('Not logged in');
      }
      else {
        console.info(`UserID: ${sessionState.userID}, Name: ${sessionState.name}`);

        this.loggedID = sessionState.userID;
        this.loggedName = sessionState.name;
      }
    });
  }

  logoutUser(): void {
    
    this.sessionStore.resetSessionState();
    this.loggedID = "";
    this.loggedName = "";

    this.router.navigate(['/logout']);
  }
}
