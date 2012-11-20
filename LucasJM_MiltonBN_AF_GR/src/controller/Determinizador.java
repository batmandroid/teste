package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Determinizador {

	List<Estado> estadosGerados;
	List<Estado> estadosTratados;

	Map<Estado, List<Estado>> recursividade = new HashMap<Estado, List<Estado>>();

	public Determinizador() {
		estadosGerados = new ArrayList<Estado>();
	}

	public Automato determinizaAutomato(Automato automato) {

		Automato determinizado = new Automato(automato.getNome(), automato.getDescricao());
		List<Estado> allEstados = automato.getEstados();

		if (isAutomatoDeterministico(automato)) {
			return automato;
		}

		for (Estado estado : allEstados) {
			if (estado.isNaoDeterminismo()) {
				Estado gerado = geraEstadoDeterministico(estado);
				estadosGerados.add(gerado);
			} else {
				estadosGerados.add(estado);
			}
		}

		boolean alldone = true;
		while (alldone) {
			alldone = false;
			List<Estado> alldones = new ArrayList<Estado>();
			alldones.addAll(estadosGerados);
			for (Estado estadox : alldones) {
				if (estadox.isNaoDeterminismo()) {
					Estado estadoy = geraEstadoDeterministico(estadox);
					if (estadox.equals(estadoy)) {
						estadox.setTransicoes(estadoy.getTransicoes());
					}
					alldone = true;
					break;
				}
			}
		}

		determinizado.setEstados(estadosGerados);

		return determinizado;
	}

	private Estado geraEstadoDeterministico(final Estado estado) {
		Estado novoEstado = new Estado("", false, false);
		Set<Character> simbolos = estado.getAllSimbolos();
		List<Transicao> transicoes = new ArrayList<Transicao>();

		for (Character character : simbolos) {

			if (estado.isTransicaoDeterministica(character)) {
				Transicao tx = estado.getTransicaoBySimbolo(character);
				if (tx != null) {
					transicoes.add(tx);
				}
			} else {

				List<Estado> destinos = estado.getDestinosBySimbolo(character);
				Estado gerado2 = geraTransicaoBydestinos(character, destinos);

				Transicao ty = null;

				if (transicaoInexistente(gerado2)) {
					estadosGerados.add(gerado2);
					ty = new Transicao(character, gerado2);
				} else {
					ty = new Transicao(character, getTransicaoIgual(gerado2));
				}

				transicoes.add(ty);
			}
		}

		novoEstado.setTransicoes(transicoes);
		novoEstado.setNome(estado.getNome());
		novoEstado.setEstFinal(estado.isEstFinal());
		novoEstado.setInicial(estado.isInicial());

		return novoEstado;
	}

	private Estado getTransicaoIgual(Estado gerado2) {
		for (Estado estado : estadosGerados) {
			if (estado.equals(gerado2)) {
				return estado;
			}
		}
		return gerado2;
	}

	private boolean transicaoInexistente(Estado x) {

		for (Estado estado : estadosGerados) {
			if (estado.equals(x)) {
				return false;
			}
		}

		return true;
	}

	private Estado geraTransicaoBydestinos(Character character, List<Estado> estadoPai) {

		Estado estado = new Estado(geraNomes(estadoPai), false, verificaFinal(estadoPai));

		List<Transicao> transicoes = new ArrayList<Transicao>();

		for (Estado pai : estadoPai) {
			List<Transicao> tpai = pai.getTransicoes();
			for (Transicao transicao : tpai) {
				if (!transicoes.contains(transicao)) {
					transicoes.add(transicao);
				}
			}
		}

		estado.setTransicoes(transicoes);

		return estado;
	}

	private boolean verificaFinal(List<Estado> destinos) {
		boolean isFinal = false;

		for (Estado estado : destinos) {
			if (estado.isEstFinal()) {
				isFinal = true;
				break;
			}
		}

		return isFinal;
	}

	public String geraNomes(List<Estado> destinos) {
		String nome = "";
		for (Estado estado : destinos) {
			nome += estado.getNome();
		}
		return nome;
	}

	private boolean isAutomatoDeterministico(Automato automato) {
		for (Estado estado : automato.getEstados()) {
			if (estado.isNaoDeterminismo()) {
				return false;
			}
		}
		return true;
	}

}
