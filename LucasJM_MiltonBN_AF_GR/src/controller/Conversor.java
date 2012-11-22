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

}
