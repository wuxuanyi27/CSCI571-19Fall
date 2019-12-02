import { Directive, AfterViewInit, ElementRef } from '@angular/core';
declare var $:any;

@Directive({
  selector: '[tooltip]'
})
export class TooltipDirective implements AfterViewInit{
  ngAfterViewInit(): void {
    $(this.elementRef.nativeElement).tooltip({placement: 'top'});
  }

  constructor(private elementRef: ElementRef) { }

}
