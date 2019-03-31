package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Impact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Impact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpactRepository extends JpaRepository<Impact, Long> {

}
