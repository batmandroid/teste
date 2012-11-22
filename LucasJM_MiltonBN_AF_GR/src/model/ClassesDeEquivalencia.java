package model;

import java.util.ArrayList;
import java.util.List;

import model.Estado;
import model.Transicao;

public class ClassesDeEquivalencia {
	private List<List<Estado>> classes = new ArrayList<List<Estado>>();
	private Automato automato;

	public ClassesDeEquivalencia(Automato _automato) {
		automato = _automato;
	}

	public List<List<Estado>> separarClasses() {
		List<Estado> estadosFinaisTemp = new ArrayList<Estado>();
		estadosFinaisTemp.addAll(automato.getEstadosFinais());

		List<Estado> estadosNaoFinaisTemp = new ArrayList<Estado>();
		estadosNaoFinaisTemp.addAll(automato.getEstadosNaoFinais());

		List<List<Estado>> classesTemp = new ArrayList<List<Estado>>();
		classesTemp.add(estadosFinaisTemp);
		classesTemp.add(estadosNaoFinaisTemp);

		boolean mudouAlgumEstado = true;
		while (mudouAlgumEstado) {
			classes.clear();
			classes.addAll(classesTemp);
			classesTemp.clear();
			mudouAlgumEstado = false;
			for (List<Estado> classe : classes) {

				boolean separou = separarEstados(classe, classesTemp);
				if (separou) {
					mudouAlgumEstado = true;
				}
			}
		}

		return this.classes;

	}

	private boolean separarEstados(List<Estado> classe,
			List<List<Estado>> classesTemp) {
		List<List<Estado>> classesNovas = new ArrayList<List<Estado>>();

		for (Estado estado : classe) {

			boolean pertenceAClasseExistente = false;
			for (List<Estado> classeNova : classesNovas) {
				boolean pertencemAMesmaClasse = saoEquivalentes(estado,
						classeNova.iterator().next());
				if (pertencemAMesmaClasse) {

					classeNova.add(estado);
					pertenceAClasseExistente = true;
				}
			}
			if (!pertenceAClasseExistente) {
				List<Estado> classeNova = new ArrayList<Estado>();

				classeNova.add(estado);
				classesNovas.add(classeNova);
			}
		}
		classesTemp.addAll(classesNovas);
		return classesNovas.size() > 1;
	}

	private boolean saoEquivalentes(Estado estado1, Estado estado2) {
		for (Character simbolo : automato.getAllSimbolos()) {
			Estado estado3 = estado1.getDestinosBySimbolo(simbolo).get(0);
			Estado estado4 = estado2.getDestinosBySimbolo(simbolo).get(0);
			if (!estaoNaMesmaClasse(estado3, estado4)) {
				return false;
			}
		}
		return true;
	}

	private boolean estaoNaMesmaClasse(Estado estado3, Estado estado4) {

		for (List<Estado> classe : classes) {
			if (classe.contains(estado3) && classe.contains(estado4)) {
				return true;
			}
		}

		return false;
	}

	public Estado obterEstadoDaTransicao(Estado estado1, Character character) {
		
		for (Transicao transicao : estado1.getTransicoes()) {
			if(transicao.getSimbolo().equals(character)){
				return transicao.getEstadoDestino();
			}
		}

		return null;
	}

	public Estado pegaEstado(String nome) {
		for (Estado e : automato.getEstados()) {
			if (e.getNome().equals(nome)) {
				return e;
			}

		}
		return null;
	}
}
