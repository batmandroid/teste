package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Determinizador {
	
	List<Estado> estadosGerados;
	List<Estado> estadosTratados;
	
	Map<Estado, List<Estado>> recursividade = new HashMap<Estado, List<Estado>>();
	
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
					Estado gerado = geraEstadoDeterministico(estado);
					if(naoContemNaListaDeGerados()){
						estadosGerados.add(gerado);
					}
				}
			} else{
				if(estadoNaoTradado(estado)){
					estadosGerados.add(estado);
				}
			}
			
			while(recursividade.keySet().size() > 0){
				Set<Estado> estadosSemTransicao = recursividade.keySet();
				
				for (Estado estadox : estadosSemTransicao) {
					List<Estado> pseudoDestinos = recursividade.get(estadox);
					geraTransicoesdeEstadosCriados(estadox, pseudoDestinos);
				}
			}

		}
		return determinizado;
	}

	private void geraTransicoesdeEstadosCriados(Estado estadox, List<Estado> pseudoDestinos) {
		// TODO Auto-generated method stub
		
		// vamos fazer a mágica aqui
		
	}

	private boolean naoContemNaListaDeGerados() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean estadoNaoTradado(Estado estado) {
		return !estadosTratados.contains(estado);
	}

	private Estado geraEstadoDeterministico(final Estado estado) {
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
				Transicao ty = geraTransicaoBydestinos(character, destinos);
				transicoes.add(ty);
			}
			
		}
		
		novoEstado.setTransicoes(transicoes);
		novoEstado.setNome(estado.getNome());
		novoEstado.setEstFinal(estado.isEstFinal());
		novoEstado.setInicial(estado.isInicial());
		
		return novoEstado;
	}

	private Transicao geraTransicaoBydestinos(Character character, List<Estado> destinos) {
		String nome = geraNomes(destinos);
		boolean isFinal = verificaFinal(destinos);
		Estado estadoDestino = new Estado(nome, false, isFinal);
		recursividade.put(estadoDestino, destinos);
		Transicao transicao = new Transicao(character, estadoDestino);
		return transicao;
	}
	
	private boolean verificaFinal(List<Estado> destinos) {
		boolean isFinal = false;
		
		for (Estado estado : destinos) {
			if(estado.isEstFinal()){
				isFinal = true;
				break;
			}
		}
		
		return isFinal;
	}

	public String geraNomes(List<Estado> destinos){
		String nome = "[";
		for (Estado estado : destinos) {
			nome=nome+estado.getNome(); 
		}
		return nome="]";
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
