package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Determinizador {
	
	List<Estado> estadosGerados;
	
	public Determinizador() {
		estadosGerados = new ArrayList<Estado>();
	}
	
	public Automato determinizaAutomato(Automato automato){
		
		Automato determinizado = new Automato(automato.getNome(), automato.getDescricao());
		List<Estado> allEstados = automato.getEstados();
		
		if(isAutomatoDeterministico(automato)){
			return automato;
		}
		
		for (Estado estado : allEstados) {
			
			if(estado.isNaoDeterminismo()){
				if(estadoNaoTradado(estado)){
					geraEstadoDeterministico(estado);
				}
			} else{
				if(estadoNaoTradado(estado)){
					estadosGerados.add(estado);
				}
			}

		}
		return determinizado;
	}

	private boolean estadoNaoTradado(Estado estado) {
		return true;
	}

	private Estado geraEstadoDeterministico(Estado estado) {
		Estado novoEstado = new Estado("", false, false);
		Set<Character> simbolos = estado.getAllSimbolos();
		List<Transicao> transicoes = new ArrayList<Transicao>();
		
		for (Character character : simbolos) {
			if(estado.isTransicaoDeterministica(character)){
				Transicao tx = estado.getTransicaoBySimbolo(character);
				if(tx != null){
					transicoes.add(tx);
				}
			} else {
				List<Estado> destinos = estado.getDestinosBySimbolo(character);
			}
		}
		
		return novoEstado;
	}



	private boolean isAutomatoDeterministico(Automato automato) {
		for (Estado estado : automato.getEstados()) {
			if(estado.isNaoDeterminismo()){
				return false;
			}
		}
		return true;
	}

}
