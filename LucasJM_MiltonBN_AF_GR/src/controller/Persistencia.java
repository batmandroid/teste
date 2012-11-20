package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import model.Automato;
import model.Estado;
import model.Transicao;

public class Persistencia {

	public void escrever(File caminho) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(caminho);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.flush();
		oos.close();
		fos.flush();
		fos.close();
	}

	public Automato lerAutomato(File caminho) {
		try {
			FileInputStream fin = new FileInputStream(caminho);
			ObjectInputStream ob = new ObjectInputStream(fin);
			Automato aux = (Automato) ob.readObject();
			return aux;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new Automato();
	}

	public Automato carregar(JPanel panel) {
		File caminho = new File(".");
		JFileChooser fc = new JFileChooser(caminho.getAbsolutePath() + "/src/arquivos");
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.toString().endsWith(".aut") || f.isDirectory())
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return "Arquivos de automato (.aut)";
			}

		});
		int ret = fc.showOpenDialog(panel);
		if (ret == JFileChooser.APPROVE_OPTION) {
			caminho = fc.getSelectedFile();

			return lerAutomato(caminho);
		}
		return new Automato();
	}

	public void escreverObjeto(File caminho, Automato automato) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(caminho);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(automato);
		oos.flush();
		oos.close();
		fos.flush();
		fos.close();
	}

	public void salvar(JPanel panel, File caminho, Automato automato) {
		if (caminho == null)
			salvarComo(panel, automato);
		else
			try {
				escreverObjeto(caminho, automato);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void salvarComo(JPanel panel, Automato automato) {
		File caminho = new File(".");
		JFileChooser fc = new JFileChooser(caminho.getAbsolutePath() + "/src/arquivos");

		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.toString().endsWith(".aut") || f.isDirectory())
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return "Arquivos de automato (.aut)";
			}

		});
		int ret = fc.showSaveDialog(panel);
		if (ret == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			String sf = f.toString();
			if (!sf.endsWith(".aut"))
				sf = sf.concat(".aut");
			caminho = new File(sf);
			try {
				escreverObjeto(caminho, automato);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
