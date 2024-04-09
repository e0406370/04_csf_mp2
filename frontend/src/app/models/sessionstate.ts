export interface SessionState {
  userID: string;
  name: string;
  isLoggedIn: boolean;
};

export const INIT_SESSION_STORE: SessionState = {
  userID: '',
  name: '',
  isLoggedIn: false,
};