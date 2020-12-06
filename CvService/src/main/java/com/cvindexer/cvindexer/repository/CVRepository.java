package com.cvindexer.cvindexer.repository;

import com.cvindexer.cvindexer.models.cv;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface pour le repository Elasticsearch
 */
@Repository
public interface CVRepository extends CrudRepository<cv, Long>{
    List<cv> findAll();
}
