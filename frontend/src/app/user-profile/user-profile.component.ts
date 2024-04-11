import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

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

  private activatedRoute = inject(ActivatedRoute);
  private router = inject(Router);
  private sessionStore = inject(SessionStore);

  private utilitySvc = inject(UtilityService);
  private userProfileSvc = inject(UserProfileService);

  userID!: string;
  userProfile!: Profile;
  isSameUser!: boolean;

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params => {
      this.userID = params['userID'];

      this.userProfileSvc.retrieveUserProfile(this.userID)
      .then(res => {

        this.userProfile = res?.response;
        // console.info(this.userProfile);
      })
      .catch(err => {
        
        this.utilitySvc.generateErrorMessage(err?.error?.message || ERROR_MESSAGE);
        this.router.navigate(['/error']);
      })

      if (this.sessionStore.getLoggedID() == this.userID) {
        this.isSameUser = true;
      }
      else {
        this.isSameUser = false;
      }
    })
  }
}
