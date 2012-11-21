package controller;

import java.util.List;

import model.Automato;
import model.Estado;
import model.GramaticaRegular;
import model.Transicao;

public class Controller {

	Persistencia persistencia;

	public Controller() {
		persistencia = new Persistencia();
	}

	public Automato determinizaAutomato(Automato automato) {
		return new Determinizador().determinizaAutomato(automato);
	}

	public Automato minimizaAutomato(Automato automato) {
		return new Minimizador().minimizaAutomto(automato);
	}
	
	public GramaticaRegular converteAFtoGR(Automato e){
		return new Conversor().converteAutomatoParaGramatica(e);
	}

	public boolean validaSentenca(Automato automato, List<Character> sentenca) {

		System.out.println(automato);
		System.out.println(sentenca);
		
		List<Estado> estados = automato.getEstados();
		boolean isEstadoFinal = false;
		Estado estadoInicial = null;
		// procura estado inicial e verifica se existe pelo menos um estado
		// final

		System.out.println(estados.size());

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

			System.out.println("INICIAL: " + estadoAtual + estadoAtual.getTransicoes().size());

			int simbolosReconhecidos = 0; // contador de símbolos reconhecidos

			// a partir do estado inicial, começa a testar a string
			for (char s : sentenca) {
				// boolean achou = false;
				// percorre cada transição
				for (Transicao t : estadoAtual.getTransicoes()) {
					System.out.println(estadoAtual.getNome() + " " + estadoAtual.getTransicoes().size());
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

	public Persistencia getPersistencia() {
		return persistencia;
	}

}
