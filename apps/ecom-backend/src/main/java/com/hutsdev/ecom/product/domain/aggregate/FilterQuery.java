package com.hutsdev.ecom.product.domain.aggregate;

import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;

@Builder
public record FilterQuery(
  PublicId subCategoryId,
  List<PublicId> brandPublicIds
) {}
