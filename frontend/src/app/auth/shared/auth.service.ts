import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { SignUpRequestPayload } from '../sign-up/signup-request.payload';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  signup(signupRequestPayload: SignUpRequestPayload): Observable<any>{

    return this.httpClient.post("http://localhost:8080/api/auth/signup", signupRequestPayload, {responseType:'text'})
  }
}
