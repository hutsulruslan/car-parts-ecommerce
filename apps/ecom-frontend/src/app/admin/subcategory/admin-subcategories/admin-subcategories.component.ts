import {Component, effect, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {AdminProductService} from "../../admin-product.service";
import {ToastService} from "../../../shared/toast/toast.service";
import {injectMutation, injectQuery, QueryClient} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {RouterLink} from "@angular/router";
import {Page} from "../../../shared/model/request.model";
import {ProductCategory} from "../../model/product.model";

@Component({
  selector: 'ecom-admin-subcategories',
  imports: [CommonModule, FaIconComponent, RouterLink],
  templateUrl: './admin-subcategories.component.html',
  styleUrl: './admin-subcategories.component.scss',
})
export class AdminSubcategoriesComponent {
  productAdminService = inject(AdminProductService);

  toastService = inject(ToastService);
  queryClient = inject(QueryClient);

  categoryMap = new Map<string, string>();

  constructor() {
    effect(() => this.handleSubCategoriesQueryError());
  }

  subCategoriesQuery = injectQuery(() => ({
    queryKey: ['subcategories'],
    queryFn: () => lastValueFrom(this.productAdminService.findAllSubCategories())
  }));

  deleteMutation = injectMutation(() => ({
    mutationFn: (subCategoryPublicId: string) =>
      lastValueFrom(this.productAdminService.deleteSubCategory(subCategoryPublicId)),
    onSuccess: () => this.onDeletionSuccess(),
    onError: () => this.onDeletionError()
  }));

  private onDeletionSuccess(): void {
    this.queryClient.invalidateQueries({queryKey: ['subcategories']});
    this.toastService.show('Підкатегорія видалена успішно', 'SUCCESS');
  }

  private onDeletionError(): void {
    this.toastService.show('Проблема під час видалення підкатегорії', 'ERROR');
  }

  private handleSubCategoriesQueryError() {
    if(this.subCategoriesQuery.isError()) {
      this.toastService.show('Помилка! Не вдалося завантажити підкатегорії товарів. Будь ласка, спробуйте ще раз.', 'ERROR');
    }
  }

  deleteSubCategory(publicId: string) {
    this.deleteMutation.mutate(publicId);
  }
}
