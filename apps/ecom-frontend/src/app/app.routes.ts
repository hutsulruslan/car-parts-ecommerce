import { Route } from '@angular/router';
import {AdminCategoriesComponent} from "./admin/category/admin-categories/admin-categories.component";
import {CreateCategoryComponent} from "./admin/category/create-category/create-category.component";
import {roleCheckGuard} from "./auth/role-check.guard";
import {HomeComponent} from "./home/home.component";
import {CreateSubcategoryComponent} from "./admin/subcategory/create-subcategory/create-subcategory.component";
import {AdminSubcategoriesComponent} from "./admin/subcategory/admin-subcategories/admin-subcategories.component";
import {AdminBrandsComponent} from "./admin/brand/admin-brands/admin-brands.component";
import {CreateBrandComponent} from "./admin/brand/create-brand/create-brand.component";

export const appRoutes: Route[] = [
  {
    path: 'admin/categories/list',
    component: AdminCategoriesComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN']
    },
  },
  {
    path: 'admin/categories/create',
    component: CreateCategoryComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN']
    },
  },
  {
    path: 'admin/subcategories/list',
    component: AdminSubcategoriesComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN']
    },
  },
  {
    path: 'admin/subcategories/create',
    component: CreateSubcategoryComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN']
    },
  },
  {
    path: 'admin/brands/list',
    component: AdminBrandsComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN']
    },
  },
  {
    path: 'admin/brands/create',
    component: CreateBrandComponent,
    canActivate: [roleCheckGuard],
    data: {
      authorities: ['ROLE_ADMIN']
    },
  },
  {
    path: '',
    component: HomeComponent,
  },
];
