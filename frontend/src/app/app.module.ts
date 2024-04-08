import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { AutoFocusModule } from 'primeng/autofocus';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgOtpInputModule } from 'ng-otp-input';

import { AppComponent } from './app.component';
import { ErrorComponent } from './error/error.component';
import { MainComponent } from './main/main.component';
import { UserConfirmationComponent } from './user-confirmation/user-confirmation.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

import { UserLoginService } from './user-login/user-login.service';
import { UserRegistrationService } from './user-registration/user-registration.service';
import { MessageService } from 'primeng/api';
import { UtilityService } from './utility/utility.service';
import { AuthenticationService } from './utility/authentication.service';


@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    MainComponent,
    UserConfirmationComponent,
    UserLoginComponent,
    UserRegistrationComponent,  
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MaterialModule,
    AutoFocusModule,
    CardModule,
    ToastModule,
    MessagesModule,
    NgbModule,
    NgOtpInputModule,
  ],
  providers: [
    MessageService,
    UtilityService,
    AuthenticationService,
    UserLoginService,
    UserRegistrationService,
    provideAnimationsAsync(),
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
