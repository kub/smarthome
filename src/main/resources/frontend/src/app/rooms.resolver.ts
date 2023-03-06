import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import {Observable, of, tap} from 'rxjs';
import {BackendService} from "./backend.service";
import {Configuration} from "./models/configuration";
import {Room} from "./models/room";

@Injectable({
  providedIn: 'root'
})
export class RoomsResolver implements Resolve<Room[]> {

  constructor(private backendService: BackendService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Room[]> {
    return this.backendService.loadRooms()
  }
}
