import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppComponent} from "./app.component";
import {AlertModule, TabsModule} from "ngx-bootstrap";
import {PlayerTable} from "./components/players-table/players-table.component";
import {DataTableModule, SharedModule} from "primeng/primeng";
import {PassingService} from "./services/passing.service";
import {RushingService} from "./services/rushing.service";
import {ReceivingService} from "./services/receiving.service";
import {DefenseService} from "./services/defense.service";
import {HttpModule} from "@angular/http";
import {KickingService} from "./services/kicking.service";

@NgModule({
  declarations: [
    AppComponent,
    PlayerTable
  ],
  imports: [
    BrowserModule,
    AlertModule.forRoot(),
    TabsModule.forRoot(),
    DataTableModule,
    SharedModule,
    HttpModule
  ],
  providers: [PassingService, RushingService, ReceivingService, DefenseService, KickingService],
bootstrap: [AppComponent]
})
export class AppModule {
}
