import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";

@Injectable()
export class DraftService {

  constructor(private http: Http) {
  }

  public getDraftedPlayers(): Observable<any[]> {
    return this.http.get("/draft").map(res => res.json());
  }

  public draftPlayer(playerId: string): Observable<boolean> {
    return this.http.post("/draft", playerId).map(res => res.status == 200 && res.text() == '1');
  }

  public undraftPlayer(playerId: string): Observable<boolean> {
    // noinspection SpellCheckingInspection
    return this.http.post("/undraft", playerId).map(res => res.status == 200 && res.text() == '1');
  }

}
