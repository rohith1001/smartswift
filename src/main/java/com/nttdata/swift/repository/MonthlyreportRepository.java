package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Monthlyreport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Monthlyreport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyreportRepository extends JpaRepository<Monthlyreport, Long> {

}
