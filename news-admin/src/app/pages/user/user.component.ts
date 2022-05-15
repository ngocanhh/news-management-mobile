import { Router } from "@angular/router";
import { UserService } from "./../../services/user.service";
import { Component, OnInit } from "@angular/core";

interface User {
  avatar: string;
  cmnd: string;
  fullName: string;
  id: number;
  password: string;
  roleId: number;
  username: string;
}
@Component({
  selector: "app-user",
  templateUrl: "user.component.html",
})
export class UserComponent implements OnInit {
  public users: User[] = [];

  constructor(private userServ: UserService, private router: Router) {}

  ngOnInit() {
    this._getUsers();
  }

  private _getUsers(): void {
    this.userServ.getUsers().subscribe((res: any) => {
      this.users = res;
      console.log(res);
    });
  }

  public goToUserDetail(userId: string): void {
    this.router.navigate([`user/${userId}`]);
  }

  public onDelete(userId: string): void {
    confirm("Are you sure you want to delete this user?");
    this.userServ.deleteUser(userId).subscribe((res: any) => {
      this._getUsers();
      window.alert("delete user successfully!");
    });
  }
}
