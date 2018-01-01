package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Instruction;

import java.util.List;

public interface InstructionService {
    List<Instruction> findAll();
    void save(List<Instruction> instructions);
    void save(Instruction instruction);
    void delete(Instruction instruction);
    Instruction findOne(Long id);
}
