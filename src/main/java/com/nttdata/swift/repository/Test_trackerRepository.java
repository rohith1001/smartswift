package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Test_tracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Test_tracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Test_trackerRepository extends JpaRepository<Test_tracker, Long> {

}
