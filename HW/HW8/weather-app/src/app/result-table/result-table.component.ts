import { Component, OnInit } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { FavoriteService } from "../services/favorite.service";
import { SearchService } from "../services/search.Service";
import { SearchForm } from "../search-form/search-form";
import { Current } from "./current-tab/current";
import { MessageService } from "../services/message.service";

@Component({
  selector: 'app-result-table',
  templateUrl: './result-table.component.html',
  styleUrls: ['./result-table.component.css']
})
export class ResultTableComponent implements OnInit {
  public tabs = [
    { id: "current-tab", title: "Current" },
    { id: "hourly-tab", title: "Hourly" },
    { id: "weekly-tab", title: "Weekly" }
  ];

  crrntInfo: Current;
  showResult = false;
  isFavorited: boolean;
  error: boolean = false;
  weatherJson = new Subject();
  weather_json = null;
  form: any = SearchForm;
  load: number = 0;

  public activeID = "current-tab";
  tempRaw: any;

  setActive(id){
    this.activeID = id;
  }

  setCrrnt(data){
    let tmp = new Current();
    //console.log(data);
    //console.log(data["timezone"]);
    tmp.timezone = data["timezone"];
    tmp.summary = data["currently"]["summary"];
    tmp.temperature = data["currently"]["temperature"];
    this.tempRaw = data["currently"]["temperature"];
    tmp.temperature = Math.round(tmp.temperature);
    tmp.humidity = data["currently"]["humidity"];
    tmp.pressure = data["currently"]["pressure"];
    tmp.windSpeed = data["currently"]["windSpeed"];
    tmp.cloudCover = data["currently"]["cloudCover"];
    tmp.visibility = data["currently"]["visibility"];
    tmp.ozone = data["currently"]["ozone"];
    this.crrntInfo = tmp;
    //console.log(tmp.timezone);
  }

  tweet() {
    let url = "https://twitter.com/intent/tweet?text=";
    url += `The current temperature at ${this.form.cityget} is ${
      this.tempRaw}°F. The weather conditions are ${this.crrntInfo.summary}.`;
    url += "&hashtags=CSCI571WeatherSearch";
    //url += "&url=" + this.details.website;
    var newWin = window.open(url, "tweet");
  }

  setFavorite(){
    if (this.isFavorited){
      this.fService.removeFavorite(this.form.cityget);
      this.isFavorited = false;
    }
    else {
      this.fService.saveFavorite(this.form);
      this.isFavorited = true;
    }
    //console.log(this.form);
  }

  checkFavorite(){
    //console.log(this.form.cityget);
    this.isFavorited = this.fService.isFavorited(this.form.cityget);
    //console.log(this.isFavorited);
  }

  constructor(
    private sService: SearchService,
    private message: MessageService,
    private fService: FavoriteService
    ) { 
      //console.log(this.showResult, this.load);
      //this.activeID = "current-tab";
      this.sService.weather_json.subscribe(data =>{
        this.checkFavorite();
        //console.log(data);
        this.weather_json = data;
        //console.log(this.weather_json);
        this.showResult = true;
        this.error = false;
        this.setCrrnt(this.weather_json);
      })
      this.sService.showError.subscribe(data =>{
        //console.log(data);
        this.error = Boolean(data);
        this.showResult = true;
        //console.log(this.error);
      })
      this.fService.isStorageChange.subscribe(data => {
        //console.log("ischanged? "+ data);
        this.checkFavorite();
      });
      this.subscription = this.message.getMessage().subscribe(msg => {
        //console.log(msg);
        //console.log(msg.type);
        this.load = Number(msg.type);
        // 根据msg，来处理你的业务逻辑。
      });
      this.message.getMessage2().subscribe(msg => {
        this.activeID = String(msg.type);
      })
      //console.log(this.showResult, this.load);
  }

  public subscription: Subscription;

   // 组件生命周期结束的时候，记得注销一下，不然会卡；
    ngOnDestroy(): void {
     this.subscription.unsubscribe();
   }

  ngOnInit() {
    
  }

}
