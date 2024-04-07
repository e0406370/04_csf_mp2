import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { UserConfirmationService } from "../user-confirmation/user-confirmation.service";
import { UserRegistrationComponent } from "../user-registration/user-registration.component";
import { MessageService } from "primeng/api";

export const notCompletedRegistration: CanDeactivateFn<UserRegistrationComponent> = (comp: UserRegistrationComponent, _route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {
  
  if (comp.userRegistrationForm.dirty) {

    return confirm('You have not completed the registration form.\nAre you sure you want to leave?');
  }
    
  return true;
}

export const correctConfirmationCode: CanActivateFn = (_route: ActivatedRouteSnapshot, _state: RouterStateSnapshot) => {

  const router = inject(Router);
  const userConfirmationSvc = inject(UserConfirmationService);
  const messageSvc = inject(MessageService);

  const userID = _route.params['userID'];

  return userConfirmationSvc.confirmUserGet(userID)
    .then(() => {
      
      userConfirmationSvc.userID = userID;
      return true;
    })
    .catch((err) => {

      const errorMessage = err?.error?.notFound || "Unknown error occurred."

      messageSvc.add({
        severity: 'error',
        summary: 'Error',
        detail: errorMessage,
        life: 5000,
      });

      return router.parseUrl('/error');
    });
}