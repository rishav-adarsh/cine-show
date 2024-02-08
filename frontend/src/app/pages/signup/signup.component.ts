import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  hide: boolean = true;

  constructor(
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {}

  signupForm = new FormGroup(
    {
      username: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
      firstName: new FormControl(null, [Validators.required]),
      lastName: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required]),
      phone: new FormControl(null, [Validators.required]),
    },
    { updateOn: 'submit' }
  );

  onSubmit() {
    console.log(this.signupForm.value);

    let username = this.signupForm.value.username;
    if (!username) {
      this.snackBar.open('Username is Required', 'OK', {
        duration: 3 * 1000,
      });
      return;
    }

    this.userService.createUser(this.signupForm.value).subscribe(
      (data: any) => {
        // alert('Successfully Signed Up!!');
        Swal.fire(
          'SignUp Successful !!', // Heading
          `A new user, ${username} is created !!`, // Sub Heading
          'success' // Message Type
        );
      },
      (err: any) => {
        // alert('SignUp Failed!!');
        this.snackBar.open('SignUp Failed', 'OK', {
          duration: 3 * 1000,
        });
      }
    );
  }
}
