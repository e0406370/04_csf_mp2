import { Component, OnInit, inject } from '@angular/core';
import { SessionStore } from './utility/session.store';
import { Observable } from 'rxjs';
import { SessionState } from './models/sessionstate';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
  
export class AppComponent implements OnInit {

  private router = inject(Router); 
  private sessionStore = inject(SessionStore);

  currentSession$!: Observable<SessionState>;
  loggedID!: string | null;
  loggedName!: string | null;

  ngOnInit(): void {
    
    this.currentSession$ = this.sessionStore.getSessionState;
    this.currentSession$.subscribe((sessionState) => {
      if (sessionState.userID) {
        console.info(`UserID: ${sessionState.userID}, Name: ${sessionState.name}`);

        this.loggedID = sessionState.userID;
        this.loggedName = sessionState.name;
      }
      else {
        console.info('Not logged in')
      }
    });
  }

  logoutUser(): void {

    this.sessionStore.resetSessionState();
    this.router.navigate(['/']);
    
    window.location.reload();
  }
}