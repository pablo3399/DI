import { Component, OnInit } from '@angular/core';
import { Player } from '../player';
import { PlayerService } from '../player.service';
import { Location } from '@angular/common';
import { FormsModule } from '@angular/forms'


@Component({
  selector: 'app-player-add',
  templateUrl: './player-add.component.html',
  styleUrls: ['./player-add.component.css']
})
export class PlayerAddComponent implements OnInit {

  players: Player[];

  delantero=null;
  defensa=null;
  portero=null;
  medio=null;
  resultado=null;

  operacionSeleccionada: string = 'Delantero';
  tipoOperaciones = [
    'Portero',
    'Defensa',
    'Medio',
    'Delantero',
  ];


  operar() {
    switch (this.operacionSeleccionada) {
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

  constructor(private playerService: PlayerService,
    private location: Location) { }

  ngOnInit() {
    this.getPlayers();
  }

  getPlayers(): void {
    this.playerService.getPlayers().subscribe(players => this.players = players);
  }

  add(name: string, dorsal: number): void {
    name = name.trim();

    let position : string = this.resultado;

    var matches = name.match(/\d+/g);

    if (matches != null || dorsal > 100 || dorsal < 0) {
      return;
    }
    if (isNaN(dorsal)) {
      return;
    }
    this.playerService.addPlayer({ name, dorsal, position } as Player)
      .subscribe(player => {
        this.players.push(player);
      });
    this.location.back();
  }

  goBack(): void {
    this.location.back();
  }
}

