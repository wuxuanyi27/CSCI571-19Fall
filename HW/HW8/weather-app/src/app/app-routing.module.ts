import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CurrentTabComponent } from './result-table/current-tab/current-tab.component';
import { HourlyTabComponent } from './result-table/hourly-tab/hourly-tab.component';
import { WeeklyTabComponent } from './result-table/weekly-tab/weekly-tab.component';

const routes: Routes = [
  { path: 'current-tab', component: CurrentTabComponent },
  { path: 'hourly-tab', component: HourlyTabComponent },
  { path: 'weekly-tab', component: WeeklyTabComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
