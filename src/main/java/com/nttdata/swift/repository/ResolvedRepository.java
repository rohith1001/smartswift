package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Resolved;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Resolved entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResolvedRepository extends JpaRepository<Resolved, Long> {

}
