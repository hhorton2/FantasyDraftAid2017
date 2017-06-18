import {Component} from "@angular/core";
import {PassingService} from "./services/passing.service";
import {ReceivingService} from "./services/receiving.service";
import {KickingService} from "./services/kicking.service";
import {DefenseService} from "./services/defense.service";
import {RushingService} from "./services/rushing.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentPlayers: any[] = [];
  all: any[] = [];
  wr: any[] = [];
  qb: any[] = [];
  rb: any[] = [];
  flex: any[] = [];
  te: any[] = [];
  k: any[] = [];
  def: any[] = [];

  constructor(private ps: PassingService, private rec: ReceivingService, private ks: KickingService, private ds: DefenseService, private rsh: RushingService) {
    this.ps.getQuarterbacks(2016).subscribe(res => {
      this.qb = res;
      this.all = this.all.concat(this.qb);
    });
    this.rec.getWideReceivers(2016).subscribe(res => {
      this.wr = res;
      this.all = this.all.concat(this.wr);
      this.flex = this.flex.concat(this.wr);
    });
    this.rec.getTightEnds(2016).subscribe(res => {
      this.te = res;
      this.all = this.all.concat(this.te);
      this.flex = this.flex.concat(this.te);
    });
    this.rsh.getRunningBacks(2016).subscribe(res => {
      this.rb = res;
      this.all = this.all.concat(this.rb);
      this.flex = this.flex.concat(this.rb);
    });
    this.ks.getKickers(2016).subscribe(res => {
      this.k = res;
      this.all = this.all.concat(this.k);
    });
    this.ds.getDefenses(2016).subscribe(res => {
      console.log(res);
      this.def = res;
      this.all = this.all.concat(this.def);
    });
    this.currentPlayers = this.all;
  }

  changeData(dataset: string) {
    this.currentPlayers = [];
    switch (dataset) {
      case 'all':
        this.currentPlayers = this.all;
        break;
      case 'qb':
        this.currentPlayers = this.qb;
        break;
      case 'rb':
        this.currentPlayers = this.rb;
        break;
      case 'wr':
        this.currentPlayers = this.wr;
        break;
      case 'te':
        this.currentPlayers = this.te;
        break;
      case 'flex':
        this.currentPlayers = this.flex;
        break;
      case 'k':
        this.currentPlayers = this.k;
        break;
      case 'def':
        this.currentPlayers = this.def;
        break;
      default:
        this.currentPlayers = this.all;
        break;
    }

  }
}
