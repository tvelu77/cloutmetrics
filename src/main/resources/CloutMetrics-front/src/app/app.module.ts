import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { GitListComponent } from './git-list/git-list.component';
import { GitFormComponent } from './git-form/git-form.component';
import { GitService } from './service/git-service.service';
import { GitDetailComponent } from './git-detail/git-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    GitListComponent,
    GitFormComponent,
    GitDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [GitService],
  bootstrap: [AppComponent]
})
export class AppModule { }
