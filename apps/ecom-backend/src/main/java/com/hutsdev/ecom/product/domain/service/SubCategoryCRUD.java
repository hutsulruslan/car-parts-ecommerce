package com.hutsdev.ecom.product.domain.service;

import com.hutsdev.ecom.product.domain.aggreagate.SubCategory;
import com.hutsdev.ecom.product.domain.repository.SubCategoryRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SubCategoryCRUD {

  private final SubCategoryRepository subCategoryRepository;

  public SubCategoryCRUD(SubCategoryRepository subCategoryRepository) {
    this.subCategoryRepository = subCategoryRepository;
  }

  public SubCategory save(SubCategory newSubCategory) {
    newSubCategory.initDefaultFields();
    return subCategoryRepository.save(newSubCategory);
  }

  public Page<SubCategory> findAll(Pageable pageable) {
    return subCategoryRepository.findAll(pageable);
  }

  public PublicId delete(PublicId subCategoryId) {
    int nbOfRowsDeleted = subCategoryRepository.delete(subCategoryId);

    if(nbOfRowsDeleted != 1) {
      throw new EntityNotFoundException(String.format("No SubCategory deleted with id %s", subCategoryId));
    }

    return subCategoryId;
  }

}
