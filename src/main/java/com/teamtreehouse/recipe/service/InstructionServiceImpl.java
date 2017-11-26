package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Instruction;
import com.teamtreehouse.recipe.repository.InstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructionServiceImpl implements InstructionService {
    @Autowired
    private InstructionRepository instructionsRepository;


    @Override
    public List<Instruction> findAll() {
        return (List<Instruction>) instructionsRepository.findAll();
    }

    @Override
    public void save(List<Instruction> instructions) {
        instructionsRepository.save(instructions);
    }

    @Override
    public void save(Instruction instruction) {
        instructionsRepository.save(instruction);
    }

    @Override
    public void delete(Long id) {
        instructionsRepository.delete(id);
    }

    @Override
    public Instruction findOne(Long id) {
        return instructionsRepository.findOne(id);
    }
}
