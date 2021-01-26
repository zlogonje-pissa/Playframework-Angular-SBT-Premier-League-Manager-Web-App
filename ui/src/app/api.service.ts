import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  //
  // public getPews(){
  //   return this.httpClient.get(`http://localhost:9000/api/footballclubs`);
  // }

  public getFootballClubs(){
    return this.httpClient.get(`/api/footballclubs`);
  }

  public postData(){
    return this.httpClient.post(`/api/savedata`, {});
  }

  public postRandomMatch(){
    return this.httpClient.post(`/api/randmatch`, {});
  }

  public getFootballMatches(){
    return this.httpClient.get(`/api/listMatches`);
  }

}
