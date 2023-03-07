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
      return this.httpClient.get<Configuration>('http://192.168.0.109:8080/api/config');
  }

  loadRooms() {
    return this.httpClient.get<Room[]>('http://192.168.0.109:8080/api/rooms');
  }

  updateControl(control: Control) {
    return this.httpClient.put('http://192.168.0.109:8080/api/control', {
      id: control.id,
      type: control.type,
      isOn: control.isOn,
      lightLevel: control.lightLevel
    }).subscribe();
  }
}
