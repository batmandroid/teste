package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Minimizador {

//	// Se um estado inalcancavel leva para ele mesmo e trata como inalcancavel =/
//	public List<Estado> getInalcancaveis(Automato e) {
//		List<Estado> inalcancaveis = new ArrayList<Estado>(e.getEstados());
//		List<Estado> alcancaveis = new ArrayList<Estado>();
//
//		for (Estado estado : inalcancaveis) {
//			List<Transicao> transicoes = estado.getTransicoes();
//			for (Transicao transicao : transicoes) {
//				if (!alcancaveis.contains(transicao.getEstadoDestino())) {
//					alcancaveis.add(estado);
//				}
//			}
//		}
//		inalcancaveis.removeAll(alcancaveis);
//		return inalcancaveis;
//	}

	private List<Estado> getInalcancaveis(Automato e) {

		List<Estado> alcancaveis = new ArrayList<Estado>();
		List<Estado> temp = new ArrayList<Estado>();
		List<Estado> inalcancaveis = new ArrayList<Estado>(e.getEstados());

		alcancaveis.add(e.getEstadoInicial());

		boolean fim = true;
		boolean breaker = false;

		while (fim) {
			breaker = false;
			temp.clear();
			temp.addAll(alcancaveis);
			for (Estado alcancavel : temp) {
				List<Transicao> transicoes = alcancavel.getTransicoes();
				for (Transicao transicao : transicoes) {
					if (!alcancaveis.contains(transicao.getEstadoDestino())) {
						alcancaveis.add(transicao.getEstadoDestino());
						breaker = true;
						break;
					}
				}
				if (breaker) {
					fim = true;
					break;
				} else {
					fim = false;
				}
			}
		}

		inalcancaveis.removeAll(alcancaveis);
		
		return inalcancaveis;
	}

	public Automato removeInalcancaveis(Automato e) {
		List<Estado> inalcancaveis = getInalcancaveis(e);
		e.getEstados().removeAll(inalcancaveis);
		return e;
	}

	private List<Estado> getEstadosMortos(Automato automato){
		List<Estado> mortos = new ArrayList<Estado>();
		List<Estado> allEstado = automato.getEstados();
		
		for (Estado estado : allEstado) {
			if(estado.getTransicoes() == null){
				if(!estado.isEstFinal()){
					mortos.add(estado);
				}
			} else{
				if(!estado.isEstFinal()  && (estado.getTransicoes().size() == 0 || isTransicaoNaoRecursiva(estado))){
					mortos.add(estado);
				}
			}
		}
		
		return mortos;
	}
	
	public Automato removeEstadosMortos(Automato e){

		List<Estado> mortos = getEstadosMortos(e);
		List<Estado> allEstados = e.getEstados();
		
		for (Estado estado : allEstados) {
			List<Transicao> removedTransicoes = new ArrayList<Transicao>();
			List<Transicao> transicoes = estado.getTransicoes();
			for (Transicao transicao : transicoes) {
				if(mortos.contains(transicao)){
					removedTransicoes.add(transicao);
				}
			}
			if(removedTransicoes.size() > 0){
				estado.getTransicoes().removeAll(removedTransicoes);
			}
			
		}
		
		e.getEstados().removeAll(mortos);
		
		return e;
	}

	private boolean isTransicaoNaoRecursiva(Estado estado) {
		List<Transicao> transicoes = estado.getTransicoes();
		for (Transicao transicao : transicoes) {
			if(!transicao.getEstadoDestino().equals(estado)){
				return true;
			}
		}
		return false;
	}
	
}
