package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Minimizador {

	// Se um estado inalcancavel leva para ele mesmo e trata como inalcancavel =/
	public List<Estado> getInalcancaveis(Automato e) {
		List<Estado> inalcancaveis = new ArrayList<Estado>(e.getEstados());
		List<Estado> alcancaveis = new ArrayList<Estado>();

		for (Estado estado : inalcancaveis) {
			List<Transicao> transicoes = estado.getTransicoes();
			for (Transicao transicao : transicoes) {
				if (!alcancaveis.contains(transicao.getEstadoDestino())) {
					alcancaveis.add(estado);
				}
			}
		}
		inalcancaveis.removeAll(alcancaveis);
		return inalcancaveis;
	}

	// esse tá bala
	public List<Estado> getInalcancaveis2(Automato e) {

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

	public Automato removeInalcancaveis(Automato e, List<Estado> inalcancaveis) {
		e.getEstados().removeAll(inalcancaveis);
		return e;
	}

	public List<Estado> getEstadosMortos(Automato automato){
		List<Estado> mortos = new ArrayList<Estado>();
		List<Estado> allEstado = automato.getEstados();
		
		for (Estado estado : allEstado) {
			if(estado.getTransicoes() == null){
				if(!estado.isEstFinal()){
					mortos.add(estado);
				}
			} else{
				if(!estado.isEstFinal()  && (estado.getTransicoes().size() == 0 || isTransicaoNaoRecursiva(estado))){
					
				}
			}
		}
		
		return mortos;
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
