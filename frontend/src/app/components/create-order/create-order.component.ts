
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { OrdersService } from '../../service/orders/orders.service';
import {Router } from '@angular/router';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrl: './create-order.component.css'
})
export class CreateOrderComponent {

  form = new FormGroup({
    description: new FormControl<string>('', [Validators.required]),
    name: new FormControl<string>('', [Validators.required]),
    status: new FormControl<string>('', [Validators.required]),
  });

  constructor(private service: OrdersService, private router:Router) { }

  submit() {
    if (this.form.value.name != null && this.form.value.description != null && this.form.value.status != null) {
      console.log("hey")
      this.service.createOrder(this.form.value.name,this.form.value.status,this.form.value.description).then(()=>{
        this.router.navigate(['/table'])
      });
      this.form.reset();


    }
  }



}

