import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'ecom-cart-failure',
  imports: [CommonModule, FaIconComponent],
  templateUrl: './cart-failure.component.html',
  styleUrl: './cart-failure.component.scss',
})
export class CartFailureComponent {}
