import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
  
export class ThemeService {

  readonly bootstrapThemeAttribute = 'data-bs-theme';

  isDarkModeEnabled() {

    return localStorage.getItem('theme') == 'dark';
  }

  toggleDarkMode() {

    if (!this.isDarkModeEnabled()) {
      localStorage.setItem('theme', 'dark');
    }
    else {
      localStorage.setItem('theme', 'light');
    }

    this.modifyHtmlElement();
  }

  modifyHtmlElement() {

    const THEME = localStorage.getItem('theme');
    
    if (THEME) {
      document
        .querySelector('html')
        ?.setAttribute(this.bootstrapThemeAttribute, THEME);
    }
  }
}
