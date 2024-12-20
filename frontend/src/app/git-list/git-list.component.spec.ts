import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GitListComponent } from './git-list.component';

describe('GitListComponent', () => {
  let component: GitListComponent;
  let fixture: ComponentFixture<GitListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GitListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GitListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
