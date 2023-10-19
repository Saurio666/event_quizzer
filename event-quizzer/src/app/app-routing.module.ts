import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { QuizzerComponent } from './quizzer/quizzer.component';

const routes: Routes = [
  {path: 'quizz', component: QuizzerComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
