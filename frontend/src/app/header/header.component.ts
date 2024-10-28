import { Component, inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { RippleModule } from 'primeng/ripple';
import { InputSwitchModule } from 'primeng/inputswitch';
import { FormsModule } from '@angular/forms';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [AvatarGroupModule, AvatarModule, ToolbarModule, ButtonModule, TagModule, RouterLink, RouterLinkActive, RippleModule, InputSwitchModule, FormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  readonly #document = inject(DOCUMENT);
  isDarkMode = false;

  constructor() {
    if (this.isSystemDark()) {
      this.toggleLightDark();
    }
  }

  isSystemDark(): boolean {
    return window?.matchMedia?.('(prefers-color-scheme:dark)')?.matches;
  }

  toggleLightDark() {
    const linkElement = this.#document.getElementById(
      'app-theme',
    ) as HTMLLinkElement;
    if (linkElement.href.includes('light')) {
      linkElement.href = 'dark-mode.css';
      this.isDarkMode = true;
    } else {
      linkElement.href = 'light-mode.css';
      this.isDarkMode = false;
    }
  }
}
