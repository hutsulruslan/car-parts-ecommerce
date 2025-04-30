import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CartFailureComponent } from './cart-failure.component';

describe('CartFailureComponent', () => {
  let component: CartFailureComponent;
  let fixture: ComponentFixture<CartFailureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CartFailureComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CartFailureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
