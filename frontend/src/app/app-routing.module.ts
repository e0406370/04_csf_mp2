import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ErrorComponent } from './error/error.component';
import { EventBookmarksComponent } from './event-bookmarks/event-bookmarks.component';
import { EventCreationComponent } from './event-creation/event-creation.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventListComponent } from './event-list/event-list.component';
import { EventRegistrationsComponent } from './event-registrations/event-registrations.component';
import { MainComponent } from './main/main.component';
import { UserAccountComponent } from './user-account/user-account.component';
import { UserConfirmationComponent } from './user-confirmation/user-confirmation.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserLogoutComponent } from './user-logout/user-logout.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

import { notCompletedRegistration, validateLogin, validateUserConfirmation } from './utility/guards';


const appRoutes: Routes = [
  {
    path: 'error',
    component: ErrorComponent
  },
  {
    path: '',
    component: MainComponent
  },
  {
    path: 'account',
    component: UserAccountComponent
  },
  {
    path: 'confirm/:userID',
    component: UserConfirmationComponent,
    canActivate: [validateUserConfirmation],
  },
  {
    path: 'profile/:userID',
    component: UserProfileComponent,
    canActivate: [validateLogin],
  },
  {
    path: 'login',
    component: UserLoginComponent
  },
  {
    path: 'logout',
    component: UserLogoutComponent
  },
  {
    path: 'register',
    component: UserRegistrationComponent,
    canDeactivate: [notCompletedRegistration],
  },
  {
    path: 'events',
    component: EventListComponent
  },
  {
    path: 'events/:eventID',
    component: EventDetailsComponent
  },
  {
    path: 'bookmarks',
    component: EventBookmarksComponent
  },
  {
    path: 'registrations',
    component: EventRegistrationsComponent
  },
  {
    path: 'creation',
    component: EventCreationComponent
  },
  {
    path: '**',
    redirectTo: '/error',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { useHash: true })],
  exports: [RouterModule],
})
  
export class AppRoutingModule {}
