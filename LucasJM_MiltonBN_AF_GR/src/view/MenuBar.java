package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;


public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MenuBar(MainView view) {
		JMenu menu;
		JMenuItem item;

		menu = new JMenu("Arquivo");
		item = new JMenuItem("Carregar AF");
		item.addActionListener(view);
		menu.add(item);
		item = new JMenuItem("Carregar GR");
		item.addActionListener(view);
		menu.add(item);
		add(menu);

		menu = new JMenu("Criar");
		item = new JMenuItem("Autômato Finito");
		item.setActionCommand(MenuOption.CRIAR_AF.name());
		item.addActionListener(view);
		menu.add(item);
		item = new JMenuItem("Gramática Regular");
		item.setActionCommand(MenuOption.CRIAR_GR.name());
		item.addActionListener(view);
		menu.add(item);
		add(menu);

		menu = new JMenu("Sistema");
		item = new JMenuItem("Sobre");
		item.setActionCommand(MenuOption.ABOUT.name());
		item.addActionListener(view);
		menu.add(item);
		menu.add(new JSeparator());
		item = new JMenuItem("Sair");
		item.setActionCommand(MenuOption.EXIT.name());
		item.addActionListener(view);
		menu.add(item);
		add(menu);
	}

}
