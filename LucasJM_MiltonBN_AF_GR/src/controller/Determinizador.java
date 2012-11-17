package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Determinizador {
	
	public Automato determinizaAutomato(Automato automato){
		
		Automato determinizado = new Automato(automato.getNome(), automato.getDescricao());
		List<List<Estado>> novosEstados = new ArrayList<>();
		List<Estado> allEstados = determinizado.getEstados();
		
		for (Estado estado : allEstados) {

			List<Transicao> transicoes = estado.getTransicoes();
			
			for (int i = 0; i < transicoes.size(); i++) {
				
				Transicao testada = transicoes.get(i);
				
				for (int j = 0; j < transicoes.size(); j++) {
					
					Transicao comparada = transicoes.get(j);
					
					if(!testada.equals(comparada)){
						
						if(testada.getSimbolo().equals(comparada.getSimbolo())){
							
						}
						
					}
				}
				
				
				
			}
		}
		return determinizado;
	}

}
