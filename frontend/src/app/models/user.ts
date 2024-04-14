export interface User {
  
  userID: string;
  name: string;
  email: string;
  birthDate: number;
  username: string;
  password: string;
};

export interface LoginDetails {

  username: string;
  password: string;
};

export interface Profile {

  userID: string,
  name: string;
  email: string;
  birthDate: number;
  createdDate: number;
};
