import { Component, OnInit } from '@angular/core';
import { Articles } from '../articles';
import { ArticlesService } from '../articles.service';


@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  articles: Articles[] = [];

  constructor(private articlesService : ArticlesService) { }

  ngOnInit() {
    this.getArticles();
  }

  getArticles():void{
    this.articlesService.getArticles().subscribe(articles => this.articles = articles.slice(0,3));
  }

  mostrarMas(idA:number):void{
    if(idA  == 1){
    this.articlesService.getArticles().subscribe(articles => this.articles = articles.slice(0,3));
    }
    else if(idA==2){
    this.articlesService.getArticles().subscribe(articles => this.articles = articles.slice(3,6));
    }else{
    this.articlesService.getArticles().subscribe(articles => this.articles = articles.slice(6,10));
    }
  }

}
