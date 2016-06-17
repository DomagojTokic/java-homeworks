package hr.fer.zemris.java.simplecomp.tests;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class MoveTest {

	@Mock
	private Computer computer = mock(ComputerImpl.class);

	@Mock
	private Registers registers = mock(RegistersImpl.class);
	@Mock
	private Memory memory = mock(MemoryImpl.class);
	
	@SuppressWarnings("unchecked")
	@Mock
	private List<InstructionArgument> arguments = mock(List.class);

	@Test
	public void testRegisterToRegister() {
		when(computer.getRegisters()).thenReturn(registers);

		InstructionArgument arg1 = mock(InstructionArgument.class);
		InstructionArgument arg2 = mock(InstructionArgument.class);
		
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);

		when(arg1.isRegister()).thenReturn(true);
		when(arg2.isRegister()).thenReturn(true);
		
		when(arg1.getValue()).thenReturn(0x0000000);
		when(arg2.getValue()).thenReturn(0x0000001);
		when(registers.getRegisterValue(1)).thenReturn(5);
		
		Instruction move = new InstrMove(arguments);
		move.execute(computer);
		
		verify(computer, times(2)).getRegisters();
		verify(registers, times(0)).getRegisterValue(0);
		verify(registers, times(1)).getRegisterValue(1);
		verify(registers, times(1)).setRegisterValue(0, 5);
	}
	
	@Test
	public void testNumberToRegister() {
		when(computer.getRegisters()).thenReturn(registers);

		InstructionArgument arg1 = mock(InstructionArgument.class);
		InstructionArgument arg2 = mock(InstructionArgument.class);
		
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);

		when(arg1.isRegister()).thenReturn(true);
		when(arg2.isRegister()).thenReturn(false);
		
		when(arg1.getValue()).thenReturn(0x0000000);
		when(arg2.getValue()).thenReturn(24);
		
		Instruction move = new InstrMove(arguments);
		move.execute(computer);
		
		verify(computer, times(1)).getRegisters();
		verify(registers, times(1)).setRegisterValue(0, 24);
	}
	
	@Test
	public void testIndirectToAddress() {
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);

		InstructionArgument arg1 = mock(InstructionArgument.class);
		InstructionArgument arg2 = mock(InstructionArgument.class);
		
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);

		when(arg1.isRegister()).thenReturn(true);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.isNumber()).thenReturn(false);
		
		when(arg1.getValue()).thenReturn(new Integer(0x01000100));
		when(arg2.getValue()).thenReturn(new Integer(0x01000201));
		
		when(registers.getRegisterValue(0)).thenReturn(10);
		when(registers.getRegisterValue(1)).thenReturn(2);
		when(memory.getLocation(4)).thenReturn(5);
		
		Instruction move = new InstrMove(arguments);
		move.execute(computer);
		
		verify(computer,times(2)).getRegisters();
		verify(registers,times(1)).getRegisterValue(0);
		verify(registers,times(1)).getRegisterValue(1);
		verify(memory,times(1)).getLocation(4);
		verify(memory,times(1)).setLocation(11, 5);
	}

}
