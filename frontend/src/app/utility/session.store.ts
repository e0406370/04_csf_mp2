import { Injectable } from '@angular/core';
import { ComponentStore } from '@ngrx/component-store';
import { SessionState } from '../models/sessionstate';
import { INIT_SESSION_STORE } from './constants';

@Injectable()
export class SessionStore extends ComponentStore<SessionState> {
  constructor() {
    super(INIT_SESSION_STORE);
  }

  // Selectors
  readonly getSessionState = this.select<SessionState>(
    (slice: SessionState) => slice
  );

  // Mutators
  updateSessionState(session: SessionState): void {
    this.patchState(session);
  }

  resetSessionState(): void {
    this.setState(INIT_SESSION_STORE);
  }
}
