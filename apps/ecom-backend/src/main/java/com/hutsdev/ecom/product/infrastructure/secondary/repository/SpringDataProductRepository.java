package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.domain.aggregate.Picture;
import com.hutsdev.ecom.product.domain.aggregate.Product;
import com.hutsdev.ecom.product.domain.repository.ProductRepository;
import com.hutsdev.ecom.product.domain.vo.PublicId;
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

@Repository
public class SpringDataProductRepository implements ProductRepository {

  private final JpaSubCategoryRepository jpaSubCategoryRepository;

  private final JpaProductRepository jpaProductRepository;

  private final JpaProductPictureRepository jpaProductPictureRepository;

  public SpringDataProductRepository(JpaSubCategoryRepository jpaSubCategoryRepository, JpaProductRepository jpaProductRepository, JpaProductPictureRepository jpaProductPictureRepository) {
    this.jpaSubCategoryRepository = jpaSubCategoryRepository;
    this.jpaProductRepository = jpaProductRepository;
    this.jpaProductPictureRepository = jpaProductPictureRepository;
  }

  @Override
  public Product save(Product productToCreate) {
    ProductEntity newProductEntity = ProductEntity.from(productToCreate);
    Optional<SubCategoryEntity> subCategoryEntityOpt = jpaSubCategoryRepository.findByPublicId(newProductEntity.getSubCategory().getPublicId());
    SubCategoryEntity subCategoryEntity = subCategoryEntityOpt.orElseThrow(() -> new EntityNotFoundException(String.format("No SubCategory found with Id %s", productToCreate.getSubCategory().getPublicId())));
    newProductEntity.setSubCategory(subCategoryEntity);
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
}
