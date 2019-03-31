package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Development_tracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Development_tracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Development_trackerRepository extends JpaRepository<Development_tracker, Long> {

}
