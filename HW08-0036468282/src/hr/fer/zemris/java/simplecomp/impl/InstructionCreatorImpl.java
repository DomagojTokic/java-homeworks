package hr.fer.zemris.java.simplecomp.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;

/**
 * Implementacija {@link InstructionCreator} sučelja
 * 
 * @author Domagoj
 *
 */
public class InstructionCreatorImpl implements InstructionCreator {

	/**
	 * Paket koji sadrži sve implementirane instrukcije
	 */
	String classPath;

	/**
	 * Stvori instancu {@link InstructionCreatorImpl}
	 * 
	 * @param classPath
	 */
	public InstructionCreatorImpl(String classPath) {
		this.classPath = classPath;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Instruction getInstruction(String name, List<InstructionArgument> arguments) {
		try {
			name = "Instr" + name.substring(0, 1).toUpperCase() + name.substring(1);
			Class instrukcija = Class.forName(classPath + "." + name);
			Constructor constructor = instrukcija.getConstructor(List.class);

			Instruction instruction = (Instruction) constructor.newInstance(arguments);
			return instruction;

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
