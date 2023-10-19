import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { constants } from './constants';

@Injectable({
    providedIn: 'root'
})
export class EndpointComponent {

    constructor(
        private httpClient: HttpClient
    ) { }


    public retrieveEvent(id: string): Observable<any> {
        return this.httpClient.get<any>(`${constants.quizzApi}/event/${id}`).pipe(
            catchError(this.handleError('retrieveReport', { message: `Could not retrieve event with id ${id}` }))
        );
    }

    public retrieveQuizz(id: string): Observable<any> {
        return this.httpClient.get<any>(`${constants.quizzApi}/quizz/${id}`).pipe(
            catchError(this.handleError('retrieveReport', { message: `Could not retrieve quizz with id ${id}` }))
        );
    }

    public retrieveQuizzByUser(id: string, userId: string): Observable<any> {
        return this.httpClient.get<any>(`${constants.quizzApi}/quizz/${id}/${userId}`).pipe(
            catchError(this.handleError('retrieveReport', { message: `Could not retrieve quizz with id ${id}` }))
        );
    }

    public getLanguage(lan: string): Observable<any>{
        return this.httpClient.get(`/assets/language/${lan}.json`);
    }

    public submitAnswers(answersMapCheck: any, id: any): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        return this.httpClient.post(`${constants.quizzApi}/quizz/submit`, answersMapCheck, { headers }).pipe(
            catchError(this.handleError('submitQuizz', { message: `Could not submit report with id ${id}` }))
        );
    }

    public submitAttendance(mapParams: any): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
        return this.httpClient.post(`${constants.quizzApi}/event/register`, mapParams, { headers }).pipe(
            catchError(this.handleError('submitAttendance', { message: `Could not submit attendance for ${mapParams}` }))
        );
    }

    private handleError(operation = 'operation', result: any) {
        return (error: any): Observable<any> => {
            if (error instanceof HttpErrorResponse) {
                if (error.error && error.error.message) {
                    return of({ message: error.error.message });
                }
            }

            console.error(operation);
            console.error(error);

            return of(result);
        };
    }
}