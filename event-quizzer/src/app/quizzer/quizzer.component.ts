import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
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
  apiSubmitResponse: any;
  environmentLabels: any;
  validationMessage: any;

  answersMap: { [key: string]: string } = {};
  answersErrorsMap: { [key: string]: string } = {};

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
  }

  private LoadResources() {
    if (this.userId != null) {
      this.endpoint.retrieveQuizzByUser(this.id, this.userId).subscribe(r => this.jsonData = r);
    } else {
      this.endpoint.retrieveQuizz(this.id).subscribe(r => this.jsonData = r);
    }

    this.endpoint.getLanguage("es").subscribe(r => this.environmentLabels = r);
  }

  private validateEmptyAnswers() {
    this.answersErrorsMap = {};
    this.validationMessage = '';
    console.log("Test errors", this.answersErrorsMap);
    if(this.jsonData.questions) {
      this.jsonData.questions.forEach((q: { id: string; }) => {
        if(!this.answersMap['q-' + q.id]) {
          this.answersErrorsMap['q-' + q.id] = 'quizz_msg_empty_answer';
        }
      });
    }
    
    console.log("Test errors", this.answersErrorsMap);
  }

  getResponse() {
    this.validateEmptyAnswers();
    if(this.answersErrorsMap && Object.keys(this.answersErrorsMap).length > 0) {
      this.validationMessage = this.environmentLabels['quizz_msg_validate_answers'];
      return;
    }
    console.log("Continua con el registro");
    //return;

    const answersMapCheck: { [key: string]: any } = {};

    if (this.userId != null) {
      answersMapCheck["userId"] = this.userId;
    }
    answersMapCheck["id"] = this.id;
    const answersMapForResponse: { [key: string]: string } = {};

    const keys = Object.keys(this.answersMap);
    keys.forEach((key) => {

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
    });

    answersMapCheck["answers"] = answersMapForResponse;

    console.log(this.answersMap);
    console.log(answersMapCheck);

    this.endpoint.submitAnswers(answersMapCheck, this.id).subscribe(r => this.apiSubmitResponse = r);

    console.log("*2222* ");
    console.log("*3333* ", this.apiSubmitResponse);
    /*if(this.apiSubmitResponse.status === 'OK') {

    }*/


    this.answersMap = {};
  }
}
