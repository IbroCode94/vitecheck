package com.vitmedics.vitcheck.model.entities.survey;

import com.vitmedics.vitcheck.model.enumeration.productType.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "prescribed_product_type", uniqueConstraints = { @UniqueConstraint(columnNames = { "survey_submission_result_id", "product_type"})})
public class PrescribedProductType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_submission_result_id", nullable = false)
  private SurveySubmissionResult surveySubmissionResult;

  @Column(name="product_type", nullable = false)
  private ProductType productType;

  public PrescribedProductType(ProductType productType) {
    this.productType = productType;
  }

  public static Set<PrescribedProductType> mapProductTypesToEntities(Set<ProductType> productTypes) {
    return productTypes.stream()
            .map(productType -> new PrescribedProductType(productType))
            .collect(Collectors.toCollection(HashSet::new));
  }
}
