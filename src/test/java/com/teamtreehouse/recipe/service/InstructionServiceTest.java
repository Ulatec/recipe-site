package com.teamtreehouse.recipe.service;

import com.teamtreehouse.recipe.model.Instruction;
import com.teamtreehouse.recipe.model.Recipe;
import com.teamtreehouse.recipe.repository.InstructionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class InstructionServiceTest {
    @Mock
    private InstructionRepository instructions;

    @InjectMocks
    private InstructionService service = new InstructionServiceImpl();

    @Test
    public void InstructionAddTest() throws Exception{
        Instruction instruction = new Instruction();
        instruction.setDescription("Test Description");
        instruction.setId(1L);
        instructions.save(instruction);

        when(instructions.findOne(1L)).thenReturn(instruction);
        Instruction foundInstruction = service.findOne(1L);
        assertEquals(foundInstruction.getDescription(), instruction.getDescription());
        assertEquals(foundInstruction.getId(), instruction.getId());
        verify(instructions).findOne(1L);
    }
    @Test
    public void findAll_ReturnsOne() throws Exception{
        Instruction instruction = new Instruction();
        instruction.setDescription("Test Description");
        instruction.setId(1L);
        instructions.save(instruction);
        List<Instruction> instructionList = new ArrayList<>();
        instructionList.add(instruction);

        when(service.findAll()).thenReturn(instructionList);
        assertEquals(service.findAll().size(), 1);
    }
    @Test
    public void deleteInstruction() throws Exception{
        Instruction instruction = new Instruction();
        instruction.setDescription("Test Description");
        when(instructions.findOne(1L)).thenReturn(instruction);
        service.delete(instruction);
        verify(instructions, times(1)).delete(instruction);
    }
}

