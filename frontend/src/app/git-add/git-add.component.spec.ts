import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GitAddComponent } from './git-add.component';

describe('GitAddComponent', () => {
  let component: GitAddComponent;
  let fixture: ComponentFixture<GitAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GitAddComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GitAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
