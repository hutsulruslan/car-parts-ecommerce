import {Component, effect, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ToastService} from "../../../shared/toast/toast.service";
import {injectMutation, injectQuery, injectQueryClient, QueryClient} from "@tanstack/angular-query-experimental";
import {Pagination} from "../../../shared/model/request.model";
import {lastValueFrom} from "rxjs";
import {AdminProductService} from "../../admin-product.service";
import {RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'ecom-admin-products',
  imports: [CommonModule, RouterLink, FaIconComponent],
  templateUrl: './admin-products.component.html',
  styleUrl: './admin-products.component.scss',
})
export class AdminProductsComponent {

  productService = inject(AdminProductService);
  toastService = inject(ToastService);
  queryClient = inject(QueryClient);

  pageRequest: Pagination = {
    page: 0,
    size: 20,
    sort: ['createdDate,desc']
  }


  constructor() {
    effect(() => this.handleProductsQueryError());
  }

  productsQuery = injectQuery(() => ({
    queryKey: ['products', this.pageRequest],
    queryFn: () => lastValueFrom(this.productService.findAllProduct(this.pageRequest))
  }));

  deleteMutation = injectMutation(() => ({
    mutationFn:
      (productPublicId: string) => lastValueFrom(this.productService.deleteProduct(productPublicId)),
    onSuccess: () => this.onDeletionSuccess(),
    onError: () => this.onDeletionError()
  }));

  deleteProduct(publicId: string) {
    this.deleteMutation.mutate(publicId)
  }

  private onDeletionSuccess() {
    this.queryClient.invalidateQueries({queryKey: ['products']});
    this.toastService.show('Товар видалено', 'SUCCESS');
  }

  private onDeletionError() {
    this.toastService.show('Помилка при видалені товару', 'ERROR');
  }

  private handleProductsQueryError() {
    if(this.productsQuery.isError()) {
      this.toastService.show('Помилка при завантажені продуктів, будь ласка спробуйте ще раз', 'ERROR');
    }
  }

}
