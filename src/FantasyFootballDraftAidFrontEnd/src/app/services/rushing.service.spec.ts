import {inject, TestBed} from "@angular/core/testing";

import {RushingService} from "./rushing.service";

describe('RushingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RushingService]
    });
  });

  it('should be created', inject([RushingService], (service: RushingService) => {
    expect(service).toBeTruthy();
  }));
});
