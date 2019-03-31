package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Configtype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Configtype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigtypeRepository extends JpaRepository<Configtype, Long> {

}
