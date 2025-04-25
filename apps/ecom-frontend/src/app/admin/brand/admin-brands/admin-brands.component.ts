import {Component, effect, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {AdminProductService} from "../../admin-product.service";
import {ToastService} from "../../../shared/toast/toast.service";
import {injectMutation, injectQuery, QueryClient} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'ecom-admin-brands',
  imports: [CommonModule, FaIconComponent, RouterLink],
  templateUrl: './admin-brands.component.html',
  styleUrl: './admin-brands.component.scss',
})
export class AdminBrandsComponent {
  productAdminService = inject(AdminProductService);

  toastService = inject(ToastService);
  queryClient = inject(QueryClient);


  constructor() {
    effect(() => this.handleBrandsQueryError());
  }

  brandsQuery = injectQuery(() => ({
    queryKey: ['brands'],
    queryFn: () => lastValueFrom(this.productAdminService.findAllBrands())
  }));

  deleteMutation = injectMutation(() => ({
    mutationFn: (brandPublicId: string) =>
      lastValueFrom(this.productAdminService.deleteBrand(brandPublicId)),
    onSuccess: () => this.onDeletionSuccess(),
    onError: () => this.onDeletionError()
  }));

  private onDeletionSuccess(): void {
    this.queryClient.invalidateQueries({queryKey: ['brands']});
    this.toastService.show('Бренд видалена успішно', 'SUCCESS');
  }

  private onDeletionError(): void {
    this.toastService.show('Проблема під час видалення бренду', 'ERROR');
  }

  private handleBrandsQueryError() {
    if(this.brandsQuery.isError()) {
      this.toastService.show('Помилка! Не вдалося завантажити бренди товарів. Будь ласка, спробуйте ще раз.', 'ERROR');
    }
  }

  deleteBrand(publicId: string) {
    this.deleteMutation.mutate(publicId);
  }
}
