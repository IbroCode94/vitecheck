package com.vitmedics.vitcheck.service;

import com.vitmedics.vitcheck.dto.model.survey.NutrientDto;
import com.vitmedics.vitcheck.repository.survey.NutrientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class NutrientServiceImpl extends BaseService implements NutrientService {

    private static final Logger log = LogManager.getLogger(NutrientServiceImpl.class);

    @Autowired
    private NutrientRepository nutrientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NutrientDto> getNutrients() {

        return StreamSupport
                .stream(nutrientRepository.findByOrderByNameAsc().spliterator(), false)
                .map(nutrient -> modelMapper.map(nutrient, NutrientDto.class))
                .collect(Collectors.toList());
    }
}

