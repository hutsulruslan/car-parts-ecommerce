package com.hutsdev.ecom.product.infrastructure.primary;

import com.hutsdev.ecom.product.application.ProductsApplicationService;
import com.hutsdev.ecom.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hutsdev.ecom.product.infrastructure.primary.ProductAdminResource.ROLE_ADMIN;

@RestController
@RequestMapping("/api/brands")
public class BrandResource {

  private static final Logger log = LoggerFactory.getLogger(BrandResource.class);
  private final ProductsApplicationService productsApplicationService;

  public BrandResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<RestBrand> save(@RequestBody RestBrand restBrand) {
    var brand = productsApplicationService.createBrand(RestBrand.toDomain(restBrand));
    return ResponseEntity.ok(RestBrand.fromDomain(brand));
  }

  @DeleteMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<UUID> delete(@RequestParam("publicId") UUID id) {
    try {
      var deleted = productsApplicationService.deleteBrand(new PublicId(id));
      return ResponseEntity.ok(deleted.value());
    } catch (EntityNotFoundException e) {
      log.error("Could not delete brand with id {}", id, e);
      var detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
      return ResponseEntity.of(detail).build();
    }
  }

  @GetMapping
  public ResponseEntity<Page<RestBrand>> getAll(Pageable pageable) {
    var brands = productsApplicationService.findAllBrand(pageable);
    var rest = new PageImpl<>(
      brands.getContent().stream().map(RestBrand::fromDomain).toList(),
      pageable,
      brands.getTotalElements()
    );
    return ResponseEntity.ok(rest);
  }
}

