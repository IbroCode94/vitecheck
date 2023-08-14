package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.model.survey.MedicationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicationService {
    List<MedicationDto> getMedications(Optional<String> filterText, int limit, List<Integer> nutrientIdFilter);
}

