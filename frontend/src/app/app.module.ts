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
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgOtpInputModule } from 'ng-otp-input';

import { AppComponent } from './app.component';
import { ErrorComponent } from './error/error.component';
import { MainComponent } from './main/main.component';
import { UserConfirmationComponent } from './user-confirmation/user-confirmation.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

import { MessageService } from 'primeng/api';
import { UtilityService } from './utility/utility.service';
import { SessionStore } from './utility/session.store';
import { UserConfirmationService } from './user-confirmation/user-confirmation.service';
import { UserLoginService } from './user-login/user-login.service';
import { UserProfileService } from './user-profile/user-profile.service';
import { UserRegistrationService } from './user-registration/user-registration.service';

import { AgeFromMillisecondsPipe } from "./utility/age.pipe";


@NgModule({
    declarations: [
        AppComponent,
        ErrorComponent,
        MainComponent,
        UserConfirmationComponent,
        UserLoginComponent,
        UserProfileComponent,
        UserRegistrationComponent,
    ],
    providers: [
        MessageService,
        UtilityService,
        SessionStore,
        UserConfirmationService,
        UserLoginService,
        UserProfileService,
        UserRegistrationService,
        provideAnimationsAsync(),
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
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
        ConfirmDialogModule,
        NgbModule,
        NgOtpInputModule,
        AgeFromMillisecondsPipe
    ]
})
export class AppModule {}
