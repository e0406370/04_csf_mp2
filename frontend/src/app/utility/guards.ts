import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot } from "@angular/router";

import { UserConfirmationService } from "../user-confirmation/user-confirmation.service";
import { UserRegistrationComponent } from "../user-registration/user-registration.component";
import { ERROR_MESSAGE } from "./constants";
import { SessionStore } from "./session.store";
import { UtilityService } from "./utility.service";

export const notCompletedRegistration: CanDeactivateFn<UserRegistrationComponent> = (comp: UserRegistrationComponent, _route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {
  
  if (comp.userRegistrationForm.dirty) {

    return confirm('You have not completed the registration form.\nAre you sure you want to leave?');
  }
    
  return true;
}

export const validateUserConfirmation: CanActivateFn = (_route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {

  const router = inject(Router);
  const utilitySvc = inject(UtilityService);
  const userConfirmationSvc = inject(UserConfirmationService);

  const userID = _route.params['userID'];

  return userConfirmationSvc.confirmUserGet(userID)
    .then(() => {
      
      userConfirmationSvc.userID = userID;
      return true;
    })
    .catch((err) => {

      utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
      return router.parseUrl('/error');
    });
}

export const validateLogin: CanActivateFn = (_route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {

  const router = inject(Router);
  const utilitySvc = inject(UtilityService);
  const sessionStore = inject(SessionStore);

  if (!sessionStore.isLoggedIn()) {
    
    utilitySvc.generateErrorMessage("You must be logged in to access this page.");
    return router.parseUrl('/login');
  }

  return true;
}