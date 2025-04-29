package com.hutsdev.ecom.order.domain.order.aggregate;

import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
