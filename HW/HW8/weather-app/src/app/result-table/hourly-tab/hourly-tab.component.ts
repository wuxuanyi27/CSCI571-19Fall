import { Component, OnInit, Input} from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { ResultTableComponent } from '../result-table.component';
import { HourlyInfo } from './hourly-tab';

@Component({
  selector: 'app-hourly-tab',
  templateUrl: './hourly-tab.component.html',
  styleUrls: ['./hourly-tab.component.css']
})

export class HourlyTabComponent implements OnInit {

  hrlyInfo: any;
  hour = HourlyInfo;
  hrdata : number [] = [];
  y_label: any = 'Fahrenheit';
  max : number;
  min : number;
  niceMin: any;
  niceMax: any;

  constructor(public rInfo: ResultTableComponent) {
    this.hour.cate = "temperature";
    //console.log(this.rInfo.weather_json);
    this.hrlyInfo = this.rInfo.weather_json["hourly"]["data"];
    //console.log(this.hrlyInfo);
    for(var i = 0; i < 24; i++){
      let tmp = this.hrlyInfo[i][this.hour.cate];
      if (i == 0) {
        this.max = tmp;
        this.min = tmp;
      }
      else {
        if (tmp > this.max) this.max = tmp;
        if (tmp < this.min) this.min = tmp;
      }
      this.hrdata.push(tmp);
    }
    /*var range = this.niceNum(this.max - this.min, false);
    var tickSpacing = this.niceNum(range / 9, true);
    //console.log(tickSpacing);
    this.niceMin = Math.floor(this.min / tickSpacing) * tickSpacing;
    this.niceMax = Math.ceil(this.max / tickSpacing) * tickSpacing;
    this.niceMax = Math.ceil(this.max + tickSpacing);
    //console.log(this.niceMax, this.niceMin);*/
    this.changecate(this.hour.cate);
    //console.log(this.cates[0]);
   }
  public barChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      onClick: (e) => e.stopPropagation()
    },
    // We use these empty structures as placeholders for dynamic theming.
    scales: { 
      xAxes: [{
      scaleLabel:{
        display: true,
        labelString:'Time different from current hour'
      }
    }], 
    yAxes: [{
      scaleLabel:{
        display: true,
        labelString: this.y_label
      },
      ticks :{suggestedMax: this.niceMax, suggestedMin: this.niceMin},
    }] },
  };
  public barChartLabels: Label[] = ['0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23'];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;

  public barChartData: ChartDataSets[] = [{ 
    data: this.hrdata, 
    label: this.hour.cate,
    backgroundColor: 
      'rgb(166, 207, 238)',
    hoverBackgroundColor:
      'rgb(93, 134, 163)',
    borderColor:
      'rgb(166, 207, 238)',
  }];

   cates = [
    {key : "temperature", value : "Temperature"},
    {key : "pressure", value : "Pressure"},
    {key : "humidity", value : "Humidity"},
    {key : "ozone", value : "Ozone"},
    {key : "visibility", value : "Visibility"},
    {key : "windSpeed", value : "Wind Speed"}
  ];

  public chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
    //console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
    //console.log(event, active);
  }

  /*niceNum( localRange:any, round:any) {
    var exponent; /** exponent of localRange */
    //var fraction; /** fractional part of localRange */
    //var niceFraction; /** nice, rounded fraction */

   /* exponent = Math.floor(Math.log10(localRange));
    fraction = localRange / Math.pow(10, exponent);

    if (round) {
        if (fraction < 1.5)
            niceFraction = 1;
        else if (fraction < 3)
            niceFraction = 2;
        else if (fraction < 7)
            niceFraction = 5;
        else
            niceFraction = 10;
    } else {
        if (fraction <= 1)
            niceFraction = 1;
        else if (fraction <= 2)
            niceFraction = 2;
        else if (fraction <= 5)
            niceFraction = 5;
        else
            niceFraction = 10;
    }
    return niceFraction * Math.pow(10, exponent);
}*/

  changecate(cate){
    this.hour.cate = cate;
    for(var i = 0; i< 24; i++){
      let tmp = this.hrlyInfo[i][this.hour.cate];
      this.hrdata[i]=tmp;
      if (i == 0) {
        this.max = tmp;
        this.min = tmp;
      }
      else {
        if (tmp > this.max) this.max = tmp;
        if (tmp < this.min) this.min = tmp;
      }
    }
    //var range = this.niceNum(this.max - this.min, false);
    //var tickSpacing = this.niceNum(range / 9, true);
    //console.log(tickSpacing);
    //this.niceMin = Math.floor(this.min / tickSpacing) * tickSpacing;
    //this.niceMax = Math.ceil(this.max / tickSpacing) * tickSpacing;
    this.niceMax = Math.ceil(this.max + (this.max - this.min) / 9 );
    this.niceMin = Math.ceil(this.min - (this.max - this.min) / 5 );
    console.log(this.niceMax, this.niceMin);
    //console.log(this.hour.cate);
    switch(this.hour.cate){
      case 'temperature': 
      this.y_label = 'Fahrenheit'; break;
      case 'pressure': this.y_label = 'Millibars'; break;
      case 'humidity': this.y_label = '% Humidity'; break;
      case 'ozone': this.y_label = 'Dobson Units'; break;
      case 'visibility': this.y_label = 'Miles (Maximum 10)'; break;
      case 'windSpeed': this.y_label = 'Miles per Hour'; break;
      }
    this.barChartData = [{
      data: this.hrdata, 
      label: this.hour.cate,
      backgroundColor: 
      'rgb(166, 207, 238)',
      hoverBackgroundColor:
      'rgb(93, 134, 163)',
      borderColor:
      'rgb(166, 207, 238)',
    }];
    this.barChartOptions = {
      responsive: true,
      legend: {
        onClick: (e) => e.stopPropagation()
      },
      // We use these empty structures as placeholders for dynamic theming.
      scales: { 
        xAxes: [{
        scaleLabel:{
          display: true,
          labelString:'Time different from current hour'
        }
      }], 
      yAxes: [{
        scaleLabel:{
          display: true,
          labelString: this.y_label,
        },
        ticks :{suggestedMax: this.niceMax, suggestedMin: this.niceMin},
      }] },
    };
    //console.log(this.hrdata.length);
  }

  ngOnInit() {
  }

  ngOnChanges (){
    

  }
}
