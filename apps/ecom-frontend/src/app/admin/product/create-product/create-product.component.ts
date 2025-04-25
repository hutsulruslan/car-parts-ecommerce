import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {AdminProductService} from "../../admin-product.service";
import {ToastService} from "../../../shared/toast/toast.service";
import {Router} from "@angular/router";
import {
  BaseProduct,
  Brand,
  CreateProductFormContent,
  ProductPicture,
  ProductSubCategory
} from "../../model/product.model";
import {injectMutation, injectQuery} from "@tanstack/angular-query-experimental";
import {lastValueFrom} from "rxjs";
import {NgxControlError} from "ngxtension/control-error";

@Component({
  selector: 'ecom-create-product',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError],
  templateUrl: './create-product.component.html',
  styleUrl: './create-product.component.scss',
})
export class CreateProductComponent {

  public formBuilder = inject(FormBuilder);
  productService = inject(AdminProductService);
  toastService = inject(ToastService);
  router = inject(Router);

  public productPictures = new Array<ProductPicture>();

  name = new FormControl<string>('', {nonNullable: true, validators:[Validators.required]});
  description = new FormControl<string>('', {nonNullable: true, validators:[Validators.required]});
  price = new FormControl<number>(0, {nonNullable: true, validators:[Validators.required]});
  subCategory = new FormControl<string>('', {nonNullable: true, validators:[Validators.required]});
  brand = new FormControl<string>('', {nonNullable: true, validators:[Validators.required]});
  featured = new FormControl<boolean>(false, {nonNullable: true, validators:[Validators.required]});
  pictures = new FormControl<Array<ProductPicture>>([], {nonNullable: true, validators:[Validators.required]});
  stock = new FormControl<number>(0, {nonNullable: true, validators:[Validators.required]});

  public createForm = this.formBuilder.group<CreateProductFormContent>({
    brand: this.brand,
    description: this.description,
    name: this.name,
    price: this.price,
    subCategory: this.subCategory,
    featured: this.featured,
    pictures: this.pictures,
    stock: this.stock
  });

  loading = false;

  subCategoriesQuery = injectQuery(() => ({
    queryKey: ['subcategories'],
    queryFn: () => lastValueFrom(this.productService.findAllSubCategories())
  }));

  brandsQuery = injectQuery(() => ({
    queryKey: ['brands'],
    queryFn: () => lastValueFrom(this.productService.findAllBrands())
  }));

  createMutation = injectMutation(() => ({
    mutationFn:
      (product: BaseProduct) => lastValueFrom(this.productService.createProduct(product)),
    onSettled: () => this.onCreationSettled(),
    onSuccess: () => this.onCreationSuccess(),
    onError: () => this.onCreationError()
  }));

  create(): void {
    const productToCreate: BaseProduct = {
      brand: {
        publicId: this.createForm.getRawValue().brand.split('+')[0],
        name: this.createForm.getRawValue().brand.split('+')[1],
      },
      description: this.createForm.getRawValue().description,
      name: this.createForm.getRawValue().name,
      price: this.createForm.getRawValue().price,
      subCategory: {
        publicId: this.createForm.getRawValue().subCategory.split('+')[0],
        name: this.createForm.getRawValue().subCategory.split('+')[1],
        category: {
          publicId: this.createForm.getRawValue().subCategory.split('+')[2],
          name: this.createForm.getRawValue().subCategory.split('+')[3],
        }
      },
      featured: this.createForm.getRawValue().featured,
      pictures: this.productPictures,
      nbInStock: this.createForm.getRawValue().stock,
    };

    this.loading = true;
    this.createMutation.mutate(productToCreate);
  }

  private extractFileFromTarget(target: EventTarget | null): FileList | null {
    const htmlInputTarget = target as HTMLInputElement;
    if(target===null || htmlInputTarget.files === null) {
      return null;
    }
    return htmlInputTarget.files;
  }

  onUploadNewPicture(target: EventTarget | null) {
    this.productPictures = [];
    const pictureFileList = this.extractFileFromTarget(target);
    if(pictureFileList !== null) {
      for (let i=0; i<pictureFileList.length; i++) {
        const picture = pictureFileList.item(i);
        if(picture !== null) {
          const productPicture: ProductPicture = {
            file: picture,
            mimeType: picture.type
          };
          this.productPictures.push(productPicture);
        }
      }
    }
  }

  private onCreationSettled() {
    this.loading = false;
  }

  private onCreationSuccess() {
    this.router.navigate(['/admin/products/list']);
    this.toastService.show('Товар створено', 'SUCCESS');
  }

  private onCreationError() {
    this.toastService.show('Помилка при створені товару', 'ERROR');
  }

}
