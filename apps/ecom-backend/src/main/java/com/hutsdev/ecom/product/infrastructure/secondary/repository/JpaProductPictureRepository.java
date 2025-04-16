package com.hutsdev.ecom.product.infrastructure.secondary.repository;

import com.hutsdev.ecom.product.infrastructure.secondary.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductPicture extends JpaRepository<PictureEntity, Long> {
}
