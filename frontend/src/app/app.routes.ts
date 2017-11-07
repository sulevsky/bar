import { Routes } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { AuthComponent } from "./auth/auth.component";


export const routes: Routes = [
  { path: "", component: DashboardComponent },
  { path: "auth", component: AuthComponent },
  { path: "**", redirectTo: "" }
];

