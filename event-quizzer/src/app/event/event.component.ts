import { Component, OnInit, Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { EndpointComponent } from '../utils/endpoint.component';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class EventComponent implements OnInit {
  id: any;
  jsonData: any;
  environmentLabels: any;
  mapParams: { [key: string]: string } = {};
  apiSubmitResponse: any;

  constructor(
    private route: ActivatedRoute,
    private endpoint: EndpointComponent
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    console.log("[EventComponent] id --> ", this.id);

    this.LoadResources();
  }

  private LoadResources() {
    if (this.id != null) {
      this.endpoint.retrieveEvent(this.id).subscribe(r => this.jsonData = r);
    }

    this.endpoint.getLanguage("es").subscribe(r => this.environmentLabels = r);
  }

  scroll(id: any) {
    console.log(`scrolling to ${id}`);
    let el = document.getElementById(id);
    if(el != null) {
      el.scrollIntoView({behavior: 'smooth'});
    }
  }

  getScreenMessage(key: string) {
    
  }

  registration() {
    var userLang = navigator.language; 
    console.log("Event ID ", this.id);
    console.log("language ", userLang);
    const currDate = new Date();
    console.log("currDate ", formatDate(currDate, 'yyyyMMdd', navigator.language));
    this.mapParams["a_date"] = formatDate(currDate, 'yyyyMMdd', navigator.language);
    this.mapParams["id"] = this.id;
    console.log(this.mapParams);

    this.endpoint.submitAttendance(this.mapParams).subscribe(r => this.apiSubmitResponse = r);
  }
}
