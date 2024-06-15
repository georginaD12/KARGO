import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LogInComponent } from './components/log-in/log-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { OrdersTableComponent } from './components/orders-table/orders-table.component';
import { CreateOrderComponent } from './components/create-order/create-order.component';


const routes: Routes = [
  { path: 'login', component: LogInComponent },
  { path: 'table', component: OrdersTableComponent },
  { path: 'signup', component: SignUpComponent },
  { path: '', component: LandingPageComponent },
  {path:'addOrder', component:CreateOrderComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }