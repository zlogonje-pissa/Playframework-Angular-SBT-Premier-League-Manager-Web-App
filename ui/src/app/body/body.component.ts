import {Component, OnInit} from '@angular/core';


import footballClubs from '../_files/footballClubs.json';


import { ApiService } from "../api.service";
import {Subject, Subscription} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.css']
})

export class BodyComponent implements OnInit {

  constructor(private apiService: ApiService) {
  }

  private http: any;

  public newClubList: Object | undefined;
  public matchList: Object | undefined;

  ngOnInit() {

   this.reloadClubs();
    this.reloadMatches();
  }

  public delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  public reloadClubs() {
    this.apiService.getFootballClubs().subscribe((data: any) => {
      console.log(data);
      this.newClubList = data;
      console.log(this.newClubList);
    });
  }

  public reloadMatches() {
    this.apiService.getFootballMatches().subscribe((data: any) => {
      console.log(data);
      this.matchList = data;
      console.log(this.matchList);
    });
  }


  public generateMatch() {
    this.apiService.postRandomMatch().subscribe((data) => {
      console.log(data);
      //  this.articles = data['articles'];
    });
       this.reloadMatches();



  }

  public saveMatches() {

    this.apiService.postData().subscribe((data) => {
      console.log(data);
      //  this.articles = data['articles'];
    });
    console.log("Saved Called");

  }

  public format(date: string) {

    return new Date(date);

  }


  public sortTable(tableId: string, column: number) {
    let table = document.getElementById(tableId);
    var rows;
    var switching
    var i, x, y
    var shouldSwitch;
    switching = true;
    while (switching) {
      switching = false;
      // @ts-ignore
      rows = table.rows;
      for (i = 1; i < (rows.length - 1); i++) {
        shouldSwitch = false;
        x = rows[i].getElementsByTagName("td")[column];
        y = rows[i + 1].getElementsByTagName("td")[column];
        if (parseInt(x.innerHTML) < parseInt(y.innerHTML)) {
          shouldSwitch = true;
          break;
        }
      }
      if (shouldSwitch) {
        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
        switching = true;
      }
    }
  }

  public sortTableByDate(tableId: string, column: number) {


  }


}
