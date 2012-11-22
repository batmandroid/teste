package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.GramaticaRegular;
import model.Transicao;

public class Conversor {

	public GramaticaRegular converteAutomatoParaGramatica(Automato automato) {

		GramaticaRegular gr = new GramaticaRegular();

		List<Estado> estados = automato.getEstados();
		Estado inicial = automato.getEstadoInicial();
		estados.remove(inicial);

		List<List<String>> todosEstados = new ArrayList<List<String>>();
		List<String> estadoPai = new ArrayList<String>();
		estadoPai.add(inicial.getNome());

		List<Transicao> transInicial = inicial.getTransicoes();
		for (Transicao transicao : transInicial) {
			estadoPai.add(transicao.getSimbolo() + transicao.getEstadoDestino().getNome());
		}

		todosEstados.add(estadoPai);

		for (Estado estado : estados) {

			List<String> estadosDemais = new ArrayList<String>();

			estadosDemais.add(estado.getNome());

			for (Transicao transicao : estado.getTransicoes()) {
				estadosDemais.add(transicao.getSimbolo() + transicao.getEstadoDestino().getNome());
			}

			if (estado.isEstFinal()) {
				estadosDemais.add("$");
			}

			todosEstados.add(estadosDemais);
		}

		gr.setOrdenado(todosEstados);

		return gr;
	}

	public Automato converteGramaticaParaAutomato(GramaticaRegular gr) {

		List<List<String>> allstates = gr.getOrdenado();
		List<String> terminais = new ArrayList<String>();
		List<String> naoTerminais = new ArrayList<String>();
		List<Estado> estados = new ArrayList<Estado>();


		// pega os estados
		for (List<String> list : allstates) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0 && !naoTerminais.contains(list.get(i))) {
					naoTerminais.add(list.get(i));
				}
			}
		}

		estados = generateEstados(naoTerminais);

		// pega os simbolos
		for (List<String> list : allstates) {
			for (int i = 0; i < list.size(); i++) {
				if (i > 0 && !terminais.contains(list.get(i).substring(0, 1))) {
					terminais.add(list.get(i).substring(0, 1));
				}
			}
		}

		Automato automato = new Automato("", "");
		
		for (List<String> list : allstates) {
			Estado estado = getEstadoBySimbolo(estados, list.get(0));
			for (int i = 0; i < list.size(); i++) {
				
				if(i==0){
					estado = getEstadoBySimbolo(estados, list.get(i));
				}
				
				if (i != 0) {
					String toda = list.get(i);
					String partTerminal = getTerminal(toda);
					String partEstado = getParteEstado(toda);

					if (!partTerminal.equals("") && !partEstado.equals("")) {
						Transicao transicao = new Transicao(partTerminal.charAt(0), getEstadoBySimbolo(estados, partEstado));
						estado.addTransicao(transicao);

					} else {

						if (partTerminal.equals("&")) {
							estado.setEstFinal(true);
						} else {
							Transicao transicao = new Transicao(partTerminal.charAt(0), getEstadoBySimbolo(estados, "qf"));
							estado.addTransicao(transicao);
						}
					}
				}
			}
		}

		arrumaNomes(estados);
		
		automato.setEstados(estados);
		
		System.out.println(automato);

		return automato;
	}

	private void arrumaNomes(List<Estado> estados) {
		for (int i = 0; i < estados.size(); i++) {
			estados.get(i).setNome("q"+i);
		}
	}

	private String getParteEstado(String toda) {
		String x = "";
		if (toda.length() > 1) {
			x = toda.substring(1);
		}
		return x;
	}

	private String getTerminal(String toda) {
		String x = "";
		if (toda.length() == 1) {
			x = toda;
		} else {
			x = toda.substring(0, 1);
		}
		return x;
	}

	private Estado getEstadoBySimbolo(List<Estado> estados, String nomeEstado) {
		Estado estado = null;
		for (Estado est : estados) {
			if (est.getNome().equals(nomeEstado)) {
				return est;
			}
		}
		return estado;
	}

	private List<Estado> generateEstados(List<String> strs) {

		List<String> strEstados = new ArrayList<String>(strs);
		List<Estado> estados = new ArrayList<>();
		String strInicial = strEstados.get(0);
		strEstados.remove(strInicial);

		Estado estadoInicial = new Estado(strInicial, true, false);
		estados.add(estadoInicial);

		for (String strEstado : strEstados) {
			Estado state = new Estado(strEstado, false, false);
			estados.add(state);
		}

		Estado estadofinal = new Estado("qf", false, true);
		estados.add(estadofinal);
		
		return estados;
	}

}
