import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Articles } from '../articles';
import { ArticlesService } from '../articles.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

import { Comentario } from '../comentarioNoticia';
import { ComentarioServiceService } from '../comentario-service.service';


@Component({
  selector: 'app-articles-comp',
  templateUrl: './articles-comp.component.html',
  styleUrls: ['./articles-comp.component.css']
})
export class ArticlesCompComponent implements OnInit {

  @Input() articles: Articles;
  user:FormGroup;
  cantidad:number=0;
  comentarios: Comentario[] = [];
  id:number;

  ngOnInit() {
    this.getArticle();
    this.getComentarios();
    this.user=this.fb.group({
      nombre:['',[Validators.minLength(2),Validators.maxLength(16), Validators.required]],
      comentario:['',[Validators.maxLength(200), Validators.required]],
    });

  }

  constructor(
    private route: ActivatedRoute,
    private articlesService: ArticlesService,
    private fb: FormBuilder,
    private comentServ: ComentarioServiceService,
  ) { }

  getArticle(): void {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.articlesService.getArticle(this.id).subscribe(articles => this.articles = articles);
  }

  getComentarios(): void {

    this.comentServ.getComentarios(this.id).subscribe(comentarios => this.comentarios = comentarios.slice(0, 4));
    this.cantidad = this.comentarios.length;
  }

  AnhadirNuevo(nombre: string, comentario: string) {

    var id = this.id;

    this.comentServ.add({ id, nombre, comentario } as Comentario);

    this.getComentarios();

    this.user.reset();
  }

}
