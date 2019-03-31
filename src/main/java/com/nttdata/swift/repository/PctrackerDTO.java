package com.nttdata.swift.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.swift.domain.Pctracker;

@Repository
public interface PctrackerDTO extends CrudRepository<Pctracker , Integer> {

}
