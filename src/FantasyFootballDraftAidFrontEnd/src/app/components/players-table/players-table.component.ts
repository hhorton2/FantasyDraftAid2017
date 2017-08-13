import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {DraftService} from "../../services/draft.service";

@Component({
  selector: 'players-table',
  templateUrl: './players-table.html',
  styleUrls: ['./players-table.css']
})
export class PlayerTable implements OnInit {
  @Input() players: any[];
  @Input() draftedSelected: boolean = false;
  @Output() playerDrafted: EventEmitter<string> = new EventEmitter<string>();
  @Output() playerUndrafted: EventEmitter<string> = new EventEmitter<string>();
  selectedPlayer: any = null;

  constructor(private draftService: DraftService) {
  }

  ngOnInit() {
  }

  draftPlayer(id: string) {
    this.selectedPlayer = null;
    this.draftService.draftPlayer(id).subscribe(b => {
      if(!b){
        alert("Draft failed to save")
      }
    });
    this.playerDrafted.emit(id);
  }

  undraftPlayer(id: string) {
    this.selectedPlayer = null;
    this.draftService.undraftPlayer(id).subscribe(b => {
      if(!b){
        alert("Undraft failed to save");
      }
    });
    this.playerUndrafted.emit(id);
  }

}
