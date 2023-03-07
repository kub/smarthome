import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import { ConfigurationComponent } from './components/configutation/configuration.component';
import {ConfigResolver} from "./config.resolver";
import {HttpClientModule} from "@angular/common/http";
import {RoomsComponent} from "./components/rooms/rooms.component";
import {RoomsResolver} from "./rooms.resolver";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {FormsModule} from "@angular/forms";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatCardModule} from "@angular/material/card";
import {MatGridListModule} from "@angular/material/grid-list";


const routes: Routes = [
  {
    path: 'config',
    component: ConfigurationComponent,
    runGuardsAndResolvers: 'always',
    resolve: {
      configuration: ConfigResolver
    }
  },
  {
    path: '',
    component: RoomsComponent,
    runGuardsAndResolvers: 'always',
    resolve: {
      rooms: RoomsResolver
    }
  }
];

@NgModule({
  declarations: [
    AppComponent,
    ConfigurationComponent,
    RoomsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'}),
    MatSlideToggleModule,
    FormsModule,
    MatExpansionModule,
    MatCardModule,
    MatGridListModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
