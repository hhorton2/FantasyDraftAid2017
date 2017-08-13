import {inject, TestBed} from "@angular/core/testing";

import {DefenseService} from "./defense.service";

describe('DefenseService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DefenseService]
    });
  });

  it('should be created', inject([DefenseService], (service: DefenseService) => {
    expect(service).toBeTruthy();
  }));
});
