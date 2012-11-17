package model;

import java.util.ArrayList;
import java.util.List;

public class Estado {
	
    private String nome;
    private boolean inicial;
    private boolean estFinal;
    private List<Transicao> transicoes;
    
    public Estado( String nome, boolean inicial, boolean estFinal ) {
        this.nome = nome;
        this.inicial = inicial;
        this.estFinal = estFinal;
        transicoes = new ArrayList< Transicao >();
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isInicial() {
		return inicial;
	}

	public void setInicial(boolean inicial) {
		this.inicial = inicial;
	}

	public boolean isEstFinal() {
		return estFinal;
	}

	public void setEstFinal(boolean estFinal) {
		this.estFinal = estFinal;
	}

	public List<Transicao> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(List<Transicao> transicoes2) {
		this.transicoes = transicoes2;
	}

}
