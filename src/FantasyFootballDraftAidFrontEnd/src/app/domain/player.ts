export class Player {

  name: string;
  position: string;
  previousYearPoints: number;
  twoYearAgoPoints: number;
  threeYearAgoPoints: number;
  threeYearChange: number;
  fiveYearChange: number;
  rookie: boolean;
  newTeam: boolean;


  constructor(name: string, position: string, previousYearPoints: number, twoYearAgoPoints: number, threeYearAgoPoints: number, threeYearChange: number, fiveYearChange: number, rookie: boolean, newteam: boolean) {
    this.name = name;
    this.position = position;
    this.previousYearPoints = previousYearPoints;
    this.twoYearAgoPoints = twoYearAgoPoints;
    this.threeYearAgoPoints = threeYearAgoPoints;
    this.threeYearChange = threeYearChange;
    this.fiveYearChange = fiveYearChange;
    this.rookie = rookie;
    this.newTeam = newteam;
  }
}
