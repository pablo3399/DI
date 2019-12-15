import { Component, OnInit, Input } from '@angular/core';
import { Player } from '../player';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PlayerService } from '../player.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-player-detail',
  templateUrl: './player-detail.component.html',
  styleUrls: ['./player-detail.component.css']
})
export class PlayerDetailComponent implements OnInit {
  @Input() player: Player;
  user:FormGroup;
  
  ngOnInit() {
    this.getPlayer();  
    this.user=this.fb.group({
      name1:['',[Validators.minLength(2),Validators.maxLength(16), Validators.required]],
      name2:['',[Validators.minLength(1),Validators.maxLength(2), Validators.required]],
      radios:[[Validators.required]],
    });
  }

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
    private location: Location,
    private fb:FormBuilder,
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
