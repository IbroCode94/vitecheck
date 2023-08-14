package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.model.survey.MedicationDto;
import com.vitmedics.vitcheck.model.entities.survey.Medication;
import com.vitmedics.vitcheck.repository.survey.MedicationRepository;
import com.vitmedics.vitcheck.repository.survey.specification.MedicationSpecs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MedicationServiceImpl extends BaseService implements MedicationService {

    private static final Logger log = LogManager.getLogger(MedicationServiceImpl.class);

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MedicationDto> getMedications(Optional<String> filterText, int limit, List<Integer> nutrientIdFilter) {

        PageRequest page = PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "name"));

        Page<Medication> medications;

        if(nutrientIdFilter != null && !nutrientIdFilter.isEmpty())
        {
            Specification<Medication> spec = Specification
                    .where(MedicationSpecs.nameContains(filterText.orElseGet(() -> "")))
                    .and(MedicationSpecs.nutrientIdIn(nutrientIdFilter))
                    .and(MedicationSpecs.orderByName());

            medications = medicationRepository.findAll(spec, page);
        }
        else {
            medications = medicationRepository.findByNameContainingIgnoreCase(filterText.orElseGet(() -> ""), page);
        }

        return StreamSupport
                .stream(medications.spliterator(), false)
                .map(medication -> modelMapper.map(medication, MedicationDto.class))
                .collect(Collectors.toList());
    }
}
