import { Component } from '@angular/core';
import {Configuration} from "../../models/configuration";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-configutation',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.scss']
})
export class ConfigurationComponent {
  configuration?: Configuration;

  constructor(private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.subscribe((data) => {
      console.log(data['configuration'].scheduledConfigs)
      this.configuration = data['configuration'];

      console.log(this.configuration?.scheduledConfigs[0].scheduleTime)
    });
  }
}
