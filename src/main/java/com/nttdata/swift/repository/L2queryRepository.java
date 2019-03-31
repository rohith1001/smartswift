package com.nttdata.swift.repository;

import com.nttdata.swift.domain.L2query;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the L2query entity.
 */
@SuppressWarnings("unused")
@Repository
public interface L2queryRepository extends JpaRepository<L2query, Long> {

}
