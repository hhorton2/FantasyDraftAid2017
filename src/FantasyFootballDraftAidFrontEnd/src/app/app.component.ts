import {Component} from "@angular/core";
import {PassingService} from "./services/passing.service";
import {ReceivingService} from "./services/receiving.service";
import {KickingService} from "./services/kicking.service";
import {DefenseService} from "./services/defense.service";
import {RushingService} from "./services/rushing.service";
import {DraftService} from "./services/draft.service";
import {isNullOrUndefined} from "util";

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
  drafted: any[] = [];
  draftedSelected: boolean = false;

  constructor(private ps: PassingService, private rec: ReceivingService, private ks: KickingService, private ds: DefenseService, private rsh: RushingService, private draftService: DraftService) {
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
      this.def = res;
      this.all = this.all.concat(this.def);
    });
    this.draftService.getDraftedPlayers().subscribe(res => {
      this.drafted = res;
    });
    this.currentPlayers = this.all;
  }

  public changeData(dataSet: string) {
    this.currentPlayers = [];
    this.draftedSelected = false;
    switch (dataSet) {
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
      case 'drafted':
        this.currentPlayers = this.drafted;
        this.draftedSelected = true;
        break;
      default:
        this.currentPlayers = this.all;
        break;
    }
  }

  public draftPlayer(id: string) {
    let player = this.all.find(player => player.player_id == id);
    if (!isNullOrUndefined(player)) {
      this.drafted.push(player);
      let player_match = p => p.player_id != player.player_id;
      this.currentPlayers = this.currentPlayers.filter(player_match);
      this.all = this.all.filter(player_match);
      if (player.hasOwnProperty("position")) {
        switch ((player.position as string).toLowerCase()) {
          case 'qb':
            this.qb = this.qb.filter(player_match);
            break;
          case 'rb':
            this.rb = this.rb.filter(player_match);
            this.flex = this.flex.filter(player_match);
            break;
          case 'wr':
            this.wr = this.wr.filter(player_match);
            this.flex = this.flex.filter(player_match);
            break;
          case 'te':
            this.te = this.te.filter(player_match);
            this.flex = this.flex.filter(player_match);
            break;
          case 'k':
            this.k = this.k.filter(player_match);
            break;
        }
      } else {
        this.def = this.def.filter(player_match);
      }
    }
  }

  public undraftPlayer(id: string) {
    let player = this.drafted.find(player => player.player_id == id);
    if (!isNullOrUndefined(player)) {
      this.drafted = this.drafted.filter(p => p.player_id != player.player_id);
      this.currentPlayers = this.currentPlayers.filter(p => p.player_id != player.player_id);
      this.all.push(player);
      if (player.hasOwnProperty("position")) {
        switch ((player.position as string).toLowerCase()) {
          case 'qb':
            this.qb.push(player);
            break;
          case 'rb':
            this.rb.push(player);
            this.flex.push(player);
            break;
          case 'wr':
            this.wr.push(player);
            this.flex.push(player);
            break;
          case 'te':
            this.te.push(player);
            this.flex.push(player);
            break;
          case 'k':
            this.k.push(player);
            break;
        }
      } else {
        this.def.push(player);
      }
    }
  }


}
