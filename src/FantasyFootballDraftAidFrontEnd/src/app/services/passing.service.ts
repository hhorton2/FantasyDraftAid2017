import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class PassingService {

  constructor(private http: Http) {
  }

  public getQuarterbacks(season: number): Observable<any[]> {
    return this.http.get("/passing/" + season + "/stats").map(res => res.json())
  }

}
