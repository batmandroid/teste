package controller;

import model.Automato;

public class Main {

	public static void main(String[] args) {
		
		Environment env = new Environment(); 
		env.start();
		
		ValidadorSentenca sentenca = new ValidadorSentenca();
		sentenca.enumerarSentencas(2, new Automato());
	}

}
