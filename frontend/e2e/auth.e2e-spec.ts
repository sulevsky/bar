import { AuthPage } from "./auth.po";

describe("Auth page", () => {
  let page: AuthPage;

  beforeEach(() => {
    page = new AuthPage();
  });

  it("should show button with correct text", () => {
    page.navigateTo();
    expect(page.getButton()).toEqual("AUTHORIZE");
  });
});
