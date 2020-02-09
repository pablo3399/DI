import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component'
import { WhoWeAreComponent } from './who-we-are/who-we-are.component';
import { WhereWeAreComponent } from './where-we-are/where-we-are.component';
import { ArticlesComponent } from './articles/articles.component';
import { ContactComponent } from './contact/contact.component';
import { CatalogComponent } from './catalog/catalog.component';
import { ArticlesCompComponent } from './articles-comp/articles-comp.component';



const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'articlesComp/:id', component: ArticlesCompComponent },
  { path: 'home', component: HomeComponent},
  { path: 'who', component: WhoWeAreComponent },
  { path: 'where', component: WhereWeAreComponent },
  { path: 'articles', component:ArticlesComponent },
  { path: 'catalog', component: CatalogComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'article-comp', component: ArticlesCompComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
