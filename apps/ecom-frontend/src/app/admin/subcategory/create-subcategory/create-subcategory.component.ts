import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {AdminProductService} from "../../admin-product.service";
import {ToastService} from "../../../shared/toast/toast.service";
import {Router} from "@angular/router";
import {
  CreateSubCategoryFormContent,
  ProductSubCategory
} from "../../model/product.model";
import {injectMutation, injectQuery} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {NgxControlError} from "ngxtension/control-error";

@Component({
  selector: 'ecom-create-subcategory',
  imports: [CommonModule, ReactiveFormsModule, NgxControlError],
  templateUrl: './create-subcategory.component.html',
  styleUrl: './create-subcategory.component.scss',
})
export class CreateSubcategoryComponent {
  formBuilder = inject(FormBuilder);
  productService = inject(AdminProductService);
  toastService = inject(ToastService);
  router = inject(Router);

  name = new FormControl<string>('', {nonNullable: true, validators: [Validators.required]});
  category = new FormControl<string>('', { nonNullable: true, validators: [Validators.required] });

  public createForm = this.formBuilder.nonNullable.group<CreateSubCategoryFormContent>({
    name: this.name,
    category: this.category
  });

  loading = false;

  categoriesQuery = injectQuery(() => ({
    queryKey: ['categories'],
    queryFn: () => lastValueFrom(this.productService.findAllCategories())
  }));

  createMutation = injectMutation(() => ({
    mutationFn:
      (subCategoryToCreate: ProductSubCategory) =>
        lastValueFrom(this.productService.createSubCategory(subCategoryToCreate)),
    onSettled: () => this.onCreationSettled(),
    onSuccess: () => this.onCreationSuccess(),
    onError: () => this.onCreationError()
  }));

  create(): void {
    const subCategoryToCreate: ProductSubCategory = {
      name: this.createForm.getRawValue().name,
      category: {
        publicId: this.createForm.getRawValue().category.split('+')[0],
        name: this.createForm.getRawValue().category.split('+')[1],
      },
    };

    this.loading = true;
    this.createMutation.mutate(subCategoryToCreate);
  }

  private onCreationSettled(): void {
    this.loading = false;
  }

  private onCreationSuccess(): void {
    this.toastService.show('Підкатегорію було додано', 'SUCCESS');
    this.router.navigate(['/admin/subcategories/list']);
  }

  private onCreationError(): void {
    this.toastService.show('Проблеми під час додання підкатегорії', 'ERROR');
  }
}
