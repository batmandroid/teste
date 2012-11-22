package util;

import java.util.ArrayList;
import java.util.List;

public class NaoTerminaisConstantes {
	
	public static String SIMBOLO_INICIAL = "S";
	List<String> naoTerminais;
	
	
	public NaoTerminaisConstantes() {
		naoTerminais = new ArrayList<String>();
		naoTerminais.add("A");
		naoTerminais.add("B");
		naoTerminais.add("C");
		naoTerminais.add("D");
		naoTerminais.add("E");
		naoTerminais.add("F");
		naoTerminais.add("G");
		naoTerminais.add("H");
		naoTerminais.add("I");
		naoTerminais.add("J");
		naoTerminais.add("K");
		naoTerminais.add("L");
		naoTerminais.add("M");
		naoTerminais.add("N");
		naoTerminais.add("O");
		naoTerminais.add("P");
		naoTerminais.add("Q");
		naoTerminais.add("R");
		// S reservado para o simbolo inicial
		naoTerminais.add("T");
		naoTerminais.add("U");
		naoTerminais.add("V");
		naoTerminais.add("X");
		naoTerminais.add("z");
	}


	public List<String> getListaNaoTerminais() {
		return naoTerminais;
	}
	
}
