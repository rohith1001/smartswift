package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Configslas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Configslas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigslasRepository extends JpaRepository<Configslas, Long> {

}
