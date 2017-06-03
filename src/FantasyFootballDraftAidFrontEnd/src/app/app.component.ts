import {Component} from "@angular/core";
import {Player} from "./domain/player";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  players: Player[] = [];
  constructor() {
    this.players = [new Player(
      "Drew Brees",
      "QB",
      1,
      1,
      1,
      1,
      1,
      true,
      true
    ),
      new Player(
        "Tom Brady",
        "QB",
        2,
        2,
        2,
        54,
        -2,
        true,
        true
      )];
  }

  changeData(dataset: string) {
    this.players = [new Player(
      "Drew Brees",
      "QB",
      1,
      1,
      1,
      1,
      1,
      true,
      true
    ),
    new Player(
      "Tom Brady",
      "QB",
      2,
      2,
      2,
      54,
      -2,
      true,
      true
    )];
  }
}
