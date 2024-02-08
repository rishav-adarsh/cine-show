import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookSeatsComponent } from './book-seats.component';

describe('BookSeatsComponent', () => {
  let component: BookSeatsComponent;
  let fixture: ComponentFixture<BookSeatsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookSeatsComponent]
    });
    fixture = TestBed.createComponent(BookSeatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
