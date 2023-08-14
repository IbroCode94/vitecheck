package com.vitmedics.vitcheck.repository.survey;

import com.vitmedics.vitcheck.model.entities.survey.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicationRepository extends CrudRepository<Medication, Integer>, JpaSpecificationExecutor<Medication> {

    Page<Medication> findByNameContainingIgnoreCase(String name, Pageable page);
    List<Medication> findByNameContainingIgnoreCase(String name);

}
