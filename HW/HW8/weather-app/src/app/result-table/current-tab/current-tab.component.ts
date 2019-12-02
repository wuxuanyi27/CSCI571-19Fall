import { Component, OnInit, Input } from '@angular/core';
import { SearchService } from "../../services/search.Service";
import { SearchForm } from "../../search-form/search-form";
import { Form } from '@angular/forms';
import { ResultTableComponent } from "../result-table.component";
import { Current } from './current';

@Component({
  selector: 'app-current-tab',
  templateUrl: './current-tab.component.html',
  styleUrls: ['./current-tab.component.css']
})
export class CurrentTabComponent implements OnInit {
  @Input() crrnt: Current;
  weather_json: any = null;
  form: any = SearchForm;

  constructor(public rInfo: ResultTableComponent) {
  }

  ngOnInit() {
  }

}
