export interface RegisterModel {
  firstName:string;
  lastName:string;
  email:string;
  password:string;
  role:'CLIENT'|'ADMIN'|'OWNER';
}
