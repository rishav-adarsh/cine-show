import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookTicketsComponent } from './book-tickets.component';

describe('BookTicketsComponent', () => {
  let component: BookTicketsComponent;
  let fixture: ComponentFixture<BookTicketsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookTicketsComponent]
    });
    fixture = TestBed.createComponent(BookTicketsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
