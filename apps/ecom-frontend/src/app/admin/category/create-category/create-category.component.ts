import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {AdminProductService} from "../../admin-product.service";
import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {ToastService} from "../../../shared/toast/toast.service";
import {CreateCategoryFormContent, ProductCategory} from "../../model/product.model";
import {injectMutation} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {NgxControlError} from "ngxtension/control-error";
import {Router} from "@angular/router";

@Component({
  selector: 'ecom-create-category',
  imports: [CommonModule, ReactiveFormsModule, NgxControlError],
  templateUrl: './create-category.component.html',
  styleUrl: './create-category.component.scss',
})
export class CreateCategoryComponent {

  formBuilder = inject(FormBuilder);
  productService = inject(AdminProductService);
  toastService = inject(ToastService);
  router = inject(Router);

  name = new FormControl<string>('', {nonNullable: true, validators: [Validators.required]});

  public createForm = this.formBuilder.nonNullable.group<CreateCategoryFormContent>({
    name: this.name
  });

  loading = false;

  createMutation = injectMutation(() =>({
    mutationFn:
      (categoryToCreate: ProductCategory) => lastValueFrom(this.productService.createCategory(categoryToCreate)),
    onSettled: () => this.onCreationSettled(),
    onSuccess: () => this.onCreationSuccess(),
    onError: () => this.onCreationError()
  }));

  create(): void {
    const categoryToCreate: ProductCategory = {
      name: this.createForm.getRawValue().name
    };

    this.loading = true;
    this.createMutation.mutate(categoryToCreate);
  }

  private onCreationSettled(): void {
    this.loading = false;
  }

  private onCreationSuccess(): void {
    this.toastService.show('Категорію створено', 'SUCCESS');
    this.router.navigate(['/admin/categories/list']);
  }

  private onCreationError(): void {
    this.toastService.show('Проблеми під час створення категорії', 'ERROR');
  }

}
