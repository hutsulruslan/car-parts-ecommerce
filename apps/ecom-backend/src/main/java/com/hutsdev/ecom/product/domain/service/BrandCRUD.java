package com.hutsdev.ecom.product.domain.service;

import com.hutsdev.ecom.product.domain.aggreagate.Brand;
import com.hutsdev.ecom.product.domain.repository.BrandRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BrandCRUD {

  private final BrandRepository brandRepository;

  public BrandCRUD(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  public Brand save(Brand newBrand) {
    newBrand.initDefaultFields();
    return brandRepository.save(newBrand);
  }

  public Page<Brand> findAll(Pageable pageable) {
    return brandRepository.findAll(pageable);
  }

  public PublicId delete(PublicId categoryId) {
    int nbOfRowsDeleted = brandRepository.delete(categoryId);

    if(nbOfRowsDeleted != 1) {
      throw new EntityNotFoundException(String.format("No Brand Deleted with id %s", categoryId));
    }

    return categoryId;
  }

}
