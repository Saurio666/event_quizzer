import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Answer } from '../models/answer'

import { EndpointComponent } from '../utils/endpoint.component';

import { constants } from '../utils/constants';

@Component({
  selector: 'app-quizzer',
  templateUrl: './quizzer.component.html',
  styleUrls: ['./quizzer.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class QuizzerComponent implements OnInit {
  @Input() parameters: Answer | undefined;
  @ViewChild('hcForm') form: any;

  id: any;
  userId: any;
  jsonData: any;
  model: any;
  apiSubmitResponse: any;
  environmentLabels: any;
  validationMessage: any;

  answersMap: { [key: string]: string } = {};
  answersErrorsMap: { [key: string]: string } = {};

  timer: Observable<number> | undefined;
  timerSubscription: Subscription | undefined;
  timeLeft: number | undefined;
  timeElapsedMinutes: any;
  timeElapsedSeconds: any;
  remainingTime: any;

  mapParams: { [key: string]: string } = {};


  constructor(
    private route: ActivatedRoute,
    private httpClient: HttpClient,
    private endpoint: EndpointComponent
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    console.log("id --> ", this.id);
    this.userId = this.route.snapshot.paramMap.get('userId');
    console.log("userId --> ", this.userId);

    this.LoadResources();

    //this.model = this.jsonData.data.model.
    console.log("this.jsonData ", this.jsonData);
    console.log("this.jsonData.data.att_id ", this.jsonData?.data.att_id);
  }

  private LoadResources() {
    if (this.userId != null) {
      if (this.id === 'evt') {
        if (this.mapParams['u_id']) {
          this.endpoint.retrieveQuizzByEventAndUser(this.userId, this.mapParams['u_id']).subscribe(r => {
            this.jsonData = r;
          });
        } else {
          this.endpoint.retrieveQuizzByEvent(this.userId).subscribe(r => {
            this.jsonData = r;
          });
        }
      } else {

        this.endpoint.retrieveQuizzByUser(this.id, this.userId).subscribe(r => {
          this.jsonData = r;
          console.log("r.data.att_elp", r.data.att_elp);
          console.log("r.data.model.time", r.data.model.quizzTime);
          console.log("r.data.att_id", r.data.att_id);
          if (r.data.model.quizzTime > 0) {
            this.startTimer(r.data.att_elp, r.data.model.quizzTime);
          }
        });
      }
    } else {
      this.endpoint.retrieveQuizz(this.id).subscribe(r => this.jsonData = r);
    }

    this.endpoint.getLanguage("es").subscribe(r => this.environmentLabels = r);
  }

  private startTimer(elapsedTime: number, maxMinutes: number) {
    console.log("startTimer elapsedTime --> ", elapsedTime)
    const timerInterval = 1000; // Intervalo de 1 segundo
    const totalTime = 60; // Tiempo total en segundos
    this.timeElapsedMinutes = elapsedTime;
    this.remainingTime = maxMinutes - elapsedTime;


    this.timer = new Observable<number>((observer) => {
      this.timeElapsedSeconds = 0;

      const timerId = setInterval(() => {
        observer.next(this.timeElapsedSeconds);
        if (this.timeElapsedSeconds === 59) {
          this.timeElapsedMinutes++;
          this.remainingTime--;
          this.timeElapsedSeconds = 0;
        } else {
          this.timeElapsedSeconds++;
        }

        if (this.timeElapsedMinutes > maxMinutes) {
          clearInterval(timerId);
          observer.complete();
        }
      }, timerInterval);

      // Cuando el componente se destruye, detenemos el temporizador
      return () => {
        clearInterval(timerId);
      };
    });

    this.timerSubscription = this.timer.subscribe({
      next: (time) => {
        this.timeLeft = time;
      },
      complete: () => {
        console.log('Tiempo agotado');
        this.getResponse('N');
      }
    });
  }

  private validateEmptyAnswers(answersMapForResponse: any) {
    console.log("answersMapForResponse --> ", answersMapForResponse);
    this.answersErrorsMap = {};
    this.validationMessage = '';
    console.log("Test errors", this.answersErrorsMap);
    if (this.jsonData.questions) {
      this.jsonData.questions.forEach((q: { id: string; correctAnswers: number; }) => {
        if (!answersMapForResponse[q.id]) {
          this.answersErrorsMap['q-' + q.id] = 'quizz_frm_msg_empty_answer';
        } else {
          const array = answersMapForResponse[q.id].split(',');
          console.log("array", array, array.length);
          if (array.length < q.correctAnswers) {
            this.answersErrorsMap['q-' + q.id] = 'quizz_frm_msg_answer_counter_min';
          }
          else {
            if (array.length > q.correctAnswers) {
              this.answersErrorsMap['q-' + q.id] = 'quizz_frm_msg_answer_counter_max';
            }
          }
        }
      });
    }

    console.log("Test errors", this.answersErrorsMap);
  }

  getResponse(validateEmpty: string) {
    //return;

    const answersMapCheck: { [key: string]: any } = {};

    if (this.userId != null) {
      answersMapCheck["userId"] = this.userId;
    }
    answersMapCheck["id"] = this.id;
    answersMapCheck["att_id"] = this.jsonData.data.att_id;

    const answersMapForResponse: { [key: string]: string } = {};

    console.log("answersMap", this.answersMap);

    const keys = Object.keys(this.answersMap);
    keys.forEach((key) => {

      if (this.answersMap[key]) {

        if (key.includes("a")) {
          const data = key.split(";");
          //console.log('pregunta:', data[0].substring(2));
          //console.log('respuesta:', data[1].substring(2));

          if (answersMapForResponse[data[0].substring(2)] != null) {
            const newValue = answersMapForResponse[data[0].substring(2)] + "," + data[1].substring(2);

            answersMapForResponse[data[0].substring(2)] = newValue;
          } else {
            answersMapForResponse[data[0].substring(2)] = data[1].substring(2);
          }

        } else {
          //console.log('pregunta:', key.substring(2));
          //console.log('respuesta:', this.answersMap[key]);
          answersMapForResponse[key.substring(2)] = this.answersMap[key] + "";
        }
      }
    });

    if (validateEmpty === 'Y') {
      console.log("Requiere validacion");
      this.validateEmptyAnswers(answersMapForResponse);
      if (this.answersErrorsMap && Object.keys(this.answersErrorsMap).length > 0) {
        this.validationMessage = this.environmentLabels['quizz_frm_msg_validate_answers'];
        return;
      }
      answersMapCheck["smt_act"] = "0";
    } else {
      console.log("No requiere validacion");
      answersMapCheck["smt_act"] = "1";
    }
    console.log("Continua con el registro");

    answersMapCheck["answers"] = answersMapForResponse;

    console.log(this.answersMap);
    console.log(answersMapCheck);

    this.endpoint.submitAnswers(answersMapCheck, this.id).subscribe(r => this.apiSubmitResponse = r);

    console.log("*2222* ");
    console.log("*3333* ", this.apiSubmitResponse);
    /*if(this.apiSubmitResponse.status === 'OK') {

    }*/


    this.answersMap = {};

    this.scroll('wrapper');


  }

  getActiveQuiz() {
    if (this.mapParams['u_id']) {
      this.endpoint.retrieveQuizzByEventAndUser(this.userId, this.mapParams['u_id']).subscribe(r => {
        this.jsonData = r;
      });
      this.mapParams['show_valid_quiz'] = 'Y';
      //console.log(this.mapParams);
    }
  }

  scroll(id: any) {
    console.log(`scrolling to ${id}`);
    let el = document.getElementById(id);
    if (el != null) {
      el.scrollIntoView({ behavior: 'smooth' });
    }
  }
}
