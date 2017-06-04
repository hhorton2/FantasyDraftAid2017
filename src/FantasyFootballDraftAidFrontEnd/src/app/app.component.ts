import {Component} from "@angular/core";
import {Player} from "./domain/player";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentPlayers: Player[] = [];
  all: Player[] = [];
  wr: Player[] = [];
  qb: Player[] = [];
  rb: Player[] = [];
  flex: Player[] = [];
  te: Player[] = [];
  k: Player[] = [];
  def: Player[] = [];

  constructor() {
    this.all = [new Player(
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
    this.currentPlayers = this.all;
  }

  changeData(dataset: string) {
    this.currentPlayers = [];
    switch (dataset){
      case 'all':
        if(this.all.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.all;
        }
        break;
      case 'qb':
        if(this.qb.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.qb;
        }
        break;
      case 'rb':
        if(this.rb.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.rb;
        }
        break;
      case 'wr':
        if(this.wr.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.wr;
        }
        break;
      case 'te':
        if(this.te.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.te;
        }
        break;
      case 'flex':
        if(this.flex.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.flex;
        }
        break;
      case 'k':
        if(this.k.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.k;
        }
        break;
      case 'def':
        if(this.def.length == 0){
          //get from server
        }else{
          this.currentPlayers = this.def;
        }
        break;
      default:
        this.currentPlayers = this.all;
        break;
    }

  }
}
