import { Component } from "@angular/core";
import { AuthService } from "./auth.service";

@Component({
  selector: "auth",
  templateUrl: "./auth.component.html",
  styleUrls: ["./auth.component.scss"],
  providers: [AuthService]
})
export class AuthComponent {

  constructor(private authService: AuthService) {}

  public auth() {
    this.authService.login();
  }

}
