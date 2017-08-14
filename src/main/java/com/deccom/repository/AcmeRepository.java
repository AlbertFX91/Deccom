package com.deccom.repository;

import com.deccom.domain.Acme;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Acme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcmeRepository extends MongoRepository<Acme,String> {
    
}
