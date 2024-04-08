import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'age'
})
  
export class AgeFromMillisecondsPipe implements PipeTransform {

  transform(birthDateInMilliseconds: number): number | null {
    
    if (!birthDateInMilliseconds) {
      return null;
    }

    const currentDate = new Date();
    const birthDate = new Date(birthDateInMilliseconds);

    return currentDate.getFullYear() - birthDate.getFullYear();
  }
}