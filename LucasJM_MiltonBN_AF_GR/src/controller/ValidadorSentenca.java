package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.Transicao;

public class ValidadorSentenca {

	public boolean validaSentenca(Automato automato, List<Character> sentenca){
		List<Estado> estados = automato.getEstados();
		boolean isEstadoFinal = false;
		Estado estadoInicial = null;
		// procura estado inicial e verifica se existe pelo menos um estado
		// final
		for (Estado e : estados) {

			if (e.isEstFinal()) {
				isEstadoFinal = true;
			}

			if (e.isInicial()) {
				estadoInicial = e;
			}
		}

		if (isEstadoFinal && estadoInicial != null) {

			Estado estadoAtual = estadoInicial;

			int simbolosReconhecidos = 0; // contador de símbolos reconhecidos

			// a partir do estado inicial, começa a testar a string
			for (char s : sentenca) {
				// boolean achou = false;
				// percorre cada transição
				for (Transicao t : estadoAtual.getTransicoes()) {
					// se um dos símbolos da transição for igual ao caracter
					// atual
					if (t.getSimbolo().equals(Character.valueOf(s))) {
						simbolosReconhecidos++;
						estadoAtual = t.getEstadoDestino();
					}
				}
			}

			if (estadoAtual.isEstFinal() && simbolosReconhecidos == sentenca.size()) {
				return true;
			} else if (estadoAtual.isEstFinal() && simbolosReconhecidos == 0 && sentenca.size() == 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public List<String> enumerarSentencas(int lim, Automato automato){
		
		char [] letras = {'a','b','c'};  
		  
		for( int i = 0 ; i < letras.length ; i++ ){  
		   for( int j = 0 ; j < letras.length ; j++ ){  
		      for( int k = 0 ; k < letras.length ; k++ ){  
		          System.out.println("" + letras[i] + letras[j] + letras[k] );  
		      }  
		   }  
		}  
		
		return new ArrayList<String>();
	}
	

}
