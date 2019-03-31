package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Holidays;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Holidays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, Long> {

}
