package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.GramaticaRegular;
import model.Transicao;

public class Conversor {

	public GramaticaRegular converteAutomatoParaGramatica(Automato automato){
		
		GramaticaRegular gr = new GramaticaRegular();
				
		List<Estado> estados = automato.getEstados();
		Estado inicial = automato.getEstadoInicial();
		estados.remove(inicial);
		
		String x = inicial.getNome() + " -> ";
		
		ArrayList<ArrayList<String>> todosEstados = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> estadoPai = new ArrayList<String>();
		
		estadoPai.add(inicial.getNome());
		
		List<Transicao>	transInicial = inicial.getTransicoes();
		
		for (Transicao transicao : transInicial) {
			x = x + transicao.getSimbolo() + transicao.getEstadoDestino().getNome()  + " | ";
			estadoPai.add(transicao.getSimbolo() + transicao.getEstadoDestino().getNome());
		}
		
		todosEstados.add(estadoPai);
		
		x = x + "\n";
		
		for (Estado estado : estados) {
			
			ArrayList<String> estadosDemais = new ArrayList<String>();
			
			x = x + estado.getNome() + " -> ";
			
			estadosDemais.add(estado.getNome());
			
			for (Transicao transicao : estado.getTransicoes()) {
				x = x + transicao.getSimbolo() + transicao.getEstadoDestino().getNome() + " | ";
				estadosDemais.add(transicao.getSimbolo() + transicao.getEstadoDestino().getNome());
			}
			
			if(estado.isEstFinal()){
				x = x +  "$";
				estadosDemais.add("$");
			}
			
			x = x + "\n";
			
		}
		
		System.out.println(x);
	
		return gr;
	}

}
