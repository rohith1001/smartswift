package com.nttdata.swift.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.swift.domain.Pcrelease;

@Repository
public interface PcreleaseDTO extends CrudRepository<Pcrelease , Integer> {

}