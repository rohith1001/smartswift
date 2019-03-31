package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Pcdefect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pcdefect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PcdefectRepository extends JpaRepository<Pcdefect, Long> {

}
