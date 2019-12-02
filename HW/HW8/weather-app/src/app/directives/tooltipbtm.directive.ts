import { Directive, AfterViewInit, ElementRef } from '@angular/core';
import { element } from 'protractor';
declare var $:any;

@Directive({
  selector: '[tooltipbtm]'
})
export class TooltipbtmDirective implements AfterViewInit{
  ngAfterViewInit(): void {
    //console.log(this.elementRef.nativeElement);
    $(this.elementRef.nativeElement).tooltip({placement: 'bottom', trigger: 'hover'});
    /*let element = this.elementRef.nativeElement;
    element.addEventListener("click",function(){
      $(element).tooltip("dispose");
    });*/
    
  }
  constructor(private elementRef: ElementRef) { }

}
