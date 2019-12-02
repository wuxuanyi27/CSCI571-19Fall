import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchFormComponent } from './search-form/search-form.component';
import { ResultContainerComponent } from './result-container/result-container.component';
import { NowhitespaceDirective } from './directives/nowhitespace.directive';
import { LoaderComponent } from './loader/loader.component';
import { ResultTableComponent } from './result-table/result-table.component';
import { FavoriteComponent } from './favorite/favorite.component';
import { CurrentTabComponent } from './result-table/current-tab/current-tab.component';
import { HourlyTabComponent } from './result-table/hourly-tab/hourly-tab.component';
import { WeeklyTabComponent, NgbdModalContent } from './result-table/weekly-tab/weekly-tab.component';
import { SearchService } from './services/search.Service';
import { ChartsModule } from 'ng2-charts';
import { TooltipDirective } from './directives/tooltip.directive';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TooltipbtmDirective } from './directives/tooltipbtm.directive';

@NgModule({
  declarations: [
    AppComponent,
    SearchFormComponent,
    ResultContainerComponent,
    NowhitespaceDirective,
    LoaderComponent,
    ResultTableComponent,
    FavoriteComponent,
    CurrentTabComponent,
    HourlyTabComponent,
    WeeklyTabComponent,
    TooltipDirective,
    NgbdModalContent,
    TooltipbtmDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ChartsModule,
    NgbModule,
    BrowserAnimationsModule,
    MatAutocompleteModule
  ],
  providers: [
    SearchService
  ],
  bootstrap: [AppComponent],
  entryComponents: [NgbdModalContent]
})
export class AppModule {
 }
