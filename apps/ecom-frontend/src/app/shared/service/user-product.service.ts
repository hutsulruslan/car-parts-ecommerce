import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {createPaginationOption, Page, Pagination} from "../model/request.model";
import {Brand, Product, ProductCategory, ProductFilter, ProductSubCategory} from "../../admin/model/product.model";
import {map, Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserProductService {

  http = inject(HttpClient);

  findAllFeaturedProducts(pageRequest: Pagination): Observable<Page<Product>> {
    const params = createPaginationOption(pageRequest);
    return this.http.get<Page<Product>>(`${environment.apiUrl}/products-shop/featured`, {params});
  }

  findOneByPublicId(publicId: string): Observable<Product> {
    return this.http.get<Product>(`${environment.apiUrl}/products-shop/find-one`, {params:{publicId}});
  }

  findRelatedProduct(pageRequest: Pagination, productPublicId: string): Observable<Page<Product>> {
    let params = createPaginationOption(pageRequest);
    params = params.append('publicId', productPublicId);
    return this.http.get<Page<Product>>(`${environment.apiUrl}/products-shop/related`, {params});
  }

  findAllCategories(): Observable<Page<ProductCategory>> {
    return this.http.get<Page<ProductCategory>>(
      `${environment.apiUrl}/categories`
    );
  }

  findAllSubCategories(): Observable<Page<ProductSubCategory>> {
    return this.http.get<Page<ProductSubCategory>>(
      `${environment.apiUrl}/subcategories`
    );
  }

  findAllBrands(): Observable<Brand[]> {
    return this.http.get<Page<Brand>>(`${environment.apiUrl}/brands`)
      .pipe(
        map(page => page.content) // витягуємо brands із content
      );
  }

  filter(pageRequest: Pagination, productFilter: ProductFilter): Observable<Page<Product>> {
    let params = createPaginationOption(pageRequest);

    if (productFilter.subCategory) {
      params = params.append('subCategoryId', productFilter.subCategory);
    }
    if (productFilter.brand?.trim()) {
      const brands = productFilter.brand.split(',').filter(b => b.trim() !== '');
      brands.forEach(b => params = params.append('brandId', b));
    }

    return this.http.get<Page<Product>>(`${environment.apiUrl}/products-shop/filter`, { params });
  }

}
