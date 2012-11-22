package controller;

import java.util.ArrayList;
import java.util.List;


import model.Automato;
import model.ClassesDeEquivalencia;
import model.Estado;
import model.Transicao;

public class Minimizador {

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
				if(!estado.isEstFinal()  && (estado.getTransicoes().size() == 0 || isTransicaoRecursiva(estado))){
					mortos.add(estado);
				}
			}
		}
		
		return mortos;
	}

	private Automato removeEstadosEsquivalentes(Automato e) {
		
		ClassesDeEquivalencia classesDeEquivalencia = new ClassesDeEquivalencia(e);

		List<List<Estado>> classes = classesDeEquivalencia.separarClasses();

		for (List<Estado> lista : classes) {

			if (lista.size() > 1) {

				for (int i = 0; i < lista.size(); i++) {

					if (i != 0) {
						int index = getIndiceEstado(lista.get(i).getNome(), e);
						renomearTransacoes(lista.get(i), lista.get(0), e);
						e.getEstados().remove(index);
						lista.remove(i);
					}
				}

			}
		}
		
		return e;
	}
	
	public void renomearTransacoes(Estado antigo, Estado novo, Automato automato ) {

		for (Estado e : automato.getEstados()) {
			boolean b = false;

			for (Character s : automato.getAllSimbolos()) {

				List<Transicao> lista = automato.getTransicoes();

				for (Transicao t : lista) {

					if (t.getEstadoDestino().getNome().equals(antigo.getNome())) {
						b = true;
					}
				}

				if (b == true) {
					lista.clear();
					//criarTransicao(e, novo, s);
				}
			}

		}
	}
	
	public int getIndiceEstado(String estado, Automato e) {
		for (int i = 0; i < e.getEstados().size(); i++) {
			if (e.getEstados().get(i).getNome().equals(estado)) {
				return i;
			}
		}
		return -1;
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

	private boolean isTransicaoRecursiva(Estado estado) {
		List<Transicao> transicoes = estado.getTransicoes();
		for (Transicao transicao : transicoes) {
			if(!transicao.getEstadoDestino().equals(estado)){
				return false;
			}
		}
		return true;
	}

	public Automato minimizaAutomto(Automato automato) {
		
		Automato naoInalcancaveis = removeInalcancaveis(automato);
		Automato naoMortos = removeEstadosMortos(naoInalcancaveis);
	//	Automato naoEquivalentes = removeEstadosEsquivalentes(naoMortos);
		
		return naoMortos;
	}
	
}
