import { ActivatedRoute } from "@angular/router";
import { NewsService } from "./../../../services/news.service";
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";

@Component({
  selector: "app-news-detail",
  templateUrl: "./news-detail.component.html",
  styleUrls: ["./news-detail.component.scss"],
})
export class NewsDetailComponent implements OnInit {
  public newsForm = new FormGroup({
    content: new FormControl(null, Validators.required),
    title: new FormControl(null, Validators.required),
    description: new FormControl(null, Validators.required),
  });
  private _newsId = "";

  constructor(private newsServ: NewsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this._newsId = this.route.snapshot.paramMap.get("id");
    if (!this._newsId) {
      return;
    }
    this.newsServ.getNewsById(this._newsId).subscribe((res: any) => {
      if (!res) {
        return;
      }
      this.newsForm.patchValue({
        ...res,
      });
    });
  }

  public onSave(): void {
    if (!this.newsForm.valid) {
      return;
    }
    const req = {
      id: this._newsId,
      categoryId: 1,
      ...this.newsForm.value,
    };
    this.newsServ.updateNews(req).subscribe((res: any) => {
      window.alert("update successfully!");
    });
  }
}
