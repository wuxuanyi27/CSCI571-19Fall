import { Component, OnInit } from '@angular/core';
import { FavoriteService } from '../services/favorite.service';
import { SearchService } from '../services/search.Service';
import { SearchForm } from '../search-form/search-form';
import { MessageService } from '../services/message.service';

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})
export class FavoriteComponent implements OnInit {

  favorites : any;
  form = SearchForm;

  constructor(private fService: FavoriteService, private sService: SearchService, private message: MessageService) { 
    this.fService.favorite.subscribe(data =>{
      this.favorites = data;
      /*if (this.favorites != ""){
        console.log("11111");
      }*/
    })
    this.sService.isClear.subscribe(data => {
      //console.log(data);
    })
    //this.message.sendMessage2('current-tab');
  }

  removeFavorite(key) {
    this.fService.removeFavorite(key);
  }

  showFavorite(info){
    //console.log(this.form);
    //this.sService.clear();
    this.sService.getFavorite(info, this.form);
  }

  ngOnInit() {
    this.fService.getFavorites();
  }


}
