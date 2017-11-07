import { AppPage } from "./app.po";

describe("frontend App", () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it("should display dummy text message", () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual("Dashboard");
  });
});
