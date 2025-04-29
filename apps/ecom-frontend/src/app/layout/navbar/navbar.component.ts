import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {Oauth2Service} from "../../auth/oauth2.service";
import {ClickOutside} from "ngxtension/click-outside";
import {UserProductService} from "../../shared/service/user-product.service";
import {injectQuery} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {ProductCategory} from "../../admin/model/product.model";

@Component({
  selector: 'ecom-navbar',
  imports: [CommonModule, RouterLink, FaIconComponent, ClickOutside],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {

  oauth2service = inject(Oauth2Service);
  productsService = inject(UserProductService);

  hoveredCategory: ProductCategory | null = null;
  isMenuHovered = false;

  connectedUserQuery = this.oauth2service.connectedUserQuery;

  categoriesQuery = injectQuery(() => ({
    queryKey: ['categories'],
    queryFn: () => lastValueFrom(this.productsService.findAllCategories())
  }));

  subcategoriesQuery = injectQuery(() => ({
    queryKey: ['subcategories'],
    queryFn: () => lastValueFrom(this.productsService.findAllSubCategories())
  }));

  get subcategoriesForHoveredCategory() {
    if (!this.hoveredCategory || !this.subcategoriesQuery.data()?.content) {
      return [];
    }
    return this.subcategoriesQuery.data()?.content.filter(sub => sub.category.publicId === this.hoveredCategory!.publicId);
  }

  login(): void {
    this.closeDropDownMenu();
    this.oauth2service.login();
  }

  logout(): void {
    this.closeDropDownMenu();
    this.oauth2service.logout();
  }

  isConnected(): boolean {
    return this.connectedUserQuery?.status() === 'success'
      && this.connectedUserQuery?.data()?.email !== this.oauth2service.notConnected;
  }

  closeDropDownMenu() {
    const bodyElement = document.activeElement as HTMLBodyElement;
    if(bodyElement) {
      bodyElement.blur();
    }
  }

  closeMenu(menu: HTMLDetailsElement) {
    menu.removeAttribute('open');
  }
}
