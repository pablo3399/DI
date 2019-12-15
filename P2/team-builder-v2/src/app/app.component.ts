import { Component, AfterViewInit, ElementRef, Input, ViewChild } from '@angular/core';
import { PlayerAddComponent } from './player-add/player-add.component';
import { PlayerDetailComponent } from './player-detail/player-detail.component';
import { Routes } from '@angular/router';
import { LocationStrategy } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit{
  title = 'Team Builder';
  show:boolean;

  constructor(private elementRef: ElementRef, private url:LocationStrategy){
  }

  ngAfterViewInit(){
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = '#a7ead7';
  }

  ngDoCheck(){
    let details:string=this.url.path();
  if(this.url.path()==='/player-add'){
    this.show=false;
  }else if (details.includes("/detail/")){
    this.show=false;
  }else{
    this.show = true;
  }
  }

  ngOnInit(){
    let details:string=this.url.path();
  if(this.url.path()==='/player-add'){
    this.show=false;
  }else if (details.includes("/detail/")){
    this.show=false;
  }else{
    this.show = true;
  }
  }

     onChange():void{
     let details:string=this.url.path();
  if(this.url.path()==='/player-add'){
    this.show=false;
  }else if (details.includes("/detail/")){
    this.show=false;
  }else{
    this.show = true;
  }
}
}
