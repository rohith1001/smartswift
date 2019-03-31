package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Issuetype;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Issuetype entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuetypeRepository extends JpaRepository<Issuetype, Long> {

}
