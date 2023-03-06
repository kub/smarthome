import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Configuration} from "./models/configuration";
import {Control, Room} from "./models/room";

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private httpClient: HttpClient) { }

  loadConfiguration(): Observable<Configuration> {
      return this.httpClient.get<Configuration>('/api/config');
  }

  loadRooms() {
    return this.httpClient.get<Room[]>('/api/rooms');
  }

  updateControl(control: Control) {
    return this.httpClient.put('/api/control', {
      id: control.id,
      type: control.type,
      isOn: control.isOn,
      lightLevel: control.lightLevel
    }).subscribe();
  }
}
