import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl } from "@angular/forms";


@Directive({
  selector: '[noWhitespace]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: NowhitespaceDirective, multi: true }
  ]
})
export class NowhitespaceDirective implements Validator{

  validate(control: AbstractControl): { [key: string]: any } {
    let isWhitespace = (control.value || "").trim().length === 0;
      return isWhitespace? {'whitespace': 'input only contains whitespace'} : null;
  }

}
