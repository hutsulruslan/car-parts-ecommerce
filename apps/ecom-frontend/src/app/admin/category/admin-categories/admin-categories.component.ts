import {Component, effect, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink} from "@angular/router";
import {AdminProductService} from "../../admin-product.service";
import {ToastService} from "../../../shared/toast/toast.service";
import {injectMutation, injectQuery, QueryClient} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'ecom-admin-categories',
  imports: [CommonModule, RouterLink, FaIconComponent],
  templateUrl: './admin-categories.component.html',
  styleUrl: './admin-categories.component.scss',
})
export class AdminCategoriesComponent {

  productAdminService = inject(AdminProductService);

  toastService = inject(ToastService);
  queryClient = inject(QueryClient);


  constructor() {
    effect(() => this.handleCategoriesQueryError());
  }

  categoriesQuery = injectQuery(() => ({
    queryKey: ['categories'],
    queryFn: () => lastValueFrom(this.productAdminService.findAllCategories())
  }));

  deleteMutation = injectMutation(() => ({
    mutationFn: (categoryPublicId: string) =>
      lastValueFrom(this.productAdminService.deleteCategory(categoryPublicId)),
    onSuccess: () => this.onDeletionSuccess(),
    onError: () => this.onDeletionError()
  }));

  private onDeletionSuccess(): void {
    this.queryClient.invalidateQueries({queryKey: ['categories']});
    this.toastService.show('Категорія видалена успішно', 'SUCCESS');
  }

  private onDeletionError(): void {
    this.toastService.show('Проблема під час видалення категорії', 'ERROR');
  }

  private handleCategoriesQueryError() {
    if(this.categoriesQuery.isError()) {
      this.toastService.show('Помилка! Не вдалося завантажити категорії товарів. Будь ласка, спробуйте ще раз.', 'ERROR');
    }
  }

  deleteCategory(publicId: string) {
    this.deleteMutation.mutate(publicId);
  }
}
