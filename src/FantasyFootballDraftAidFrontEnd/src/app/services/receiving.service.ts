import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";

@Injectable()
export class ReceivingService {

  constructor(private http: Http) {
  }

  public getWideReceivers(season: number): Observable<any[]> {
    return this.http.get("/receiving/wr/" + season + "/stats").map(res => res.json())
  }

  public getTightEnds(season: number): Observable<any[]> {
    return this.http.get("/receiving/te/" + season + "/stats").map(res => res.json())
  }
}
