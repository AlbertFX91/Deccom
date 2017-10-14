package com.deccom.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.deccom.domain.SQLControlVar;

/**
 * Spring Data MongoDB repository for the SQLControlVar entity.
 */
@Repository
public interface SQLControlVarRepository extends MongoRepository<SQLControlVar,String> {
    
}
