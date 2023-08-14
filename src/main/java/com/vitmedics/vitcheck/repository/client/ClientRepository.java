package com.vitmedics.vitcheck.repository.client;

import com.vitmedics.vitcheck.model.entities.client.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, String> {

}
