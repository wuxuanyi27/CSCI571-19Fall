import { SearchForm } from "./search-form";
import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { SearchService } from "../services/search.Service";
import { observable, Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
//import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {
  form: any = SearchForm;
  myControl = new FormControl();
  options : any;
  //gotgeojson: boolean = false;
  //userLocation: Object;

  constructor(public sService: SearchService, private cdRef: ChangeDetectorRef, private http:HttpClient) {
    //console.log(this.form);
   }

  states = [
    "Select State",
    "Alabama",
    "Alaska",
    "Arizona",
    "Arkansas",
    "California",
    "Colorado",
    "Connecticut",
    "Delaware",
    "District Of Columbia",
    "Florida",
    "Georgia",
    "Hawaii",
    "Idaho",
    "Illinois",
    "Indiana",
    "Iowa",
    "Kansas",
    "Kentucky",
    "Louisiana",
    "Maine",
    "Maryland",
    "Massachusetts",
    "Michigan",
    "Minnesota",
    "Mississippi",
    "Missouri",
    "Montana",
    "Nebraska",
    "Nevada",
    "New Hampshire",
    "New Jersey",
    "New Mexico",
    "New York",
    "North Carolina",
    "North Dakota",
    "Ohio",
    "Oklahoma",
    "Oregon",
    "Pennsylvania",
    "Rhode Island",
    "South Carolina",
    "South Dakota",
    "Tennessee",
    "Texas",
    "Utah",
    "Vermont",
    "Virginia",
    "Washington",
    "West Virginia",
    "Wisconsin",
    "Wyoming"
  ];

  

  clear() {
    this.sService.clear();
    this.form.state = 'Select State';
    this.form.curLocation = false;
    this.form.stateSeal = "";
    this.form.cityget = "";
    this.form.stateget = "";
    this.form.stateA = "";
    //console.log(this.form);
  }

  ngOnInit() {
  }

  onSubmit() {
    if (this.form.curLocation == true) {
      this.sService.getGeolocation(this.form);
    }
    else {
      this.sService.search(this.form);
    }
  }

  getAutoComplete(event: any){
    this.options = [];
    var cty = this.form.city.trim();
    //console.log(cty+ " " + this.form.city);
    if (this.form.city != "" && cty.length != 0){// check all blank 
      this.sService.getAutoComplete(this.form.city).subscribe(data =>{
       // console.log(data);
        var nums = data["predictions"];
        for (var i = 0; i < nums.length; i++){
          var ac = nums[i]["structured_formatting"]["main_text"];
          this.options.push(ac);
        }
        //console.log(this.options);
      });
    }
  }

  /*getGeo() {
    //console.log(this.form.curLocation);
    if (this.form.curLocation == true){
      this.sService.getGeolocation().subscribe(data => {
        //console.log(data);
        this.form.lat = data["lat"];
        this.form.lon = data["lon"];
        this.form.stateget = data["regionName"];
        this.form.cityget = data["city"];
        this.form.curLocation = true; 
        this.form.stateA = data["region"];
        console.log(this.form);
        
      });
     // source.subscribe((data) => console.log(data),(err) => console.log(err));
    }
  }*/
  
}
