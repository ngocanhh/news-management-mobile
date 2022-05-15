import { Router } from "@angular/router";
import { UserService } from "./../../../services/user.service";
import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: "app-user-new",
  templateUrl: "./user-new.component.html",
  styleUrls: ["./user-new.component.scss"],
})
export class UserNewComponent implements OnInit {
  public userForm = new FormGroup({
    username: new FormControl(null),
    password: new FormControl(null),
    cmnd: new FormControl(null),
    avatar: new FormControl(null),
    fullName: new FormControl(null),
  });

  constructor(private userServ: UserService) {}

  ngOnInit(): void {}

  public onSubmit(): void {
    if (!this.userForm.value) {
      return;
    }
    const req = {
      ...this.userForm.value,
    };
    this.userServ.createUser(req).subscribe((res: any) => {
      window.alert("Create user successfully!");
      this.userForm.reset();
    });
  }
}
