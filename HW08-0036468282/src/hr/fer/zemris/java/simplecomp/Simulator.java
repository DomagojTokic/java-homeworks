package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.impl.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Simulator koji se koristi za stvaranje i pokretanje računala koje obrađuje
 * instrukcije.
 * 
 * @author Domagoj Tokić
 *
 */
public class Simulator {

	/**
	 * Ulaz u simulator
	 * 
	 * @param args Adresa ulazne datoteke
	 * @throws Exception ako dođe do pogreške prilikom parsiranja koda.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Program prima jedan argument!");
			System.exit(1);
		}

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);

		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");

		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		ProgramParser.parse(args[0], comp, creator);

		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();

		// Izvedi program
		exec.go(comp);
	}

}
