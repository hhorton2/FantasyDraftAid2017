import { FantasyFootballDraftAidFrontEndPage } from './app.po';

describe('fantasy-football-draft-aid-front-end App', () => {
  let page: FantasyFootballDraftAidFrontEndPage;

  beforeEach(() => {
    page = new FantasyFootballDraftAidFrontEndPage();
  });

  it('should display welcome message', done => {
    page.navigateTo();
    page.getParagraphText()
      .then(msg => expect(msg).toEqual('Welcome to app!!'))
      .then(done, done.fail);
  });
});
