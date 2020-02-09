import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlesCompComponent } from './articles-comp.component';

describe('ArticlesCompComponent', () => {
  let component: ArticlesCompComponent;
  let fixture: ComponentFixture<ArticlesCompComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticlesCompComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticlesCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
