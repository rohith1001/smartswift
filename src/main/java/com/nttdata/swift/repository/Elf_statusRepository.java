package com.nttdata.swift.repository;

import com.nttdata.swift.domain.Elf_status;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Elf_status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Elf_statusRepository extends JpaRepository<Elf_status, Long> {

}
