import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ErrorComponent } from './error/error.component';
import { MainComponent } from './main/main.component';
import { UserConfirmationComponent } from './user-confirmation/user-confirmation.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

import { notCompletedRegistration, validateLogin, validateUserConfirmation } from './utility/guards';
import { UserLogoutComponent } from './user-logout/user-logout.component';
import { EventListComponent } from './event-list/event-list.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventBookmarksComponent } from './event-bookmarks/event-bookmarks.component';


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
    path: 'events/bookmarks',
    component: EventBookmarksComponent,
  },
  {
    path: 'events/:eventID',
    component: EventDetailsComponent
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
