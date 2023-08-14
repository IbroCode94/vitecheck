package com.vitmedics.vitcheck.controller.v1.api;

import com.vitmedics.vitcheck.dto.APIResponse;
import com.vitmedics.vitcheck.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MedicationsControllerImpl implements MedicationsController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping
    public APIResponse getMedications(final Optional<String> filterText, final int limit, final List<Integer> nutrientIdFilter) {

        return APIResponse
                .ok()
                .setPayload(medicationService.getMedications(filterText, limit, nutrientIdFilter));
    }

}
