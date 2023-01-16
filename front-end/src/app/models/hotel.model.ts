export class HotelModel{
  id!:number;
  cityId!:number;
  isAvailable!:boolean;
  isApproved!:boolean;
  title!:string;
  description!:string;
  imageName!:string;
  imageBase64!:string | ArrayBuffer | null;
  updateImage!:boolean;
  ownerName!:number
}
