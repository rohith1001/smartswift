package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Samplebulk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Samplebulk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SamplebulkRepository extends JpaRepository<Samplebulk, Long> {

}
