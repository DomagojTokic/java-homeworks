package hr.fer.zemris.java.simplecomp.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;


@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class CallTest {

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
	public void testCall() {
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);

		InstructionArgument arg = mock(InstructionArgument.class);
		
		when(arguments.size()).thenReturn(1);
		when(arguments.get(0)).thenReturn(arg);
		
		when(arg.isNumber()).thenReturn(true);
		when(arg.getValue()).thenReturn(20);
		when(registers.getProgramCounter()).thenReturn(10);
		
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(50);
		
		
		Instruction call = new InstrCall(arguments);
		call.execute(computer);
		
		verify(registers, times(1)).getProgramCounter();
		verify(registers, times(1)).setProgramCounter(19);
	}

}
