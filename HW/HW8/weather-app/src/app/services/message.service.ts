import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private subject = new Subject<any>();
  private subject2 = new Subject<any>();
  sendMessage(type:number){
    //console.log('TAG' + '---------->>>' + type);
    //console.log(type);
    this.subject.next({type: type});
  }
  clearMessage() {
    this.subject.next();
    this.subject2.next();
  }

  sendMessage2(type: String){
    //console.log('TAG' + '------->>' + type);
    this.subject2.next({type: type});
  }

  getMessage2(): Observable<any> {
    return this.subject2.asObservable();
  }
  getMessage(): Observable<any> {
    return this.subject.asObservable();
  }

  constructor() { }
}
