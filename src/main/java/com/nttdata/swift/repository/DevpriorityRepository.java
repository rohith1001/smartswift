package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Devpriority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Devpriority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevpriorityRepository extends JpaRepository<Devpriority, Long> {

}
