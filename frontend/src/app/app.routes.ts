import { Routes } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { AuthComponent } from "./auth/auth.component";
import { AuthCallbackComponent } from "./auth/auth-callback.component";
import { AuthGuard } from "./auth/auth.guard";


export const routes: Routes = [
  { path: "", component: DashboardComponent, canActivate: [AuthGuard]  },
  { path: "auth", component: AuthComponent },
  { path: "callback", component: AuthCallbackComponent },
  { path: "**", redirectTo: "" }
];

