import { Injectable, inject } from '@angular/core';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
  
export class UtilityService {

  private messageSvc = inject(MessageService);

  generateSuccessMessage(message: string): void {

    this.messageSvc.add({
      severity: 'success',
      summary: 'Success',
      detail: message,
      life: 3500,
    });
  }

  generateErrorMessage(message: string): void {

    this.messageSvc.add({
      severity: 'error',
      summary: 'Error',
      detail: message,
      life: 3500,
    });
  }

  updateDateDayTime(): string {

    const currentDate = new Date();

    const options: Intl.DateTimeFormatOptions = {
      weekday: "long",
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      second: "numeric",
      hour12: false,
    };
    
    return currentDate.toLocaleDateString('en-US', options);
  }
}
