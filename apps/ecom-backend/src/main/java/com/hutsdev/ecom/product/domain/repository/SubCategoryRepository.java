package com.hutsdev.ecom.product.domain.repository;

import com.hutsdev.ecom.product.domain.aggregate.SubCategory;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubCategoryRepository {

  Page<SubCategory> findAll(Pageable pageable);

  int delete(PublicId publicId);

  SubCategory save(SubCategory subCategoryToCreate);

}
