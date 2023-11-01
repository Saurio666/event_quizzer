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
  validationMessage: any;

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

    this.validationMessage = '';
  }

  scroll(id: any) {
    console.log(`scrolling to ${id}`);
    let el = document.getElementById(id);
    if (el != null) {
      el.scrollIntoView({ behavior: 'smooth' });
    }
  }

  getScreenMessage(key: string) {

  }

  registration() {

    if (this.mapParams['u_id']) {
      const currDate = new Date();
      console.log(currDate)
      var frDate = currDate.getFullYear() + '' + ("00" + currDate.getMonth()).slice(-2) + '' + ("00" + currDate.getDate()).slice(-2);
      console.log(frDate)
      this.mapParams["a_date"] = frDate
      this.mapParams["id"] = this.id;
      console.log(this.mapParams);

      this.endpoint.submitAttendance(this.mapParams).subscribe(r => this.apiSubmitResponse = r);
    } else {
      this.validationMessage = this.environmentLabels['event_frm_err_null_u_id'];
    }
  }
}
