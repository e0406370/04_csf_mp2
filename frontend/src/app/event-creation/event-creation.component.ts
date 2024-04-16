import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { UtilityService } from '../utility/utility.service';
import { EventCreationService } from './event-creation.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ERROR_MESSAGE } from '../utility/constants';

@Component({
  selector: 'app-event-creation',
  templateUrl: './event-creation.component.html',
  styleUrl: './event-creation.component.css'
})
  
export class EventCreationComponent implements OnInit {

  private fb = inject(FormBuilder);
  private router = inject(Router);

  private utilitySvc = inject(UtilityService);
  private eventCreationSvc = inject(EventCreationService);

  eventCreationForm!: FormGroup;

  ngOnInit(): void {
    
    this.eventCreationSvc.loadAutoCompleteMap();
    this.eventCreationForm = this.createEventCreationForm();
  }

  createEventCreationForm(): FormGroup {

    return this.fb.group({
      name: this.fb.control<string>('', [
        Validators.required,
        Validators.maxLength(100),
      ]),
      description: this.fb.control<string>('', [
        Validators.required,
        Validators.maxLength(256),
      ]),
      logo: this.fb.control<string>('', [
        Validators.maxLength(256)
      ]),
      start: this.fb.control<string>('', [
        Validators.required,
        this.eventCreationSvc.futureDateValidator.bind(this),
      ]),
      end: this.fb.control<string>('', [
        Validators.required,
        this.eventCreationSvc.futureDateValidator.bind(this),
      ]),
    })
  }

  isFormInvalid(): boolean {

    if (this.eventCreationForm.invalid) {
      return true;
    }
    else {
      return this.eventCreationSvc.cannotSubmit;
    }
  }

  submitEventCreationForm(): void {

    const details: any = this.eventCreationSvc.parseEventCreationForm(this.eventCreationForm);
    console.info(details);

    if (details.creation.start >= details.creation.end) {
      alert("Event end must be later than its start! Please review the form again!")
    }
    else {
      this.eventCreationSvc.createEvent(details)
        .then((res) => {
        
          this.utilitySvc.generateSuccessMessage(res.message);
          this.eventCreationForm.reset();

          this.router.navigate(['/events', res?.response.eventID]);
        })
        .catch((err) => {
          
          this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
        })
    }
  }
}
