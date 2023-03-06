import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Control, Room} from "../../models/room";
import {BackendService} from "../../backend.service";


interface Dictionary {
  [key: string]: number;
}

const lightLevelUpdateStep = 20;
const roomPriorities:Dictionary = {
  'Bathroom': 1,
  'Kid room': 2,
  'Hallway' : 3,
  'Living room': 4,
  'Bedroom': 5,
  'Office' : 6,
  'Toilet' : 7,
}

@Component({
  selector: 'app-configutation',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent {
  rooms?: Room[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private backendService: BackendService) {
  }

  ngOnInit(): void {
    this.route.data.subscribe((data) => {
      this.rooms = data['rooms'].sort((r1: Room, r2: Room) => roomPriorities[r1.name] - roomPriorities[r2.name]);
    });
  }

  toggleControl(control: Control) {
    control.isOn = !control.isOn;
    this.backendService.updateControl(control)
  }

  getBackgroundColor(control: Control) {
    if (!control.isReachable) {
      return 'grey'
    }
    return control.isOn ? 'green' : 'red'
  }

  increaseLightIntensity(control: Control) {
    control.lightLevel = control.lightLevel + lightLevelUpdateStep > 100 ? 100 : control.lightLevel + lightLevelUpdateStep
    this.backendService.updateControl(control)

  }

  decreaseLightIntensity(control: Control) {
    control.lightLevel = control.lightLevel - lightLevelUpdateStep < 1 ? 1 : control.lightLevel - lightLevelUpdateStep
    this.backendService.updateControl(control)
  }
}
