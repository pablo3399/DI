import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';


@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService{

  createDb(){
    const articles = [
      {images: 'assets/img/penny.png', title:'this is pennywise', subtitle:'THATs IT.', id:1},
      {images: 'assets/img/fallout4.png', title:'this is from fallout 4', subtitle:'long life to the broterhood.', id:2},
      {images: 'assets/img/bbbyoda.png', title:'this is from Star Wars', subtitle:'Patience you must have, my young padawan.', id:3},
      {images: 'assets/img/meeseeks.png', title:'this is from Rick & Morty', subtitle:'Im Mr. Meeseeks, look at me', id:4},
      {images: 'assets/img/superman.png', title:'this is from DC comics', subtitle:'Im the man of steel', id:5},
      {images: 'assets/img/fallout4.png', title:'Interdum et malesuada amet', subtitle:'Pellentesque at dignissim ante.', id:6},
      {images: 'assets/img/penny.png', title:'Interdum et malesuada amet', subtitle:'Pellentesque at dignissim ante.', id:7},
      {images: 'assets/img/fallout4.png', title:'Interdum et malesuada amet', subtitle:'Pellentesque at dignissim ante.', id:8},
      {images: 'assets/img/penny.png', title:'Interdum et malesuada amet', subtitle:'Pellentesque at dignissim ante.', id:9},

    ];
    return {articles};
  }
}
