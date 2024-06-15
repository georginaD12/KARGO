import { Injectable } from '@angular/core';
import axios from 'axios';
import { AuthenticationService } from '../authentication/authentication.service';
import { Order } from '../../models/order.model';
import { User } from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  api = axios.create({ baseURL: "http://localhost:8080/" });

  constructor(private service: AuthenticationService) { }


  async getOrders() {
    let response = await this.api.request({
      method: 'get',
      url: 'api/v1/orders',
      headers: {
        Authorization: `Bearer ${this.service.getCurrentUserAcessToken()}`,
      },
    });


    console.log('GETTING OORDERS');
    // console.log(response.data);
    console.log('the end');
    const dataArray: Order[] = response.data.map((item: any) => {
      return new Order(
        item.id,
        item.name,
        item.status,
        item.description,

        item.user != null ? new User(
          item.user.role,
          item.user.username,
          null,
          null,
          item.user.password,
          null,
          null
        ) : null,
        false

      );
    });
    console.log(dataArray);

    return dataArray;
  }

  async createOrder(name:string, status: string, description:string) {
    const response = await this.api.request({
      method: 'post',
      url: 'api/v1/orders',
      data: {
        name: name,
        status: status,
        description: description,
        username: null,
      },
      headers: {
        Authorization: `Bearer ${this.service.getCurrentUserAcessToken()}`,
      },
    });
    return response.data;
  }



  async changeOrder(
    id: string,
    name: string,
    status: string,
    username: String | null,
    description: string,

  ) {
    try {
      const url = `api/v1/orders/${id}`;
      const response = await this.api.request({
        method: 'put',
        url: url,
        data: {
          name: name,
          status: status,
          description: description,
          username: username,

        },
        headers: {
          Authorization: `Bearer ${this.service.getCurrentUserAcessToken()}`,
        },
      });

      console.log('Order updated successfully:', response.data);
    } catch (error) {
      console.error('Error updating document:', error);
    }
  }



  async deleteOrder(id:string){
    try {
      const response = await this.api.request({
        method: 'delete',
        url: `api/v1/orders/${id}`,
        headers: {
          Authorization: `Bearer ${this.service.getCurrentUserAcessToken()}`,
        },
      });

      if (response.status === 200) {
        console.log(`Order with ID ${id} deleted successfully.`);
      } else {
        console.error('Failed to delete order.');
      }
    } catch (error) {
      console.error('Error deleting order:', error);
    }
  }
}
