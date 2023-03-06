import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import {Observable, of, tap} from 'rxjs';
import {BackendService} from "./backend.service";
import {Configuration} from "./models/configuration";

@Injectable({
  providedIn: 'root'
})
export class ConfigResolver implements Resolve<Configuration> {

  constructor(private backendService: BackendService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Configuration> {
    return this.backendService.loadConfiguration()
  }
}
