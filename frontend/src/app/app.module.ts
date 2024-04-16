import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { AutoFocusModule } from 'primeng/autofocus';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { PaginatorModule } from 'primeng/paginator';
import { PanelModule } from 'primeng/panel';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import { NgOtpInputModule } from 'ng-otp-input';

import { AppComponent } from './app.component';
import { ErrorComponent } from './error/error.component';
import { MainComponent } from './main/main.component';
import { UserConfirmationComponent } from './user-confirmation/user-confirmation.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserLogoutComponent } from './user-logout/user-logout.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { EventListComponent } from './event-list/event-list.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventBookmarksComponent } from './event-bookmarks/event-bookmarks.component';

import { MessageService } from 'primeng/api';
import { UtilityService } from './utility/utility.service';
import { ThemeService } from './utility/theme.service';
import { SessionStore } from './utility/session.store';
import { UserConfirmationService } from './user-confirmation/user-confirmation.service';
import { UserLoginService } from './user-login/user-login.service';
import { UserProfileService } from './user-profile/user-profile.service';
import { UserRegistrationService } from './user-registration/user-registration.service';
import { EventListService } from './event-list/event-list.service';
import { EventDetailsService } from './event-details/event-details.service';
import { EventBookmarksService } from './event-bookmarks/event-bookmarks.service';

import { AgeFromMillisecondsPipe } from './utility/age.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    MainComponent,
    UserConfirmationComponent,
    UserLoginComponent,
    UserLogoutComponent,
    UserProfileComponent,
    UserRegistrationComponent,
    EventListComponent,
    EventDetailsComponent,
    EventBookmarksComponent,
  ],
  providers: [
    MessageService,
    UtilityService,
    ThemeService,
    SessionStore,
    UserConfirmationService,
    UserLoginService,
    UserProfileService,
    UserRegistrationService,
    EventListService,
    EventDetailsService,
    EventBookmarksService,
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
    DividerModule,
    PaginatorModule,
    PanelModule,
    ToastModule,
    MessagesModule,
    NgOtpInputModule,
    AgeFromMillisecondsPipe,
  ],
})
    
export class AppModule {}
