import { NewsDetailComponent } from './../../pages/news/news-detail/news-detail.component';
import { AddNewsComponent } from "./../../pages/news/add-news/add-news.component";
import { NewsComponent } from "./../../pages/news/news.component";
import { Routes } from "@angular/router";

import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { IconsComponent } from "../../pages/icons/icons.component";
import { MapComponent } from "../../pages/map/map.component";
import { NotificationsComponent } from "../../pages/notifications/notifications.component";
import { UserComponent } from "../../pages/user/user.component";
import { UserNewComponent } from "../../pages/user/user-new/user-new.component";
import { UserDetailComponent } from "../../pages/user/user-detail/user-detail.component";
import { TablesComponent } from "../../pages/tables/tables.component";
import { TypographyComponent } from "../../pages/typography/typography.component";

export const AdminLayoutRoutes: Routes = [
  { path: "dashboard", component: DashboardComponent },
  { path: "icons", component: IconsComponent },
  { path: "maps", component: MapComponent },
  { path: "notifications", component: NotificationsComponent },
  { path: "user", component: UserComponent },
  { path: "user/new", component: UserNewComponent },
  { path: "user/:id", component: UserDetailComponent },
  { path: "news", component: NewsComponent },
  { path: "news/new", component: AddNewsComponent },
  { path: "news/:id", component: NewsDetailComponent },
  { path: "tables", component: TablesComponent },
  { path: "typography", component: TypographyComponent },
];
