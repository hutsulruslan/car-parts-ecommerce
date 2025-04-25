import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {AdminProductService} from "../../admin-product.service";
import {ToastService} from "../../../shared/toast/toast.service";
import {Router} from "@angular/router";
import {Brand, CreateBrandFormContent, CreateCategoryFormContent, ProductCategory} from "../../model/product.model";
import {injectMutation} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {NgxControlError} from "ngxtension/control-error";

@Component({
  selector: 'ecom-create-brand',
  imports: [CommonModule, NgxControlError, ReactiveFormsModule],
  templateUrl: './create-brand.component.html',
  styleUrl: './create-brand.component.scss',
})
export class CreateBrandComponent {
  formBuilder = inject(FormBuilder);
  productService = inject(AdminProductService);
  toastService = inject(ToastService);
  router = inject(Router);

  name = new FormControl<string>('', {nonNullable: true, validators: [Validators.required]});

  public createForm = this.formBuilder.nonNullable.group<CreateBrandFormContent>({
    name: this.name
  });

  loading = false;

  createMutation = injectMutation(() =>({
    mutationFn:
      (brandToCreate: Brand) => lastValueFrom(this.productService.createBrand(brandToCreate)),
    onSettled: () => this.onCreationSettled(),
    onSuccess: () => this.onCreationSuccess(),
    onError: () => this.onCreationError()
  }));

  create(): void {
    const brandToCreate: Brand = {
      name: this.createForm.getRawValue().name
    };

    this.loading = true;
    this.createMutation.mutate(brandToCreate);
  }

  private onCreationSettled(): void {
    this.loading = false;
  }

  private onCreationSuccess(): void {
    this.toastService.show('Бренд додано', 'SUCCESS');
    this.router.navigate(['/admin/brands/list']);
  }

  private onCreationError(): void {
    this.toastService.show('Проблеми під час додання бренду', 'ERROR');
  }
}
