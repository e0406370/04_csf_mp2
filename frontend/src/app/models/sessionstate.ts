export interface SessionState {

  userID: string;
  name: string;
};

export const INIT_SESSION_STORE: SessionState = {
  
  userID: '',
  name: '',
};