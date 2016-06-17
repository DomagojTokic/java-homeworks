package hr.fer.zemris.java.tecaj.hw1;

/**
 * List class which implements basic list functions such as adding, size,
 * sorting and printing content to standard output.
 * 
 * @author Domagoj Tokić
 * @version 1.0
 */

public class ProgramListe {

	/**
	 * Node class which stores String data
	 */
	static class CvorListe {
		
		/** Next node */
		CvorListe sljedeci;

		/** Data */
		String podatak;
	}

	/**
	 * Demo method which showing class functionality.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;

		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");

		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);

		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);

		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
	}

	/**
	 * Returns size of list.
	 * 
	 * @param cvor Lists first node.
	 * @return size of list.
	 */
	private static int velicinaListe(CvorListe cvor) {
		if (cvor.sljedeci != null) {
			return 1 + velicinaListe(cvor.sljedeci);
		} else {
			return 1;
		}
	}

	/**
	 * Adds new node to list.
	 * 
	 * @param prvi Lists first node.
	 * @param podatak Data stored in node.
	 * @return Lists first node
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		if (prvi == null) {
			prvi = new CvorListe();
			prvi.podatak = podatak;
		} else {
			CvorListe cvor = new CvorListe();
			CvorListe tempCvor = prvi;

			while (tempCvor.sljedeci != null) {
				tempCvor = tempCvor.sljedeci;
			}

			cvor.podatak = podatak;
			tempCvor.sljedeci = cvor;
		}
		return prvi;
	}

	/**
	 * Prints all data stored in list.
	 * 
	 * @param cvor Lists first node.
	 */
	private static void ispisiListu(CvorListe cvor) {
		CvorListe tempCvor = cvor;

		while (tempCvor != null) {
			System.out.println(tempCvor.podatak);
			tempCvor = tempCvor.sljedeci;
		}
	}

	/**
	 * Sorts list using bubble sort.
	 * 
	 * @param cvor Lists first node.
	 * @return Lists first node.
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if (velicinaListe(cvor) > 1) {
			String temp;
			boolean sorted = true;
			CvorListe tempCvor = cvor;

			do {
				sorted = true;
				tempCvor = cvor;

				while (tempCvor.sljedeci != null) {
					if (tempCvor.podatak.compareTo(tempCvor.sljedeci.podatak) > 0) {
						temp = tempCvor.podatak;
						tempCvor.podatak = tempCvor.sljedeci.podatak;
						tempCvor.sljedeci.podatak = temp;

						sorted = false;
					}
					tempCvor = tempCvor.sljedeci;
				}
			} while (!sorted);
		}
		return cvor;
	}
}