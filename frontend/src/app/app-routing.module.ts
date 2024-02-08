import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { HomeComponent } from './pages/home/home.component';
import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { adminAuthGuard } from './services/admin-auth.guard';
import { userAuthGuard } from './services/user-auth.guard';
import { ProfileComponent } from './pages/admin/profile/profile.component';
import { MoviesComponent } from './pages/admin/movies/movies.component';
import { AddMovieComponent } from './pages/admin/add-movie/add-movie.component';
import { TheatresComponent } from './pages/admin/theatres/theatres.component';
import { AddTheatreComponent } from './pages/admin/add-theatre/add-theatre.component';
import { AddShowComponent } from './pages/admin/add-show/add-show.component';
import { UserHomeComponent } from './pages/user/user-home/user-home.component';
import { AdminHomeComponent } from './pages/admin/admin-home/admin-home.component';
import { UserMoviesComponent } from './pages/user/user-movies/user-movies.component';
import { BookTicketsComponent } from './pages/user/book-tickets/book-tickets.component';
import { BookSeatsComponent } from './pages/user/book-seats/book-seats.component';

const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'signup', component: SignupComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [adminAuthGuard],
    children: [
      {
        path: '',
        component: AdminHomeComponent,
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'movies',
        component: MoviesComponent,
      },
      {
        path: 'add-movie',
        component: AddMovieComponent,
      },
      {
        path: 'theatres',
        component: TheatresComponent,
      },
      {
        path: 'add-theatre',
        component: AddTheatreComponent,
      },
      {
        path: 'add-show',
        component: AddShowComponent,
      },
    ],
  },
  {
    path: 'user',
    component: UserDashboardComponent,
    canActivate: [userAuthGuard],
    children: [
      {
        path: '',
        component: UserHomeComponent,
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'movies',
        component: UserMoviesComponent,
      },
    ],
  },
  {
    path: 'book',
    component: BookTicketsComponent,
  },
  {
    path: 'seats',
    component: BookSeatsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
