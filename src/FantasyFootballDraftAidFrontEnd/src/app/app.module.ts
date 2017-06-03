import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {AppComponent} from "./app.component";
import {AlertModule, TabsModule} from "ngx-bootstrap";
import {PlayerTable} from "./components/players-table/players-table.component";
import {DataTableModule, SharedModule} from "primeng/primeng";

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
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
