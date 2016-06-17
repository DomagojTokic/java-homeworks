package hr.fer.zemris.java.simplecomp.impl;

/**
 * Pomoćna klasa za provjeru registarskih argumenata naredbi.
 * 
 * @author Domagoj Tokić
 *
 */
public class RegisterUtil {

	/**
	 * Vraća indeks registra
	 * 
	 * @param registerDescriptor Registarski argument
	 * @return Indeks registra
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return registerDescriptor & 0x000000FF;
	}

	/**
	 * Provjerava da li registar koristi indirektno adresiranje
	 * 
	 * @param registerDescriptor Registarski argument
	 * @return True ako registar koristi indirektno adresiranje, inače false.
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (registerDescriptor & 0x01000000) != 0 ? true : false;
	}

	/**
	 * Vraća dani pomak od vrijednosti registra
	 * 
	 * @param registerDescriptor Registarski argument
	 * @return Dani pomak od vrijednosti registra
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		int offset = (registerDescriptor & 0x00FFFF00) >> 8;
		if ((offset & 0x00008000) != 0) {
			offset = offset | 0xFFFF0000;
		}
		return offset;
	}

}
