package com.nttdata.swift.repository;

import com.nttdata.swift.domain.State;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the State entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
