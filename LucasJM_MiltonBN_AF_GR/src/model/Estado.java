package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Estado implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1595232800234656840L;
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
	
	public boolean isNaoDeterminismo(){
		for (Transicao transicao : transicoes) {
			for (Transicao transicaoComparada : transicoes) {
				if(!transicao.equals(transicaoComparada)){
					if(transicao.getSimbolo().equals(transicaoComparada.getSimbolo())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Set<Character> getAllSimbolos() {
		Set<Character> simbolos = new HashSet<Character>();
		for (Transicao transicao : this.getTransicoes()) {
			simbolos.add(transicao.getSimbolo());
		}
		return simbolos;
	}

	public boolean isTransicaoDeterministica(Character character) {
		
		if(getDestinosBySimbolo(character).size() > 1){
			return false;
		}
		
		return true;
	}
	
	public List<Estado> getDestinosBySimbolo(Character character){
		List<Estado> destinos = new ArrayList<Estado>();
		
		for (Transicao transicao : transicoes) {
			if(transicao.getSimbolo().equals(character)){
				destinos.add(transicao.getEstadoDestino());
			}
		}
		return destinos;
	}
	
	public Transicao getTransicaoBySimbolo(Character character){
		for (Transicao transicao : transicoes) {
			if(transicao.getSimbolo().equals(character)){
				return transicao;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Estado [nome=" + nome + ", inicial=" + inicial + ", estFinal=" + estFinal +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (estFinal ? 1231 : 1237);
		result = prime * result + (inicial ? 1231 : 1237);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((transicoes == null) ? 0 : transicoes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (estFinal != other.estFinal)
			return false;
		if (inicial != other.inicial)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public void addTransicao(Transicao transicao) {
		if (transicoes == null) {
			transicoes = new ArrayList<Transicao>();
		}
		transicoes.add(transicao);
	}
	
}
