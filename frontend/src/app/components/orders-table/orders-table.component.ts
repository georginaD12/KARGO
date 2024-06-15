import { AuthenticationService } from './../../service/authentication/authentication.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Order } from '../../models/order.model';
import { OrdersService } from '../../service/orders/orders.service';
import { User } from '../../models/user.model';


@Component({
  selector: 'app-orders-table',
  templateUrl: './orders-table.component.html',
  styleUrl: './orders-table.component.css'
})

export class OrdersTableComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'Title',
    'status',
    'user',
    'Action',
  ];

  dataSource: MatTableDataSource<Order>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ordersList: Order[] = [];
  orders: any = [];
  currentUser!: User;

  oldTitleObj: any;

  constructor(private service: OrdersService, private authService: AuthenticationService) {
    this.dataSource = new MatTableDataSource(this.orders);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;


    this.currentUser = this.authService.currentUser;


    this.service.getOrders().then((items: Order[]) => {
      this.ordersList = items;
      console.log("Initialization")
      console.log(this.ordersList)

      this.dataSource = new MatTableDataSource(this.ordersList);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }


  //DELETE order
  async deleteOrder(id: string) {
    try {
      const confirmation = confirm('Are you sure you want to delete?');
      if (confirmation) {
        await this.service.deleteOrder(id);

        this.ordersList = this.ordersList.filter(order => order.id !== id);
        console.log("after delete");

        console.log(this.ordersList)
        this.dataSource = new MatTableDataSource(this.ordersList);
      }

    } catch (error) {
      console.error('Error deleting order:', error);
    }
  }

  async assignMe(id: string) {
    try {
      const confirmation = confirm('Are you sure you want to be assigned to this order?');
      if (confirmation) {
        var orderToBeChanged = this.ordersList.find((order => order.id == id));
        if (orderToBeChanged)
          await this.service.changeOrder(id,
            orderToBeChanged?.name,
            orderToBeChanged?.status,
            (this.authService.currentUser.username == undefined) ? null : this.authService.currentUser.username,
            orderToBeChanged?.description);

        this.ordersList = this.ordersList
          .map(order => {
            if (order.id === id)
              order.user = this.authService.currentUser
            return order;
          }
          );
        console.log("after changed user");

        console.log(this.ordersList)
        this.dataSource = new MatTableDataSource(this.ordersList);
      }

    } catch (error) {
      console.error('Error changing order:', error);
    }
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }






  onUpdate(row: any) {
    for (let order in this.ordersList) {
      console.log("ORDER" + order);
      console.log("On Update:", row.name)
      console.log(row.Title)
      console.log(this.ordersList)
      if (this.ordersList[order].id == row.id) {
        this.service.changeOrder(
          row.id,
          row.Title,
          this.ordersList[order].status,
          this.ordersList[order].user?.username ?? null,

          this.ordersList[order].description,
        );
        this.ordersList[order].name = row.Title;

      }
    }
    console.log(this.ordersList);
    row.isEdit = false;
  }

  onEdit(TitleObj: any) {
    this.oldTitleObj = JSON.stringify(TitleObj);
    this.dataSource.data.forEach((element) => {
      element.isEdit = false;
    });
    TitleObj.isEdit = true;
  }

  onCancel(titleObj: any) {
    const oldObj = JSON.parse(this.oldTitleObj);
    titleObj.name = oldObj.name;
    titleObj.isEdit = false;
  }
}



