package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Hld;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hld entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HldRepository extends JpaRepository<Hld, Long> {

}
