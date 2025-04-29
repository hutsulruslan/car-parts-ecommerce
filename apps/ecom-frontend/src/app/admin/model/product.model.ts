import {FormControl, FormRecord} from "@angular/forms";

export interface Brand {
  publicId?: string;
  name: string;
}

export interface ProductCategory {
  publicId?: string;
  name: string;
}

export interface ProductSubCategory {
  publicId?: string;
  name: string;
  category: ProductCategory;
}

export interface ProductPicture {
  file: File;
  mimeType: string;
}

export interface BaseProduct {
  brand: Brand;
  subCategory: ProductSubCategory;
  description: string;
  name: string;
  price: number;
  featured: boolean;
  pictures: ProductPicture[];
  nbInStock: number;
}

export interface Product extends BaseProduct {
  publicId: string;
}

export type CreateCategoryFormContent = {
  name: FormControl<string>;
}

export type CreateBrandFormContent = {
  name: FormControl<string>;
}

export type CreateSubCategoryFormContent = {
  name: FormControl<string>;
  category: FormControl<string>;
}

export type CreateProductFormContent = {
  brand: FormControl<string>,
  subCategory: FormControl<string>,
  description: FormControl<string>,
  name: FormControl<string>,
  price: FormControl<number>,
  featured: FormControl<boolean>,
  pictures: FormControl<ProductPicture[]>,
  stock: FormControl<number>
}

export interface ProductFilter {
  brand?: string;
  subCategory?: string | null;
  sort: string[];
}

export type FilterProductsFormContent = {
  sort: FormControl<string>;
  brand: FormRecord<FormControl<boolean>>;
}
export interface ProductFilterForm {
  brand?: {
    [brandPublicId: string]: boolean;
  } | undefined;
  sort: string;
}
