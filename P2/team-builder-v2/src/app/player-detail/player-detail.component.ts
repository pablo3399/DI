import { Component, OnInit, Input } from '@angular/core';
import { Player } from '../player';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PlayerService } from '../player.service';

@Component({
  selector: 'app-player-detail',
  templateUrl: './player-detail.component.html',
  styleUrls: ['./player-detail.component.css']
})
export class PlayerDetailComponent implements OnInit {
  @Input() player: Player;
  
  ngOnInit() {
    this.getPlayer();  
  }

  delantero=null;
  defensa=null;
  portero=null;
  medio=null;
  resultado=null;
  

  position : string ;

  Posiciones = [
    'Portero',
    'Defensa',
    'Medio',
    'Delantero',
  ];


  operar() {
    switch (this.position) {

      case 'Portero' : this.resultado = 'Portero';
                    break;
      case 'Defensa' : this.resultado = 'Defensa';
                     break;
      case 'Medio' : this.resultado = 'Medio';
                              break;
      case 'Delantero' : this.resultado = 'Delantero' ;
                        break;
    }
  }

  constructor(
    private route: ActivatedRoute,
    private playerService: PlayerService,
    private location: Location
  ) { }

  getPlayer(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.playerService.getPlayer(id).subscribe(player => this.player = player);
  }
  
  save(): void {
    this.playerService.updatePlayer(this.player)
      .subscribe(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
