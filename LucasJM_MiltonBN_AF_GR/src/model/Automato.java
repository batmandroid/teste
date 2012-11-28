package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Automato implements Serializable {

	private static final long serialVersionUID = 4966556923674843702L;
	private String nome;
	private String descricao;
	private List<Estado> estados;

	public Automato(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
		estados = new ArrayList<Estado>();
	}

	public Automato() {
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

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void addEstado(Estado estado) {
		estados.add(estado);
	}

	public Estado getEstadoInicial() {
		for (Estado estado : estados) {
			if (estado.isInicial()) {
				return estado;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((estados == null) ? 0 : estados.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Automato other = (Automato) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (estados == null) {
			if (other.estados != null)
				return false;
		} else if (!estados.equals(other.estados))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {

		String x = "";

		try {
			for (Estado estado : estados) {
				x = x + "Nome: " + estado.getNome() + "\n";
				for (Transicao trans : estado.getTransicoes()) {
					x = x + "Simbolo: " + trans.getSimbolo();
					x = x + " destino: " + trans.getEstadoDestino().getNome()
							+ "\n";
				}
			}
		} catch (Exception e) {
			System.out.println(x);

			System.out.println("caiu no catch");
			e.printStackTrace();
		}

		return x;
	}

	public List<Estado> getEstadosFinais() {
		List<Estado> finais = new ArrayList<Estado>();
		for (Estado estado : estados) {
			if (estado.isEstFinal()) {
				finais.add(estado);
			}
		}
		return finais;
	}

	public List<Estado> getEstadosNaoFinais() {
		List<Estado> naoFinais = new ArrayList<Estado>();
		for (Estado estado : estados) {
			if (!estado.isEstFinal()) {
				naoFinais.add(estado);
			}
		}
		return naoFinais;
	}

	public List<Character> getAllSimbolos() {
		List<Character> list = new ArrayList<Character>();
		for (Estado estado : estados) {
			List<Transicao> transicoes = estado.getTransicoes();
			for (Transicao transicao : transicoes) {
				if (!list.contains(transicao.getSimbolo())) {
					list.add(transicao.getSimbolo());
				}
			}
		}
		return list;
	}

	public List<Transicao> getTransicoes() {
		List<Transicao> list = new ArrayList<Transicao>();
		for (Estado estado : estados) {
			list.addAll(estado.getTransicoes());
		}
		return list;
	}
	
	public boolean isAutomatoDeterministico() {
		for (Estado estado : estados) {
			if (estado.isNaoDeterminismo()) {
				return false;
			}
		}
		return true;
	}

}
