package com.hutsdev.ecom.order.domain.order.aggregate;

import com.hutsdev.ecom.order.domain.order.vo.OrderQuantity;
import com.hutsdev.ecom.order.domain.order.vo.ProductPublicId;
import org.jilt.Builder;

@Builder
public record OrderProductQuantity(OrderQuantity quantity, ProductPublicId productPublicId) {
}
