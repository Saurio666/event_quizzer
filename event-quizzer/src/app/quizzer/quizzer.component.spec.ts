import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzerComponent } from './quizzer.component';

describe('QuizzerComponent', () => {
  let component: QuizzerComponent;
  let fixture: ComponentFixture<QuizzerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [QuizzerComponent]
    });
    fixture = TestBed.createComponent(QuizzerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
