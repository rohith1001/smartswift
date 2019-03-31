package com.nttdata.swift.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.swift.domain.Pcincident;

@Repository
public interface PcincidentDTO extends CrudRepository<Pcincident , Integer> {

}
