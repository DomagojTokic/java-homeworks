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
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;


@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class LoadTest {

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
	public void testLoad() {
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);

		InstructionArgument arg1 = mock(InstructionArgument.class);
		InstructionArgument arg2 = mock(InstructionArgument.class);
		
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);

		when(arg1.isRegister()).thenReturn(true);
		when(arg2.isNumber()).thenReturn(true);
		
		when(arg1.getValue()).thenReturn(0x0000000);
		when(arg2.getValue()).thenReturn(55);
		
		when(memory.getLocation(55)).thenReturn(30);
		
		Instruction load = new InstrLoad(arguments);
		load.execute(computer);
		
		verify(memory,times(1)).getLocation(55);
		verify(registers, times(1)).setRegisterValue(0, 30);
	}

}
