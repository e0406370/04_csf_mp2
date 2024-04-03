import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MainComponent } from './main/main.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { notCompletedRegistration } from './utility/guards';

const appRoutes: Routes = [
  { path: '', component: MainComponent },
  { path: 'register', component: UserRegistrationComponent, canDeactivate: [notCompletedRegistration] },
  { path: 'login', component: UserLoginComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { useHash: true })],
  exports: [RouterModule],
})
  
export class AppRoutingModule {}
