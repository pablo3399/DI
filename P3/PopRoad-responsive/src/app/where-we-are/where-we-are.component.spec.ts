import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WhereWeAreComponent } from './where-we-are.component';

describe('WhereWeAreComponent', () => {
  let component: WhereWeAreComponent;
  let fixture: ComponentFixture<WhereWeAreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WhereWeAreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WhereWeAreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
