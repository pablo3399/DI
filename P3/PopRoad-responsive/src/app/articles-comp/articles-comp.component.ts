import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Articles } from '../articles';
import { ArticlesService } from '../articles.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-articles-comp',
  templateUrl: './articles-comp.component.html',
  styleUrls: ['./articles-comp.component.css']
})
export class ArticlesCompComponent implements OnInit {

  @Input() articles: Articles;


  ngOnInit() {
    this.getArticle();
  }

  constructor(
    private route: ActivatedRoute,
    private articlesService: ArticlesService,
    private location: Location,
  ) { }

  getArticle(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.articlesService.getArticle(id).subscribe(articles => this.articles = articles);
  }

    goBack(): void {
    this.location.back;
  }

}
