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
	
	public Automato converteGRtoAf(GramaticaRegular gr){
		return new Conversor().converteGramaticaParaAutomato(gr);
	}

	public boolean validaSentenca(Automato automato, List<Character> sentenca) {
		return new ValidadorSentenca().validaSentenca(automato, sentenca);
	}

	public Persistencia getPersistencia() {
		return persistencia;
	}

}
