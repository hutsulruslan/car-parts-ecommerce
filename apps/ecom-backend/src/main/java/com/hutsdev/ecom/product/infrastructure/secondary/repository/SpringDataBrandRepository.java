package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.domain.aggreagate.SubCategory;
import com.hutsdev.ecom.product.domain.repository.SubCategoryRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.SubCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SpringDataSubCategoryRepository implements SubCategoryRepository {

  private final JpaSubCategoryRepository jpaSubCategoryRepository;

  public SpringDataSubCategoryRepository(JpaSubCategoryRepository jpaSubCategoryRepository) {
    this.jpaSubCategoryRepository = jpaSubCategoryRepository;
  }

  @Override
  public Page<SubCategory> findAll(Pageable pageable) {
    return jpaSubCategoryRepository.findAll(pageable).map(SubCategoryEntity::to);
  }

  @Override
  public int delete(PublicId publicId) {
    return jpaSubCategoryRepository.deleteByPublicId(publicId.value());
  }

  @Override
  public SubCategory save(SubCategory subCategoryToCreate) {
    SubCategoryEntity entity = SubCategoryEntity.from(subCategoryToCreate);
    return SubCategoryEntity.to(jpaSubCategoryRepository.save(entity));
  }
}
