import { Component, OnInit, Input } from '@angular/core';
import * as CanvasJS from '../../../assets/canvasjs.min';
import { ResultTableComponent } from '../result-table.component';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SearchService } from '../../services/search.Service';
import { SearchForm } from '../../search-form/search-form';


@Component({
  selector: 'ngbd-modal-content',
  template: `
    <div class="modal-header" style="border-radius: 0.3rem 0.3rem 0 0; background: #6F91AA;">
      <h4 class="modal-title">{{date}}</h4>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body" style="padding: 0.5rem 2rem; background: #A6CFEE;">
    <div class="row">
    <div class = "col-sm-7 col-7 col-form-label">
      <div id = 'city' style="font-size: 1.4rem">
        {{city}}
      </div>
      <div id ='detail_t' style = "font-size: 2rem">
        {{tem}}
        <span id = 't_icon1'><img src ='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png' height="10px" width="10px" style="vertical-align: top"></span>
        <span id = 'F-sign1'>F</span>
      </div>
      <div id = 'smmry' style = "font-szie: 0.8rem">
        {{data['currently']['summary']}}
      </div>
    </div>
      <div id = 'detail_weather_pic' class="col-sm-4 col-4 col-form-label"><img src ={{weather_pic}} width="100px">
      </div>
    </div>
      <hr width="90%" style="margin: 0 auto">
    </div>
    <div class="modal-footer" style="border-top: 0 none; background: #A6CFEE;">
      <div class = "detail_info" style="margin-right: 30px">
      <p id = 'precip' style="margin-bottom: 1%">Precipitsion: <span>{{data['currently']['precipIntensity']}}</span></p>
      <p id = 'rainChance' style="margin-bottom: 1%">Chance of Rain: <span>{{(data['currently']['precipProbability'] * 100).toFixed(2)}}</span><span class = 'units'> %</span></p>
      <p id = 'wdspd' style="margin-bottom: 1%">Wind Speed: <span>{{data['currently']['windSpeed'].toFixed(2)}} </span><span class = 'units'> mph</span></p>
      <p id = 'humidity' style="margin-bottom: 1%">Humidity: <span>{{(data['currently']['humidity'] * 100).toFixed(2)}}</span><span class = 'units'> %</span></p>
      <p id = 'visibility' style="margin-bottom: 1%">Visibility: <span>{{data['currently']['visibility']}}</span><span class = 'units'> miles</span></p>
      </div>
    </div>
  `
})

export class NgbdModalContent {

  @Input() data;
  @Input() date;
  @Input() tem;
  @Input() city;
  @Input() weather_pic;
  //tem: number;
  constructor(public activeModal: NgbActiveModal) {
    //console.log();
    //this.tem = Math.round(this.data['currently']['temperature']);
   }
}

@Component({
  selector: 'app-weekly-tab',
  templateUrl: './weekly-tab.component.html',
  styleUrls: ['./weekly-tab.component.css']
})
export class WeeklyTabComponent implements OnInit {
  dailyInfo: any;
  ajstT: string[] = [];
  rawT: number[] = [];
  tlow: number[] = [];
  thigh: number[] = [];
  lat: any;
  lon: any;
  form = SearchForm;

  /*adjustT(time) {
    if (time == 0) return 0;
    if (time > 12) return (24 - time);
    return (time * -1);
  }
  covertT(time10, adjst_time) {
    var date_time = new Date();
    date_time.setTime(time10);
    var year = date_time.getFullYear();
    var month = date_time.getMonth() + 1;
    var date = date_time.getDate();
    if (adjst_time > 0) {
      date += 1;
    }
    return date + "/" + month + "/" + year;
  }

  toHour(time: number) {
    const date_time = new Date(time * 1000);
    return date_time.getHours();
  }*/

  constructor(public rInfo: ResultTableComponent, private modalService: NgbModal, private sService: SearchService) {
    this.dailyInfo = this.rInfo.weather_json['daily']['data'];
    this.lat = this.rInfo.weather_json['latitude'];
    this.lon = this.rInfo.weather_json['longitude'];
    //console.log(this.dailyInfo);
    for (var i = 0; i < 7; i++) {
      let tmp = this.dailyInfo[i]['time'];
      this.rawT.push(tmp);
      //console.log(tmp+ " "+ i);
      let item1 = new Date(tmp * 1000).toLocaleString("en-GB",{timeZone: this.rInfo.weather_json["timezone"]}).split(',')[0];
      //console.log(item1);
      //let hour = this.toHour(tmp);
      //console.log(hour + " " + i);
      //hour = this.adjustT(hour);
      //let item = this.covertT(tmp * 1000, hour);
      //console.log(item);
      let tmplow = this.dailyInfo[i]['temperatureLow'];
      tmplow = Math.round(tmplow);
      let tmphigh = this.dailyInfo[i]['temperatureHigh'];
      tmphigh = Math.round(tmphigh);
      this.ajstT.push(item1);
      this.tlow.push(tmplow);
      this.thigh.push(tmphigh);
    }
    //console.log(this.ajstT);
  }

  ngOnInit() {
    let chart = new CanvasJS.Chart("chartContainer", {
      animationEnabled: true,
      dataPointWidth: 15,
      title: {
        text: "Weekly Weather"
      },
      axisX: {
        title: "Days"
      },
      legend: {
        verticalAlign: "top",
      },
      axisY: {
        includeZero: false,
        title: "Temperature in Fahrenheit",
        interval: 10,
        gridThickness: 0
        //suffix: "k",
        //prefix: "$"
      },
      data: [{
        type: "rangeBar",
        color: "#A6CFEE",
        showInLegend: true,
        //yValueFormatString: "$#0.#K",
        indexLabel: "{y[#index]}",
        legendText: "Day wise temperature range",
        toolTipContent: "<b>{label}</b>: {y[0]} to {y[1]}",
        click: (e) => {
          this.onClick(e.dataPointIndex)
        },
        dataPoints: [
          //x:this.ajstT y :[this.tlow, this.thigh]
          { x: 70, y: [this.tlow[0], this.thigh[0]], label: this.ajstT[0] },
          { x: 60, y: [this.tlow[1], this.thigh[1]], label: this.ajstT[1] },
          { x: 50, y: [this.tlow[2], this.thigh[2]], label: this.ajstT[2] },
          { x: 40, y: [this.tlow[3], this.thigh[3]], label: this.ajstT[3] },
          { x: 30, y: [this.tlow[4], this.thigh[4]], label: this.ajstT[4] },
          { x: 20, y: [this.tlow[5], this.thigh[5]], label: this.ajstT[5] },
          { x: 10, y: [this.tlow[6], this.thigh[6]], label: this.ajstT[6] },
          //{ x:this.ajstT, y:[this.tlow, this.thigh], label: this.ajstT}
        ]
      }]
    });
    chart.render();
  }
  onClick(index) {
    //console.log(this.lat+" "+ this.lon+" "+this.rawT[index]);
    this.sService.getDetail(this.lat,this.lon,this.rawT[index]).subscribe(data =>{
      //console.log(data);
      //console.log(this.form);
      const modalRef = this.modalService.open(NgbdModalContent, { size: 'lg' });
      //console.log(data['currently']['precipIntensity']);
      if (data['currently']['precipIntensity'] != 0) data['currently']['precipIntensity'] = data['currently']['precipIntensity'].toFixed(2);
      modalRef.componentInstance.city = this.form.cityget;
      modalRef.componentInstance.data = data;
      modalRef.componentInstance.tem = Math.round(data['currently']['temperature']);
      modalRef.componentInstance.date = this.ajstT[index];
      switch(data['currently']['icon']){
        case "clear-day": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";break;
				case "clear-night": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";break;
				case "rain": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png";break;
				case "snow": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png";break;
				case "sleet": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png";break;
				case "wind": modalRef.componentInstance.weather_pic = "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png";break;
				case "fog": modalRef.componentInstance.weather_pic = "<https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png";break;
				case "cloudy": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png";break;
				case "partly-cloudy-day": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";break;
				case "partly-cloudy-night": modalRef.componentInstance.weather_pic = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";break;
      }
    });
    //console.log(this.rawT);
    

  }

}
