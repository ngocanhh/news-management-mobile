import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: "app-user-detail",
  templateUrl: "./user-detail.component.html",
  styleUrls: ["./user-detail.component.scss"],
})
export class UserDetailComponent implements OnInit {
  public userForm = new FormGroup({
    username: new FormControl(null),
    password: new FormControl(null),
    cmnd: new FormControl(null),
    avatar: new FormControl(null),
    fullName: new FormControl(null),
  });

  private _userId: string;

  constructor(
    private userServ: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this._userId = this.route.snapshot.paramMap.get("id");
    if (!this._userId) {
      return;
    }
    this.userServ.getUserById(this._userId).subscribe((res: any) => {
      if (!res) {
        return;
      }
      this.userForm.patchValue({
        ...res,
      });
    });
  }

  public onSubmit(): void {
    if (!this.userForm.value) {
      return;
    }
    const req = {
      id: this._userId,
      ...this.userForm.value,
    };
    this.userServ.updateUser(req).subscribe((res: any) => {
      if (res) {
        window.alert("update successfully!");
        this.router.navigate(["/user"]);
      }
    });
  }
}
