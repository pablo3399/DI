import { Component, OnInit } from '@angular/core';
import { FormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Comentarios } from '../comentarios';
import { ComentariosService } from '../comentariosService.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  user:FormGroup;

  constructor(private comentarios:ComentariosService, private fb: FormBuilder) { }

  ngOnInit() {

    this.user=this.fb.group({
      nombre:['',[Validators.minLength(2),Validators.maxLength(16), Validators.required]],
      apellido:['',[Validators.minLength(1),Validators.maxLength(16), Validators.required]],
      apellido2:['', [Validators.maxLength(16)]],
      correo:['',[Validators.minLength(11),Validators.maxLength(30), Validators.required]],
      telefono:['',[Validators.minLength(8),Validators.maxLength(9), Validators.required]],
      comentario:['',[Validators.maxLength(200), Validators.required]],
    });
  }

  add(nombre:string, apellido:string, apellido2:string, correo:string, telefono:string, comentario:string): void {

    var matches1 = nombre.match(/\d+/g);
    var matches2 = apellido.match(/\d+/g);
    var matches3 = apellido2.match(/\d+/g);

    if(apellido2!=null){
      if(matches3!=null){
        return;
      }
    }
    if (matches1 != null || matches2!= null) {
      return;
    }
    if (isNaN(parseInt(telefono))) {
      return;
    }

    if(matches3!=null){
    this.comentarios.add({ nombre, apellido, apellido2, correo, telefono, comentario } as Comentarios);
  }else{
    this.comentarios.add2({ nombre, apellido, correo, telefono, comentario } as Comentarios);
  }
    this.user.reset("");  
  }



}
