import {inject, TestBed} from "@angular/core/testing";

import {PassingService} from "./passing.service";

describe('PassingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PassingService]
    });
  });

  it('should be created', inject([PassingService], (service: PassingService) => {
    expect(service).toBeTruthy();
  }));
});
