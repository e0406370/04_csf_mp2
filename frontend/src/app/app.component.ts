import { Component, OnInit, inject } from '@angular/core';
import { SessionStore } from './utility/session.store';
import { Observable } from 'rxjs';
import { SessionState } from './models/sessionstate';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
  
export class AppComponent implements OnInit {

  private sessionStore = inject(SessionStore);

  currentSession$!: Observable<SessionState>;

  ngOnInit(): void {
    
    this.currentSession$ = this.sessionStore.getSessionState;
    this.currentSession$.subscribe((sessionState) => {
      if (sessionState.userID) {
        console.info(`UserID: ${sessionState.userID}, Name: ${sessionState.name}`);
      }
      else {
        console.info('Not logged in')
      }
    });
  }
}