package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Bulkdefect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bulkdefect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BulkdefectRepository extends JpaRepository<Bulkdefect, Long> {

}
