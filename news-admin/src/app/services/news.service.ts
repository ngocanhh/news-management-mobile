import { HttpService } from "./http.service";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment as env } from "../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class NewsService extends HttpService {
  private _newsUrl = `${env.API_URL}/news`;

  public getNews(): Observable<any> {
    return this.get(this._newsUrl);
  }

  public getNewsById(newsId: string): Observable<any> {
    return this.get(`${this._newsUrl}/${newsId}`);
  }

  public updateNews(data: any): Observable<any> {
    return this.post(`${this._newsUrl}`, data);
  }

  public createNews(data: any): Observable<any> {
    return this.post(this._newsUrl, data);
  }

  public deleteNews(id: string): Observable<any> {
    return this.delete(`${this._newsUrl}/${id}`);
  }
}
