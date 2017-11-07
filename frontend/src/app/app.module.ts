import { BrowserModule } from "@angular/platform-browser";
import { RouterModule} from "@angular/router";
import { NgModule } from "@angular/core";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { HttpClient, HttpClientModule } from "@angular/common/http";

import { routes } from "./app.routes";

import { AppComponent } from "./app.component";
import { AuthComponent } from "./auth/auth.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import {AuthCallbackComponent} from "./auth/auth-callback.component";
import {AuthGuard} from "./auth/auth.guard";
import {AuthService} from "./auth/auth.service";
import {TokenInterceptor} from "./auth/auth.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    AuthCallbackComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    AuthGuard,
    HttpClient,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
