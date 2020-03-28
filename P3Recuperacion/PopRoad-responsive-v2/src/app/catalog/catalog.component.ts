import { Component, OnInit } from '@angular/core';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';


@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {

    public cols=4;
    public rowHeight=280;
    public isMobile:boolean=false;
    
  constructor(private breakpointObserver:BreakpointObserver) {

    breakpointObserver.observe([Breakpoints.Handset]).subscribe(result=>{
      this.isMobile=result.matches;
      if(this.isMobile){
        this.cols=1;
        this.rowHeight=280;
      }else{
        this.cols=4
        this.rowHeight=380;
      }
    })
   }

   ngOnInit() {  
  }

}
