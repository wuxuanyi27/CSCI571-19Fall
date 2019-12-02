import { Injectable } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Subject, of } from "rxjs";
import { Observable } from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" })
};
@Injectable()
export class SearchService {
  public urlhead: string = "http://weather-app.us-west-1.elasticbeanstalk.com";
  //public urlhead: string = "localhost:8081";
  private geourl: string = "http://ip-api.com/json";

  private _weatherJson = new Subject();
  weather_json = this._weatherJson.asObservable();

  private _detailJson = new Subject();
  detail_json = this._detailJson.asObservable();

  private _isClear = new Subject();
  isClear = this._isClear.asObservable();

  private _loadState = new Subject();
  loadState = this._loadState.asObservable();

  private _showError = new Subject();
  showError = this._showError.asObservable();

  states2 =
    {
      "Alabama": "AL",
      "Alaska": "AK",
      "Arizona": "AZ",
      "Arkansas": "AR",
      "California": "CA",
      "Colorado": "CO",
      "Connecticut": "CT",
      "Delaware": "DE",
      "District Of Columbia": "DC",
      "Florida": "FL",
      "Georgia": "GA",
      "Hawaii": "HI",
      "Idaho": "ID",
      "Illinois": "IL",
      "Indiana": "IN",
      "Iowa": "IA",
      "Kansas": "KS",
      "Kentucky": "KY",
      "Louisiana": "LA",
      "Maine": "ME",
      "Maryland": "MD",
      "Massachusetts": "MA",
      "Michigan": "MI",
      "Minnesota": "MN",
      "Mississippi": "MS",
      "Missouri": "MO",
      "Montana": "MT",
      "Nebraska": "NE",
      "Nevada": "NV",
      "New Hampshire": "NH",
      "New Jersey": "NJ",
      "New Mexico": "NM",
      "New York": "NY",
      "North Carolina": "NC",
      "North Dakota": "ND",
      "Ohio": "OH",
      "Oklahoma": "OK",
      "Oregon": "OR",
      "Pennsylvania": "PA",
      "Rhode Island": "RI",
      "South Carolina": "SC",
      "South Dakota": "SD",
      "Tennessee": "TN",
      "Texas": "TX",
      "Utah": "UT",
      "Vermont": "VT",
      "Virginia": "VA",
      "Washington": "WA",
      "West Virginia": "WV",
      "Wisconsin": "WI",
      "Wyoming": "WY"
    }

  states3 =
    {
      "AL": "Alabama",
      "AK": "Alaska",
      "AZ": "Arizona",
      "AR": "Arkansas",
      "CA": "California",
      "CO": "Colorado",
      "CT": "Connecticut",
      "DE": "Delaware",
      "DC": "District Of Columbia",
      "FL": "Florida",
      "GA": "Georgia",
      "HI": "Hawaii",
      "ID": "Idaho",
      "IL": "Illinois",
      "IN": "Indiana",
      "IA": "Iowa",
      "KS": "Kansas",
      "KY": "Kentucky",
      "LA": "Louisiana",
      "ME": "Maine",
      "MD": "Maryland",
      "MA": "Massachusetts",
      "MI": "Michigan",
      "MN": "Minnesota",
      "MS": "Mississippi",
      "MO": "Missouri",
      "MT": "Montana",
      "NE": "Nebraska",
      "NV": "Nevada",
      "NH": "New Hampshire",
      "NJ": "New Jersey",
      "NM": "New Mexico",
      "NY": "New York",
      "NC": "North Carolina",
      "ND": "North Dakota",
      "OH": "Ohio",
      "OK": "Oklahoma",
      "OR": "Oregon",
      "PA": "Pennsylvania",
      "RI": "Rhode Island",
      "SC": "South Carolina",
      "SD": "South Dakota",
      "TN": "Tennessee",
      "TX": "Texas",
      "UT": "Utah",
      "VT": "Vermont",
      "VA": "Virginia",
      "WA": "Washington",
      "WV": "West Virginia",
      "WI": "Wisconsin",
      "WY": "Wyoming"
    }

  /*private _autoComplete = new Subject();
  autoComplete = this._autoComplete.asObservable();*/

  constructor(private http: HttpClient) { }


  getGeolocation(form) {
    //console.log(this.http);
    this.http.get(this.geourl).subscribe(data => {
      //console.log(data);
      form.lat = data["lat"];
      form.lon = data["lon"];
      form.stateget = data["regionName"];
      form.cityget = data["city"];
      form.curLocation = true;
      form.stateA = data["region"];
      //console.log(form);
      this.search(form);
    });
    //return this.http.get(this.geourl);
  }

  clear() {
    this._isClear.next(true);
    this._loadState.next(0);
  }

  getWeatherInfo(form: any) {
    let url = this.urlhead + "/get_weather?" + "lat=" + form.lat + "&lng=" + form.lon;
    //console.log(url);
    this.http.get(url).subscribe(data => {
      this._weatherJson.next(data);
      //.log(data);
      //console.log(this._weatherJson);
      //console.log(this.weather_json);
      this.getStateSeal(form);
    });

  }
  getStateSeal(form: any) {
    let url = this.urlhead + "/get_seal" + "?state=" + form.stateA;
    console.log(url);
    this.http.get(url).subscribe(data => {
      form.stateSeal = data["items"][0]["link"];
      //console.log(form.stateSeal);
    })
    this._loadState.next(0);
  }

  getAutoComplete(input: any) {
    let url = this.urlhead + "/get_autocomplete?input=" + encodeURIComponent(input);
    return this.http.get(url);
  }

  getDetail(lat: any, lon: any, time: any) {
    let url = this.urlhead + "/get_detail?" + "lat=" + lat + "&lng=" + lon + "&time=" + time;
    return this.http.get(url);
  }

  getFavorite(info, form) {
    //this.clear();
    //console.log(info);
    this._loadState.next(1);
    this._isClear.next(false);
    let url = this.urlhead + "/get_location?city=" + encodeURIComponent(info.city) + "&state=" + encodeURIComponent(info.state);
    //console.log(url);
    this.http.get(url).subscribe(data => {
      //console.log(data);
      //console.log(form);
      form.lat = data["results"][0]["geometry"]["location"]["lat"];
      form.lon = data["results"][0]["geometry"]["location"]["lng"];
      form.stateget = this.states3[info.state];
      form.cityget = info.city;
      form.stateA = info.state;
      //console.log(form);
      this._loadState.next(2);
      this.getWeatherInfo(form);
    })
  }

  search(form) {
    //this.clear();
    this._loadState.next(1);
    //console.log(form.lat+"  "+ form.lon);
    this._isClear.next(false);
    if (form.curLocation) {
      this._loadState.next(2);
      this.getWeatherInfo(form);
      //当前经纬度darksky
    }
    else {
      //输入的地址得到lat和lon
      //if (form.state != "Select State"){
      //输入了州名
      form.stateA = this.states2[form.state];
      //console.log(form.stateA);
      let url = this.urlhead + "/get_location?street=" + encodeURIComponent(form.street) + "&city=" + encodeURIComponent(form.city) + "&state=" + encodeURIComponent(form.stateA);
      //console.log(url);
      this.http.get(url).subscribe(data => {
        //console.log(data["results"]);
        if (data === null || data["results"] == "") {
          this._loadState.next(0);
          this._showError.next(true);
        }
        form.lat = data["results"][0]["geometry"]["location"]["lat"];
        form.lon = data["results"][0]["geometry"]["location"]["lng"];
        form.stateget = form.state;
        form.cityget = form.city;
        form.stateA = this.states2[form.stateget];
        //console.log(form);
        this._loadState.next(2);
        this.getWeatherInfo(form);
      });
      //}
      /*else{//未输入州名
        let url = this.urlhead + "/get_location?street="+ encodeURIComponent(form.street)+"&city="+encodeURIComponent(form.city);
        console.log(url);
        this.http.get(url).subscribe(data=>{
          form.lat = data["results"][0]["geometry"]["location"]["lat"];
          form.lon = data["results"][0]["geometry"]["location"]["lng"];
          form.stateget = data["results"][0]["address_components"][5]["long_name"];
          form.cityget = form.city;
          console.log(form.lat+"  "+ form.lon + " "+form.stateget);
          this.getWeatherInfo(form);
        });*/
    }
  }
}