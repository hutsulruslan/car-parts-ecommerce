package com.hutsdev.ecom.order.application;

import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartRequest;
import com.hutsdev.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.hutsdev.ecom.order.domain.order.service.CartReader;
import com.hutsdev.ecom.product.application.ProductsApplicationService;
import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

  private final ProductsApplicationService productsApplicationService;
  private final CartReader cartReader;

  public OrderApplicationService(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
    this.cartReader = new CartReader();
  }

  @Transactional(readOnly = true)
  public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
    List<PublicId> publicIds = detailCartRequest.items().stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return cartReader.getDetails(productsInformation);
  }
}
