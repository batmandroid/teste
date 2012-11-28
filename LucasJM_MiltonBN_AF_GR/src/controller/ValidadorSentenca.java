package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Automato;
import model.Estado;
import model.Transicao;

public class ValidadorSentenca {

	private Set<String> sentencasGeradas;
	
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
		sentencasGeradas = new HashSet<String>();
		Estado inicial = automato.getEstadoInicial();
		
		List<Transicao> transicoesIniciais = new ArrayList<Transicao>(inicial.getTransicoes());
		
		List<String> sentencasValidas = new ArrayList<String>();
		
		for (Transicao tri : transicoesIniciais) {
			
			String x = tri.getSimbolo().toString();
			
			Estado estado = tri.getEstadoDestino();
				
			percorreEstado(estado, x, x.length(), lim);
			
			sentencasGeradas.add(x);
		}
		
		for (String sentenca : sentencasGeradas) {
			List<Character> itensSentenca = new ArrayList<Character>();
			for (int i = 0; i < sentenca.length(); i++) {
				itensSentenca.add(sentenca.charAt(i));
			}
			if(validaSentenca(automato, itensSentenca)){
				sentencasValidas.add(sentenca);
			}
		}
		
		return sentencasValidas;
	}

	public void percorreEstado(Estado estado, String sentenca, int nivel, int limite){
		if(nivel == limite){
			return;
		}
		for (Transicao transicao : estado.getTransicoes()) {
			sentencasGeradas.add(sentenca + transicao.getSimbolo());
			percorreEstado(transicao.getEstadoDestino(), sentenca + transicao.getSimbolo(), nivel + 1, limite);
		}
	}

}
