//loader.interceptor.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { SearchService } from '../services/search.Service';
import { Subscription } from 'rxjs';
import { MessageService } from '../services/message.service';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent implements OnInit, OnDestroy {

  //show = false;
  //private subscription: Subscription;
  private subscription: Subscription;
  load: number = 0;
  constructor(private sService: SearchService, private message: MessageService) {
    this.sService.loadState.subscribe((v) => {
      //console.log(v);
      this.load = Number(v);
      this.message.sendMessage(this.load);
      this.message.sendMessage2('current-tab');
    });
  }
  ngOnInit() {
    /*this.subscription = this.loaderService.loaderState.subscribe(
      (state: boolean) => {
        this.show = state;
        console.log("show: " + this.show);
      }
    );*/
  }
  
  ngOnDestroy(){
    //this.subscription.unsubscribe();
  }

}
