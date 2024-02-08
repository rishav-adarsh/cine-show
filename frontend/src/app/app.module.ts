import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './pages/login/login.component';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { SignupComponent } from './pages/signup/signup.component';
import { HomeComponent } from './pages/home/home.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { AuthInterceptorProviders } from './services/auth.interceptor';
import { SidebarComponent } from './pages/admin/sidebar/sidebar.component';
import { ProfileComponent } from './pages/admin/profile/profile.component';
import { AddMovieComponent } from './pages/admin/add-movie/add-movie.component';
import { MoviesComponent } from './pages/admin/movies/movies.component';
import { TheatresComponent } from './pages/admin/theatres/theatres.component';
import { AddTheatreComponent } from './pages/admin/add-theatre/add-theatre.component';
import { AddShowComponent } from './pages/admin/add-show/add-show.component';
import { UserSidebarComponent } from './pages/user/user-sidebar/user-sidebar.component';
import { UserHomeComponent } from './pages/user/user-home/user-home.component';
import { AdminHomeComponent } from './pages/admin/admin-home/admin-home.component';
import { UserMoviesComponent } from './pages/user/user-movies/user-movies.component';
import { MovieCardComponent } from './components/movie-card/movie-card.component';
import { ImgFallbackDirective } from './directives/img-fallback.directive';
import { BookTicketsComponent } from './pages/user/book-tickets/book-tickets.component';
import { BookSeatsComponent } from './pages/user/book-seats/book-seats.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    AdminDashboardComponent,
    UserDashboardComponent,
    SidebarComponent,
    AdminHomeComponent,
    ProfileComponent,
    MoviesComponent,
    AddMovieComponent,
    TheatresComponent,
    AddTheatreComponent,
    AddShowComponent,
    UserSidebarComponent,
    UserHomeComponent,
    AdminHomeComponent,
    UserMoviesComponent,
    MovieCardComponent,
    ImgFallbackDirective,
    BookTicketsComponent,
    BookSeatsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatCardModule,
    MatListModule,
    MatSlideToggleModule,
    MatSelectModule,
  ],
  providers: [AuthInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
