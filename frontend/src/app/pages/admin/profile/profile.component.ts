import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {
  activeUser: any = null;
  userRole?: string = '';
  backLink: string = '/';
  isEditing = false;
  updatedUser: any = {};

  constructor(
    private loginService: LoginService,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {
    this.activeUser = loginService.getActiveUser();
    this.userRole = loginService.getActiveUserRole();
    this.backLink = this.userRole === 'admin-role-csid' ? '/admin' : '/user';
    if (this.activeUser) {
      this.updatedUser = { ...this.activeUser };
    }
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
    if (this.isEditing && this.activeUser) {
      this.updatedUser = { ...this.activeUser };
    }
  }

  saveProfile() {
    if (!this.updatedUser.firstName || !this.updatedUser.lastName || !this.updatedUser.email) {
      this.snackBar.open('Please fill all required fields', 'Close', { duration: 3000 });
      return;
    }

    const userId = this.activeUser.id || this.activeUser.csid;
    this.userService.updateUser(userId, this.updatedUser).subscribe({
      next: (user) => {
        this.activeUser = user;
        this.loginService.setActiveUser(user);
        this.isEditing = false;
        this.snackBar.open('Profile updated successfully!', 'Close', { duration: 3000 });
      },
      error: (err) => {
        console.error(err);
        this.snackBar.open('Failed to update profile. Please try again.', 'Close', { duration: 3000 });
      }
    });
  }

  shareProfile() {
    const shareData = {
      title: 'CineShow Profile',
      text: `Check out ${this.activeUser.firstName}'s profile on CineShow!`,
      url: window.location.href
    };

    if (navigator.share) {
      navigator.share(shareData)
        .then(() => this.snackBar.open('Shared successfully!', 'Close', { duration: 2000 }))
        .catch((err) => console.log('Error sharing', err));
    } else {
      // Fallback: Copy to clipboard
      const shareText = `${shareData.text} ${shareData.url}`;
      navigator.clipboard.writeText(shareText).then(() => {
        this.snackBar.open('Profile link copied to clipboard!', 'Close', { duration: 3000 });
      });
    }
  }
}
