import { browser, by, element } from "protractor";

export class AuthPage {
  navigateTo() {
    return browser.get("/auth");
  }

  getButton() {
    return element(by.css("auth .auth-button")).getText();
  }
}
