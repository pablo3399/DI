import { Injectable } from '@angular/core';
import { Comentarios } from './comentarios';

@Injectable({
  providedIn: 'root'
})
export class ComentariosService {

  comentarios: string[] = [];

  add(comentario: Comentarios) {

    var toString= "Nombre: " + comentario.nombre + " " + "Apellido: " + comentario.apellido + " " + comentario.apellido2 + " "+"Correo: "
    + comentario.correo + " " + "Telefono: " + comentario.telefono + " " + "Comentario: " + comentario.comentario;

    this.comentarios.push(toString);
    console.log(this.comentarios);
   }

   add2(comentario: Comentarios) {

    var toString= "Nombre: " + comentario.nombre + " " + "Apellido: " + comentario.apellido + " "+"Correo: "
    + comentario.correo + " " + "Telefono: " + comentario.telefono + " " + "Comentario: " + comentario.comentario;

    this.comentarios.push(toString);
    console.log(this.comentarios);
   }

   clear(){
     this.comentarios=[];
   }
}
