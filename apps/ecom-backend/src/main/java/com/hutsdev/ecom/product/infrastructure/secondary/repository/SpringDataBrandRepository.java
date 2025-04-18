package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.domain.aggregate.Brand;
import com.hutsdev.ecom.product.domain.repository.BrandRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SpringDataBrandRepository implements BrandRepository {

  private final JpaBrandRepository jpaBrandRepository;

  public SpringDataBrandRepository(JpaBrandRepository jpaBrandRepository) {
    this.jpaBrandRepository = jpaBrandRepository;
  }

  @Override
  public Page<Brand> findAll(Pageable pageable) {
    return jpaBrandRepository.findAll(pageable).map(BrandEntity::to);
  }

  @Override
  public int delete(PublicId publicId) {
    return jpaBrandRepository.deleteByPublicId(publicId.value());
  }

  @Override
  public Brand save(Brand brandToCreate) {
    BrandEntity entity = BrandEntity.from(brandToCreate);
    return BrandEntity.to(jpaBrandRepository.save(entity));
  }
}
