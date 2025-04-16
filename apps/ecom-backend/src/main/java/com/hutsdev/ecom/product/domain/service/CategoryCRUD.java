package com.hutsdev.ecom.product.domain.service;

import com.hutsdev.ecom.product.domain.aggreagate.Category;
import com.hutsdev.ecom.product.domain.repository.CategoryRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryCRUD {

  private final CategoryRepository categoryRepository;

  public CategoryCRUD(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category save(Category newCategory) {
    newCategory.initDefaultFields();
    return categoryRepository.save(newCategory);
  }

  public Page<Category> findAll(Pageable pageable) {
    return categoryRepository.findAll(pageable);
  }

  public PublicId delete(PublicId categoryId) {
    int nbOfRowsDeleted = categoryRepository.delete(categoryId);

    if(nbOfRowsDeleted != 1) {
      throw new EntityNotFoundException(String.format("No Category Deleted with id %s", categoryId));
    }

    return categoryId;
  }

}
