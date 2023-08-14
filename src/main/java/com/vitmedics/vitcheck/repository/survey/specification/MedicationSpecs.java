package com.vitmedics.vitcheck.repository.survey.specification;

import com.vitmedics.vitcheck.model.entities.survey.Medication;
import com.vitmedics.vitcheck.model.entities.survey.MedicationInteraction;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;

public class MedicationSpecs {

    public static Specification<Medication> nutrientIdIn(List<Integer> nutrientIds) {
        return (medication, ec, cb) -> {

            if(nutrientIds == null || nutrientIds.isEmpty()) {
                return cb.and();
            }

            Join<Medication, MedicationInteraction> joinInteraction = medication.join("medicationInteractions", JoinType.INNER);
            joinInteraction.on(joinInteraction.get("nutrientId").in(nutrientIds));

            return cb.and();
        };
    }

    public static Specification<Medication> nameContains(String filterText) {
        return (proposal, ec, cb) -> {

            if( Strings.isBlank(filterText)) {
                return cb.and();
            }

            return cb.like(cb.upper(proposal.get("name")), "%" + filterText.toUpperCase() + "%");
        };
    }

    public static Specification<Medication> orderByName() {
        return (proposal, ec, cb) ->  {
            ec.orderBy(cb.asc(proposal.get("name")));
            return cb.and();
        };
    }
}
