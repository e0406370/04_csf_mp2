import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ErrorComponent } from './error/error.component';
import { MainComponent } from './main/main.component';
import { UserConfirmationComponent } from './user-confirmation/user-confirmation.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

import { validateUserID, notCompletedRegistration } from './utility/guards';

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
    canActivate: [validateUserID],
    data: { endpoint: 'confirm' },
  },
  {
    path: 'login',
    component: UserLoginComponent
  },
  {
    path: 'register',
    component: UserRegistrationComponent,
    canDeactivate: [notCompletedRegistration],
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
