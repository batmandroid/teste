package model;

import java.util.ArrayList;
import java.util.List;

public class Automato {
	
	private String nome;
	private String descricao;
	private Estado estadoInicial;
	private List<Estado> estados;
	
	public Automato(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
		estados = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Estado getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(Estado estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public void addEstado(Estado estado){
		estados.add(estado);
	}

}
