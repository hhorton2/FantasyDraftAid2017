import {Component, Input, OnInit} from '@angular/core';
import {Player} from "../../domain/player";

@Component({
  selector: 'players-table',
  templateUrl: './players-table.html',
  styleUrls: ['./players-table.css']
})
export class PlayerTable implements OnInit {
  @Input() players: Player[];
  selectedPlayer: Player = null;

  constructor() {
  }

  ngOnInit() {

  }

  alert(message: string){
    window.alert(message);
  }

}
