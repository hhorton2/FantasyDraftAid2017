import {inject, TestBed} from "@angular/core/testing";

import {KickingService} from "./kicking.service";

describe('KickingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [KickingService]
    });
  });

  it('should be created', inject([KickingService], (service: KickingService) => {
    expect(service).toBeTruthy();
  }));
});
