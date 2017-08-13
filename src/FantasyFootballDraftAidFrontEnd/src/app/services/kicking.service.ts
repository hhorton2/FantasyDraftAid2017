import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class KickingService {

  constructor(private http: Http) {
  }

  public getKickers(season: number): Observable<any[]> {
    return this.http.get("/kicking/" + season + "/stats").map(res => res.json())
  }
}
