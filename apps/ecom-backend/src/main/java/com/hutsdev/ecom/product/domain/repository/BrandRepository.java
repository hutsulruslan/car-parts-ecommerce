package com.hutsdev.ecom.product.domain.repository;

import com.hutsdev.ecom.product.domain.aggregate.Brand;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandRepository {

  Page<Brand> findAll(Pageable pageable);

  int delete(PublicId publicId);

  Brand save(Brand brandToCreate);

}
