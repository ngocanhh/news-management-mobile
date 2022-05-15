import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { RouterModule } from "@angular/router";
import { ToastrModule } from "ngx-toastr";

import { AppComponent } from "./app.component";
import { AdminLayoutComponent } from "./layouts/admin-layout/admin-layout.component";
import { AuthLayoutComponent } from "./layouts/auth-layout/auth-layout.component";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { AppRoutingModule } from "./app-routing.module";
import { ComponentsModule } from "./components/components.module";
import { UserNewComponent } from "./pages/user/user-new/user-new.component";
import { UserDetailComponent } from './pages/user/user-detail/user-detail.component';
import { NewsComponent } from './pages/news/news.component';
import { NewsDetailComponent } from './pages/news/news-detail/news-detail.component';
import { AddNewsComponent } from './pages/news/add-news/add-news.component';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ComponentsModule,
    NgbModule,
    RouterModule,
    AppRoutingModule,
    ToastrModule.forRoot(),
  ],
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    AuthLayoutComponent,
    UserNewComponent,
    UserDetailComponent,
    NewsComponent,
    NewsDetailComponent,
    AddNewsComponent,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
