import { Component, OnInit } from '@angular/core';
import { SearchService } from "../services/search.Service";

@Component({
  selector: 'app-result-container',
  templateUrl: './result-container.component.html',
  styleUrls: ['./result-container.component.css'],
})
export class ResultContainerComponent implements OnInit {
  show = true;
  isShowResult = true;
  isShowFavorite = false;

  resultShowClass = "btn btn-primary";
  favoriteShowClass = "btn btn-default";

  constructor(private sService: SearchService) { 
   /* this.sService.isClear.subscribe(data =>{
      this.showResult();
      //this.isShowResult = false;
      console.log("clear  "+data);
      this.show = !Boolean(data);
      console.log(this.show);
      //console.log("show  "+this.show);
      //console.log(this.isShowResult+" "+this.isShowFavorite);
    })*/
  }


  showResult() {
    this.show = true;
    this.isShowResult = true;
    this.isShowFavorite = false;
    this.resultShowClass = "btn btn-primary";
    this.favoriteShowClass = "btn btn-default";
    //console.log(this.show+" "+this.isShowResult);
  }

  showFavorite() {
    this.show = true;
    this.isShowResult = false;
    this.isShowFavorite = true;
    this.resultShowClass = "btn btn-default";
    this.favoriteShowClass = "btn btn-primary";
  }

  ngOnInit() { 
    this.sService.isClear.subscribe(data =>{
      this.showResult();
      //this.isShowResult = false;
      //console.log("clear  "+data);
      this.show = !Boolean(data);
      //console.log(this.show);
      //console.log("show  "+this.show);
      //console.log(this.isShowResult+" "+this.isShowFavorite);
    })
  }

}
