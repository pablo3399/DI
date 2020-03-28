import { Injectable } from '@angular/core';
import { Articles } from './articles';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  private articlesUrl = 'api/articles';  // URL to web api
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {

  }
  
  getArticles(): Observable<Articles[]> {
      return this.http.get<Articles[]>(this.articlesUrl)
      .pipe(
        tap(_ =>  catchError(this.handleError<Articles[]>('getArticles', []))
      ));
}

private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // TODO: better job of transforming error for user consumption

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

getArticle(id: number): Observable<Articles> {
  const url = `${this.articlesUrl}/${id}`;
  return this.http.get<Articles>(url).pipe(
    catchError(this.handleError<Articles>(`getArticles id=${id}`))
  );
}


}
