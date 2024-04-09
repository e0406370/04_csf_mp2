import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { Profile } from '../models/profile';

import { ERROR_MESSAGE } from '../utility/constants';
import { SessionStore } from '../utility/session.store';
import { UtilityService } from '../utility/utility.service';
import { UserProfileService } from './user-profile.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
  
export class UserProfileComponent {

  private router = inject(Router);
  private sessionStore = inject(SessionStore);

  private utilitySvc = inject(UtilityService);
  private userProfileSvc = inject(UserProfileService);

  userProfile!: Profile;

  ngOnInit(): void {

    this.userProfileSvc.retrieveUserProfile()
      .then(res => {

        this.userProfile = res?.response;
        console.info(this.userProfile);
      })
      .catch(err => {
        
        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
        this.router.navigate(['/error']);
      })
    
    //TODO: sessionStore to verify whether same userID as loggedin or not to render different profiles
  }
}
