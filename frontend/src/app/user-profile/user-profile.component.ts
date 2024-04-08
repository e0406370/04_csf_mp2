import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../utility/authentication.service';
import { Profile } from '../models/profile';
import { UtilityService } from '../utility/utility.service';
import { ERROR_MESSAGE } from '../utility/constants';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
  
export class UserProfileComponent {

  private router = inject(Router);

  private utilitySvc = inject(UtilityService);
  private authenticationSvc = inject(AuthenticationService);

  userID!: string;
  userProfile!: Profile;

  ngOnInit(): void {

    this.userID = this.authenticationSvc.userID;

    this.authenticationSvc.retrieveUserProfile(this.userID)
      .then(res => this.userProfile = res?.response)
      .then(() => console.info(this.userProfile))
      .catch(err => {
        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
        this.router.navigate(['/error']);
      })
  }
}
