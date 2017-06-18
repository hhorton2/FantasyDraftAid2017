import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerTable } from './players-table.component';

describe('PlayerTable', () => {
  let component: PlayerTable;
  let fixture: ComponentFixture<PlayerTable>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlayerTable ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlayerTable);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
