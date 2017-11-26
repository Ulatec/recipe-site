package com.teamtreehouse.recipe.repository;

import com.teamtreehouse.recipe.model.Instruction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface InstructionRepository extends CrudRepository<Instruction, Long>{
}
