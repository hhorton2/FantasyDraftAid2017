import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";

@Injectable()
export class RushingService {

  constructor(private http: Http) {
  }

  public getRunningBacks(season: number): Observable<any[]> {
    return this.http.get("/rushing/" + season + "/stats").map(res => res.json())
  }
}
