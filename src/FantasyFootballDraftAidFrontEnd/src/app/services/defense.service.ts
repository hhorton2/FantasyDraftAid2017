import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";

@Injectable()
export class DefenseService {


  constructor(private http: Http) {
  }

  public getDefenses(season: number): Observable<any[]> {
    return this.http.get("/defense/" + season + "/stats").map(res => res.json())
  }
}
