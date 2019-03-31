package com.nttdata.swift.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.swift.domain.Pcdefect;

@Repository
public interface PcdefectDTO extends CrudRepository<Pcdefect , Integer> {

}