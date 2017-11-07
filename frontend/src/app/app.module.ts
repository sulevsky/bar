import { BrowserModule } from "@angular/platform-browser";
import { RouterModule} from "@angular/router";
import { NgModule } from "@angular/core";

import { routes } from "./app.routes";

import { AppComponent } from "./app.component";
import { AuthComponent } from "./auth/auth.component";
import { DashboardComponent } from "./dashboard/dashboard.component";

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
