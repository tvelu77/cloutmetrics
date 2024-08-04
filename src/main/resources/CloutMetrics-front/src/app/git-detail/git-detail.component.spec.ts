import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GitDetailComponent } from './git-detail.component';

describe('GitDetailComponent', () => {
  let component: GitDetailComponent;
  let fixture: ComponentFixture<GitDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GitDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GitDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
