package controller;

import view.MainView;

public class Main {

	public static void main(String[] args) {

//		Automato automato = new Automato("GERA-A", "Qualquer sequencia de a");
//		
//		Estado q0 = new Estado("q0", true, false);
//		Estado q1 = new Estado ("q1", false, true);
//		
//		Transicao t0 = new Transicao('a' ,q1);
//		
//		List<Transicao> transicoes = new ArrayList<Transicao>();
//		transicoes.add(t0);
//		
//		q0.setTransicoes(transicoes);
//		q1.setTransicoes(transicoes);
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
		
		MainView view = new MainView();
		view.setVisible(true);
		
//		Automato novoAutomato = controller.determinizaAutomato(automato);
//		
//		if(automato.equals(novoAutomato)){
//			System.out.println("automato já determinizado");
//		} else {
//			System.out.println("Automato nao determinizado");
//		}
		
	}

}
