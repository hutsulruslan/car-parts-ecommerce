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
  parentCategory: ProductCategory;
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
