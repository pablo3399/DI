import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';

import { Player } from '../player';
import { PlayerService } from '../player.service';

@Component({
  selector: 'app-player-search',
  templateUrl: './player-search.component.html',
  styleUrls: ['./player-search.component.css']
})
export class PlayerSearchComponent implements OnInit {

  players$: Observable<Player[]>;
  rVal = "nombre";
  private searchTerms = new Subject<any>();

  constructor(private playerService: PlayerService) { }

  // Push a search term into the observable stream.
  search(term: any, rVal: string): void {

    if (rVal == "dorsal") {

      let dor: number = term;

      if (dor > 100 || dor < 0 || isNaN(dor)) {
        return;
      }

      this.searchTerms.next(parseInt(term));
    } else {
      this.searchTerms.next(term);
    }
  }

  ngOnInit(): void {

    this.players$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      switchMap((term: any) => this.playerService.searchPlayers(term)),
    );
  }



}