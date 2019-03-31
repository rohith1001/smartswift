package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Iia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Iia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IiaRepository extends JpaRepository<Iia, Long> {

}
