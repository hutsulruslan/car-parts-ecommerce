import {Component, computed, effect, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductsFilterComponent} from "./products-filter/products-filter.component";
import {injectQueryParams} from "ngxtension/inject-query-params";
import {UserProductService} from "../../shared/service/user-product.service";
import {Router} from "@angular/router";
import {ToastService} from "../../shared/toast/toast.service";
import {Brand, ProductFilter} from "../../admin/model/product.model";
import {Pagination} from "../../shared/model/request.model";
import {injectQuery} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {ProductCardComponent} from "../product-card/product-card.component";

@Component({
  selector: 'ecom-products',
  imports: [CommonModule, ProductsFilterComponent, ProductCardComponent],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss',
})
export class ProductsComponent {

  subCategory = injectQueryParams('subCategory');
  sort = injectQueryParams('sort');
  productService = inject(UserProductService);
  router = inject(Router);
  toastService = inject(ToastService);

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: ['createdDate,desc'],
  };

  brandsQuery = injectQuery(() => ({
    queryKey: ['brands'],
    queryFn: () => lastValueFrom(this.productService.findAllBrands()),
  }));

  brands = computed(() => this.brandsQuery.data() ?? []);

  filterProducts: ProductFilter = {
    subCategory: this.subCategory(),
    sort: [this.sort() ? this.sort()! : ''],
  };

  lastSubCategory = '';

  filteredProductsQuery = injectQuery(() => ({
    queryKey: ['products', this.filterProducts],
    queryFn: () => {
      if(this.subCategory()) {
        return lastValueFrom(
          this.productService.filter(this.pageRequest, this.filterProducts)
        )
      } else {
        return  lastValueFrom(this.productService.findAllFeaturedProducts(this.pageRequest));
      }
    }
  }));

  constructor() {
    effect(() => this.handleFilteredProductsQueryError());
    effect(() => this.handleParametersChange());
  }

  onFilterChange(filterProducts: ProductFilter) {
    filterProducts.subCategory = this.subCategory();
    this.filterProducts = filterProducts;
    this.pageRequest.sort = filterProducts.sort;
    this.router.navigate(['/products'], {
      queryParams: {
        ...filterProducts,
      },
    });
    this.filteredProductsQuery.refetch();
  }

  private handleFilteredProductsQueryError() {
    if(this.filteredProductsQuery.isError()) {
      this.toastService.show('Помилка! Не вдалось завантажити товари, спробуйте ще раз.', 'ERROR');
    }
  }

  private handleParametersChange() {
    if (this.subCategory()) {
      if (this.lastSubCategory != this.subCategory() && this.lastSubCategory !== '') {
        this.filterProducts = {
          subCategory: this.subCategory(),
          sort: [this.sort() ? this.sort()! : ''],
        };
        this.filteredProductsQuery.refetch();
      }
    }
    this.lastSubCategory = this.subCategory()!;
  }
}
