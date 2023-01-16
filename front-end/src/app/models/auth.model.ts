export interface AuthModel {
  firstName?:string;
  lastName?:string;
  email:string;
  password:string;
  role:'CLIENT'|'ADMIN'|'OWNER';
  jwtToken?:string;
}
