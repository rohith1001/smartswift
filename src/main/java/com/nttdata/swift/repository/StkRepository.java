package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Stk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Stk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StkRepository extends JpaRepository<Stk, Long> {

}
