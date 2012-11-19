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
		
//		Automato automato = new Automato("GERA-A", "Qualquer sequencia de a");
//		
//		Estado q0 = new Estado("q0", true, false);
//		Estado q1 = new Estado ("q1", false, true);
//		
//		Transicao t0 = new Transicao('a' ,q0);
//		Transicao t1 = new Transicao('a' ,q1);
//		
//		List<Transicao> transicoesQ0 = new ArrayList<Transicao>();
//		transicoesQ0.add(t0);
//		transicoesQ0.add(t1);
//	
//		List<Transicao> transicoesQ1 = new ArrayList<Transicao>();
//		transicoesQ1.add(t1);
//		
//		q0.setTransicoes(transicoesQ0);
//		q1.setTransicoes(transicoesQ1);
//		
//		automato.addEstado(q0);
//		automato.addEstado(q1);
//		
//		Controller controller = new Controller();
//		
//		List<Character> sentenca = new ArrayList<>();
//		
//		sentenca.add('a');
//		sentenca.add('a');
//		sentenca.add('a');
//		sentenca.add('a');
//		
//		controller.validaSentenca(automato, sentenca);
//		
//		Automato novoAutomato = controller.determinizaAutomato(automato);
//		
//		controller.validaSentenca(novoAutomato, sentenca);

		Automato automato = new Automato("GERA (a,b) * abb: ", "qualquer sequencia de a,b  terminado em abb");
		
		Estado q0 = new Estado("q0", true, false);
		Estado q1 = new Estado ("q1", false, false);
		Estado q2 = new Estado ("q2", false, false);
		Estado q3 = new Estado ("q3", false, true);
		
		Transicao t0 = new Transicao('a' ,q0);
		Transicao t1 = new Transicao('a' ,q1);
		Transicao t2 = new Transicao('b' ,q0);
		Transicao t3 = new Transicao('b' ,q2);
		Transicao t4 = new Transicao('b' ,q3);
		
		List<Transicao> transicoesQ0 = new ArrayList<Transicao>();
		transicoesQ0.add(t0);
		transicoesQ0.add(t1);
		transicoesQ0.add(t2);
	
		List<Transicao> transicoesQ1 = new ArrayList<Transicao>();
		transicoesQ1.add(t3);
		
		List<Transicao> transicoesQ2 = new ArrayList<Transicao>();
		transicoesQ2.add(t4);
		
		q0.setTransicoes(transicoesQ0);
		q1.setTransicoes(transicoesQ1);
		q2.setTransicoes(transicoesQ2);
		
		automato.addEstado(q0);
		automato.addEstado(q1);
		automato.addEstado(q2);
		automato.addEstado(q3);
		
		Controller controller = new Controller();
		
		List<Character> sentenca = new ArrayList<>();
		
		sentenca.add('a');
		sentenca.add('b');
		sentenca.add('a');
		sentenca.add('a');
		sentenca.add('b');
		sentenca.add('b');
		
		controller.validaSentenca(automato, sentenca);
		
		Automato novoAutomato = controller.determinizaAutomato(automato);
		
		List<Estado> est = novoAutomato.getEstados();
		
		for (Estado estado : est) {
			System.out.println("ext: " + estado.getNome() + "  "  + estado.getTransicoes());
		}
		
		controller.validaSentenca(novoAutomato, sentenca);
		
	}

}
