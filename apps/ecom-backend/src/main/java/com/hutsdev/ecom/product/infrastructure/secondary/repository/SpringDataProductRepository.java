package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.domain.aggregate.Picture;
import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.repository.ProductRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.BrandEntity;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.PictureEntity;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.ProductEntity;
import com.hutsdev.ecom.product.infrastructure.secondary.entity.SubCategoryEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class SpringDataProductRepository implements ProductRepository {

  private final JpaSubCategoryRepository jpaSubCategoryRepository;

  private final JpaBrandRepository jpaBrandRepository;

  private final JpaProductRepository jpaProductRepository;

  private final JpaProductPictureRepository jpaProductPictureRepository;

  public SpringDataProductRepository(JpaSubCategoryRepository jpaSubCategoryRepository,
                                     JpaBrandRepository jpaBrandRepository,
                                     JpaProductRepository jpaProductRepository,
                                     JpaProductPictureRepository jpaProductPictureRepository) {
    this.jpaSubCategoryRepository = jpaSubCategoryRepository;
    this.jpaBrandRepository = jpaBrandRepository;
    this.jpaProductRepository = jpaProductRepository;
    this.jpaProductPictureRepository = jpaProductPictureRepository;
  }

  @Override
  public Product save(Product productToCreate) {
    UUID brandPublicId = productToCreate.getBrand().getPublicId().value();
    UUID subCategoryPublicId = productToCreate.getSubCategory().getPublicId().value();

    BrandEntity brandEntity = jpaBrandRepository.findByPublicId(brandPublicId)
      .orElseThrow(() -> new EntityNotFoundException("No Brand with Id " + brandPublicId));

    SubCategoryEntity subCategoryEntity = jpaSubCategoryRepository.findByPublicId(subCategoryPublicId)
      .orElseThrow(() -> new EntityNotFoundException("No SubCategory with Id " + subCategoryPublicId));

    // ВАЖЛИВО: тут вже передаєш готові managed entity
    ProductEntity newProductEntity = ProductEntity.from(productToCreate, brandEntity, subCategoryEntity);

    ProductEntity savedProductEntity = jpaProductRepository.save(newProductEntity);

    saveAllPictures(productToCreate.getPictures(), savedProductEntity);

    return ProductEntity.to(savedProductEntity);
  }

  private void saveAllPictures(List<Picture> pictures, ProductEntity newProductEntity) {
    Set<PictureEntity> picturesEntities = PictureEntity.from(pictures);

    for (PictureEntity pictureEntity: picturesEntities) {
      pictureEntity.setProduct(newProductEntity);
    }

    jpaProductPictureRepository.saveAll(picturesEntities);
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    return jpaProductRepository.findAll(pageable).map(ProductEntity::to);
  }

  @Override
  public int delete(PublicId publicId) {
    return jpaProductRepository.deleteByPublicId(publicId.value());
  }

  @Override
  public Page<Product> findAllFeaturedProduct(Pageable pageable) {
    return jpaProductRepository.findAllByFeaturedTrue(pageable).map(ProductEntity::to);
  }
}
