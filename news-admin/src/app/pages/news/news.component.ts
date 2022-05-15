import { Router } from "@angular/router";
import { NewsService } from "./../../services/news.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-news",
  templateUrl: "./news.component.html",
  styleUrls: ["./news.component.scss"],
})
export class NewsComponent implements OnInit {
  public news = [];
  constructor(private newsServ: NewsService, private router: Router) {}

  ngOnInit(): void {
    this._getNews();
  }

  private _getNews(): void {
    this.newsServ.getNews().subscribe((res: any) => {
      this.news = res;
    });
  }

  public gotoDetail(newsId: string): void {
    this.router.navigate([`news/${newsId}`]);
  }

  public onDelete(newsId: string): void {
    confirm("Are you sure you want to delete this news?");
    this.newsServ.deleteNews(newsId).subscribe((res: any) => {
      this._getNews();
      window.alert("delete news successfully!");
    });
  }
}
