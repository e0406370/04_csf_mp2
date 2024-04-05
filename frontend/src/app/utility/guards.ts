import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { UserConfirmationService } from "../user-confirmation/user-confirmation.service";
import { UserRegistrationComponent } from "../user-registration/user-registration.component";

export const notCompletedRegistration: CanDeactivateFn<UserRegistrationComponent> = (comp: UserRegistrationComponent, _route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {
  
  if (comp.userRegistrationForm.dirty) {

    return confirm('You have not completed the registration form.\nAre you sure you want to leave?');
  }
    
  return true;
}

export const correctConfirmationCode: CanActivateFn = (_route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {

  const router = inject(Router);
  const userConfirmationSvc = inject(UserConfirmationService);

  const userID = _route.params['userID'];

  return userConfirmationSvc.confirmUserGet(userID)
    .then(() => {
      
      userConfirmationSvc.userID = userID;
      return true;
    })
    .catch((err) => {

      alert(`Error: ${JSON.stringify(err)}`);
      return router.parseUrl('/error');
    });
}