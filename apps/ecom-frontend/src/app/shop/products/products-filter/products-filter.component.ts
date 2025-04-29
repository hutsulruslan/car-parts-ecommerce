import { Component, effect, inject, input, output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Brand, FilterProductsFormContent, ProductFilter, ProductFilterForm } from "../../../admin/model/product.model";
import { FormBuilder, FormControl, FormRecord, ReactiveFormsModule, Validators } from "@angular/forms";

@Component({
  selector: 'ecom-products-filter',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './products-filter.component.html',
  styleUrl: './products-filter.component.scss',
})
export class ProductsFilterComponent {

  sort = input<string>("createdDate,asc");
  brands = input<Brand[]>([]);

  productFilter = output<ProductFilter>();
  formBuilder = inject(FormBuilder);

  constructor() {
    effect(() => this.updateBrandsFormValue());
    effect(() => this.updateSortFormValue());

    this.formFilterProducts.valueChanges.subscribe(() =>
      this.onFilterChange(this.formFilterProducts.getRawValue())
    );
  }

  formFilterProducts = this.formBuilder.nonNullable.group<FilterProductsFormContent>({
    sort: new FormControl<string>(this.sort().split(',')[1], {
      nonNullable: true,
      validators: [Validators.required],
    }),
    brand: this.buildBrandsFormControl(),
  });

  private buildBrandsFormControl(): FormRecord<FormControl<boolean>> {
    const brandFormControl = this.formBuilder.nonNullable.record<FormControl<boolean>>({});
    for (const brand of this.brands()) {
      if (brand.publicId) {
        brandFormControl.addControl(
          brand.publicId,
          new FormControl<boolean>(false, { nonNullable: true })
        );
      }
    }
    return brandFormControl;
  }

  private onFilterChange(filter: Partial<ProductFilterForm>) {
    const filterProduct: ProductFilter = {
      sort: [`createdDate,${filter.sort}`],
    };

    if (filter.brand) {
      const selectedBrands = Object.entries(filter.brand)
        .filter(([, selected]) => selected)
        .map(([brandId]) => brandId);

      if (selectedBrands.length > 0) {
        filterProduct.brand = selectedBrands.join(',');
      }
    }

    this.productFilter.emit(filterProduct);
  }

  public getBrandsFormGroup(): FormRecord<FormControl<boolean>> {
    return this.formFilterProducts.get('brand') as FormRecord<FormControl<boolean>>;
  }

  private updateBrandsFormValue() {
    if (this.brands()) {
      const brandForm = this.getBrandsFormGroup();
      brandForm.reset({});
      this.brands().forEach(brand => {
        if (brand.publicId && !brandForm.contains(brand.publicId)) {
          brandForm.addControl(
            brand.publicId,
            new FormControl<boolean>(false, { nonNullable: true })
          , { emitEvent: false });
        }
      });
    }
  }

  private updateSortFormValue() {
    if (this.sort()) {
      this.formFilterProducts.controls.sort.setValue(
        this.sort().split(',')[1],
        { emitEvent: false });
    }
  }
}
