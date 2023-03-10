import { Injectable ,Output,EventEmitter } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { SignUpRequestPayload } from '../sign-up/signup-request.payload';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';
import { Observable,throwError } from 'rxjs';
import { LoginResponse } from '../login/login-response.payload';
import { LoginRequestPayload } from '../login/login-request.payload';
import { LocalStorageService } from 'ngx-webstorage';
import {map, catchError,tap} from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class AuthService {
   refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }
  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();
  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService
    ) { }

  signup(signupRequestPayload: SignUpRequestPayload): Observable<any>{

    return this.httpClient.post("http://localhost:8080/api/auth/signup", signupRequestPayload, {responseType:'text'})
  }
  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login', loginRequestPayload)
      .pipe(map(data => {
        this.localStorage.store('authenticationToken', data.authenticationToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);

        this.loggedIn.emit(true);
        this.username.emit(data.username);
        return true;
      }));
  }
  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    }
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/refresh/token',
      refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.store('authenticationToken', response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }
  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getExpirationTime() {
    return this.localStorage.retrieve('expiresAt');
  }
  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }
  logout() {
    this.httpClient.post('http://localhost:8080/api/auth/logout', this.refreshTokenPayload,
      { responseType: 'text' })
      .subscribe(data => {
        console.log(data);
      }, error => {
        throwError(error);
      })
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');
  }
}
