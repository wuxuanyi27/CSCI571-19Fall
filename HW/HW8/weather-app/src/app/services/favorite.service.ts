import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { ResultContainerComponent } from '../result-container/result-container.component';
import { stringify } from '@angular/compiler/src/util';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {
  private _favorite = new Subject();
  favorite = this._favorite.asObservable();

  private _isStorageChange = new Subject();
  isStorageChange = this._isStorageChange.asObservable();

  constructor() { }

  getFavorites() {
    let localStorageArray = new Array(localStorage.length);
    for (let i = 0; i < localStorage.length; i++){
      localStorageArray[i] = JSON.parse(localStorage.getItem(localStorage.key(i)));
    }
    this._favorite.next(localStorageArray);
    this._isStorageChange.next(true);
    //console.log(localStorageArray);
  }

  isFavorited(city_name){
    if (!localStorage.getItem(city_name)){
      return false;
    }
    return true;
  }

  saveFavorite(form){
    let key = form.cityget;
    //console.log(form.stateSeal);
    let favJson ={
      stateSeal: form.stateSeal,
      city: form.cityget,
      state: form.stateA
    }
    localStorage.setItem(key, JSON.stringify(favJson));
    //console.log(localStorage);
    this.getFavorites();
  }

  removeFavorite(key){
    localStorage.removeItem(key);
    this.getFavorites();
    //console.log(localStorage);
  }

}
