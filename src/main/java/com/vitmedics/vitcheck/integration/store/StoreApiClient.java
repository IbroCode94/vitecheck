package com.vitmedics.vitcheck.integration.store;

import com.vitmedics.vitcheck.model.PrescribedProduct;
import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallationStoreSetting;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;

public interface StoreApiClient {

    Set<PrescribedProduct> getProducts(@NotNull Set<SurveyInstallationStoreSetting> storeSettings) throws IOException;

}
