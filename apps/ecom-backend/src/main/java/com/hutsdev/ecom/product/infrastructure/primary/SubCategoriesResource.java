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

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoriesResource {

  private static final Logger log = LoggerFactory.getLogger(SubCategoriesResource.class);
  private final ProductsApplicationService productsApplicationService;

  public SubCategoriesResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('" + ProductAdminResource.ROLE_ADMIN + "')")
  public ResponseEntity<RestSubCategory> save(@RequestBody RestSubCategory dto) {
    var subCategory = productsApplicationService.createSubCategory(RestSubCategory.toDomain(dto));
    return ResponseEntity.ok(RestSubCategory.fromDomain(subCategory));
  }

  @DeleteMapping
  @PreAuthorize("hasAnyRole('" + ProductAdminResource.ROLE_ADMIN + "')")
  public ResponseEntity<UUID> delete(@RequestParam("publicId") UUID id) {
    try {
      var deleted = productsApplicationService.deleteSubCategory(new PublicId(id));
      return ResponseEntity.ok(deleted.value());
    } catch (EntityNotFoundException e) {
      log.error("Could not delete subcategory with id {}", id, e);
      var detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
      return ResponseEntity.of(detail).build();
    }
  }

  @GetMapping
  public ResponseEntity<Page<RestSubCategory>> getAll(Pageable pageable) {
    var subCats = productsApplicationService.findAllSubCategory(pageable);
    var rest = new PageImpl<>(
      subCats.getContent().stream().map(RestSubCategory::fromDomain).toList(),
      pageable,
      subCats.getTotalElements()
    );
    return ResponseEntity.ok(rest);
  }
}
