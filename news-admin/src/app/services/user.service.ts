import { HttpService } from "./http.service";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment as env } from "../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class UserService extends HttpService {
  private _userUrl = `${env.API_URL}/user`;

  public getUsers(): Observable<any> {
    return this.get(this._userUrl);
  }

  public getUserById(userId: string): Observable<any> {
    return this.get(`${this._userUrl}/${userId}`);
  }

  public updateUser(data: any): Observable<any> {
    return this.patch(`${this._userUrl}`, data);
  }

  public createUser(data: any): Observable<any> {
    return this.post(this._userUrl, data);
  }

  public deleteUser(id: string): Observable<any> {
    return this.delete(`${this._userUrl}/${id}`);
  }
}
