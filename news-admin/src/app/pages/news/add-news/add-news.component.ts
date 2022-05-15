import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { NewsService } from "src/app/services/news.service";

@Component({
  selector: "app-add-news",
  templateUrl: "./add-news.component.html",
  styleUrls: ["./add-news.component.scss"],
})
export class AddNewsComponent implements OnInit {
  public newsForm = new FormGroup({
    content: new FormControl(null, Validators.required),
    title: new FormControl(null, Validators.required),
    description: new FormControl(null, Validators.required),
  });

  constructor(private newsServ: NewsService) {}

  ngOnInit(): void {}

  public onCreate(): void {
    if (!this.newsForm.valid) {
      return;
    }
    const req = {
      ...this.newsForm.value,
      categoryId: 1,
    };
    this.newsServ.createNews(req).subscribe((res: any) => {
      this.newsForm.reset();
      window.alert("create news successfully!");
    });
  }
}
