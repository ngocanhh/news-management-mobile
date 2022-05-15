import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, throwError, TimeoutError } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class HttpService {
  constructor(protected readonly http: HttpClient) {}

  private _getHeader(): HttpHeaders {
    return new HttpHeaders();
  }

  protected get<T = any>(
    url: string,
    headers?: any,
    params?: any
  ): Observable<T> {
    return this.http
      .get<any>(url, {
        headers: headers ? headers : this._getHeader(),
        params: params ? params : null,
      })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }
  protected put<T = any>(
    url: string,
    body: any,
    headers?: any,
    params?: any
  ): Observable<T> {
    return this.http
      .put<any>(url, body, {
        headers: headers ? headers : this._getHeader(),
        params: params ? params : null,
      })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  protected patch<T = any>(
    url: string,
    body: any,
    headers?: any,
    params?: any
  ): Observable<T> {
    return this.http
      .patch<any>(url, body, {
        headers: headers ? headers : this._getHeader(),
        params: params ? params : null,
      })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  protected post<T = any>(
    url: string,
    body: any,
    headers?: any
  ): Observable<T> {
    return this.http
      .post<any>(url, body, {
        headers: headers ? headers : this._getHeader(),
      })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  protected delete(url: string, headers?: any): any {
    return this.http
      .delete<any>(url, {
        headers: headers ? headers : this._getHeader(),
      })
      .pipe(
        map((response) => response),
        catchError(this.handleError)
      );
  }

  protected handleError(error: unknown): Observable<typeof error> {
    if (
      error instanceof TimeoutError ||
      (error instanceof HttpErrorResponse && error.status === 0)
    ) {
      window.alert(error);
    }
    return throwError(() => error);
  }
}
