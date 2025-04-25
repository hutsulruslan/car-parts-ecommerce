package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.domain.aggregate.SubCategory;
import com.hutsdev.ecom.product.domain.repository.SubCategoryRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.CategoryEntity;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.SubCategoryEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDataSubCategoryRepository implements SubCategoryRepository {

  private final JpaSubCategoryRepository jpaSubCategoryRepository;
  private final JpaCategoryRepository jpaCategoryRepository;

  public SpringDataSubCategoryRepository(JpaSubCategoryRepository jpaSubCategoryRepository, JpaCategoryRepository jpaCategoryRepository) {
    this.jpaSubCategoryRepository = jpaSubCategoryRepository;
    this.jpaCategoryRepository = jpaCategoryRepository;
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
    SubCategoryEntity entityToSave = SubCategoryEntity.from(subCategoryToCreate);
    Optional<CategoryEntity> categoryOpt = jpaCategoryRepository.findByPublicId(
      subCategoryToCreate.getCategory().getPublicId().value()
    );

    CategoryEntity category = categoryOpt.orElseThrow(() ->
      new EntityNotFoundException(String.format(
        "No Category found with publicId %s", subCategoryToCreate.getCategory().getPublicId()
      ))
    );

    entityToSave.setCategory(category);

    SubCategoryEntity savedEntity = jpaSubCategoryRepository.save(entityToSave);
    return SubCategoryEntity.to(savedEntity);
  }
}
