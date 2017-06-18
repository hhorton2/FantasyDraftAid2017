import {inject, TestBed} from "@angular/core/testing";

import {ReceivingService} from "./receiving.service";

describe('ReceivingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReceivingService]
    });
  });

  it('should be created', inject([ReceivingService], (service: ReceivingService) => {
    expect(service).toBeTruthy();
  }));
});
