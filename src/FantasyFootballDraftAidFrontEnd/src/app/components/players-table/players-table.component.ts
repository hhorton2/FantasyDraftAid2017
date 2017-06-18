import {Component, Input, OnInit} from "@angular/core";

@Component({
  selector: 'players-table',
  templateUrl: './players-table.html',
  styleUrls: ['./players-table.css']
})
export class PlayerTable implements OnInit {
  @Input() players: any[];
  selectedPlayer: any = null;

  constructor() {
  }

  ngOnInit() {

  }

  alert(message: string){
    window.alert(message);
  }

}
