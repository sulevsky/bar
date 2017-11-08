import { Component } from "@angular/core";
import { AuthService } from "./auth.service";

@Component({
  selector: "auth-callback",
  templateUrl: "./auth-callback.component.html",
  styleUrls: ["./auth-callback.component.scss"],
  providers: [AuthService]
})
export class AuthCallbackComponent {

  constructor(private authService: AuthService) {}

}
