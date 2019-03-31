package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Devservice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Devservice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevserviceRepository extends JpaRepository<Devservice, Long> {

}
