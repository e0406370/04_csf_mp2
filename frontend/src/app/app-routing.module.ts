import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MainComponent } from './main/main.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';

const appRoutes: Routes = [
  { path: '', component: MainComponent },
  { path: 'register', component: UserRegistrationComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, { useHash: true })],
  exports: [RouterModule],
})
  
export class AppRoutingModule {}
