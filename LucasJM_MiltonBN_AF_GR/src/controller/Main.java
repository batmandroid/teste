package controller;

import java.util.ArrayList;
import java.util.List;

import model.Automato;
import model.Estado;
import model.Transicao;
import view.MainView;

public class Main {

	public static void main(String[] args) {
		
		MainView view = new MainView();
		view.setVisible(true);
		
		Automato automato = new Automato("GERA-A", "Qualquer sequencia de a");
		
		Estado q0 = new Estado("q0", true, false);
		Estado q1 = new Estado ("q1", false, true);
		
		Transicao t0 = new Transicao('a' ,q0);
		Transicao t1 = new Transicao('a' ,q1);
		
		List<Transicao> transicoesQ0 = new ArrayList<Transicao>();
		transicoesQ0.add(t0);
		transicoesQ0.add(t1);
	
		List<Transicao> transicoesQ1 = new ArrayList<Transicao>();
		transicoesQ1.add(t1);
		
		q0.setTransicoes(transicoesQ0);
		q1.setTransicoes(transicoesQ1);
		
		automato.addEstado(q0);
		automato.addEstado(q1);
		
		Controller controller = new Controller();
		
		List<Character> sentenca = new ArrayList<>();
		
		sentenca.add('a');
		sentenca.add('a');
		sentenca.add('a');
		sentenca.add('a');
		
		controller.validaSentenca(automato, sentenca);
		
		Automato novoAutomato = controller.determinizaAutomato(automato);
		
		controller.validaSentenca(novoAutomato, sentenca);
	
	}

}
