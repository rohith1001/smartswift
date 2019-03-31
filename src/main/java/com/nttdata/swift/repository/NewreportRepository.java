package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Newreport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Newreport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewreportRepository extends JpaRepository<Newreport, Long> {

}
