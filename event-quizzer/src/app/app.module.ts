import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { RouterModule, ExtraOptions } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { QuizzerComponent } from './quizzer/quizzer.component';
import { EventComponent } from './event/event.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    QuizzerComponent,
    EventComponent,
    FooterComponent    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    RouterModule.forRoot([
      { path: 'quizzer/:id', component: QuizzerComponent },
      { path: 'quizzer/:id/:userId', component: QuizzerComponent },
      { path: 'quizzer/tkn/:token', component: QuizzerComponent },
      { path: 'event/:id', component: EventComponent }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
