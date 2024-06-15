import { User } from "./user.model";

export class Order{
    
 
    constructor(
        public id:string,
        public name:string,
        public status:string,
        public description:string,
        public user: User|null,
        public isEdit:false,
       
    ){}
    
     
}