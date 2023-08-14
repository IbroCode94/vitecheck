package com.vitmedics.vitcheck.repository.survey;

import com.vitmedics.vitcheck.model.entities.survey.Nutrient;
import com.vitmedics.vitcheck.model.entities.survey.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NutrientRepository extends CrudRepository<Nutrient, Integer> {
    List<Nutrient> findByOrderByNameAsc();
}
