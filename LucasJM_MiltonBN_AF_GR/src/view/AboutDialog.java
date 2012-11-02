package view;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	JLabel title;
	JLabel separator;
	JLabel actors;
	JLabel actor1;
	JLabel actor2;
	JLabel date;
	JLabel version;

	public AboutDialog(MainView view) {
		super(view, "Sobre", true);
		setResizable(false);
		defineContent();
		positionContent(view);

		setVisible(true);
	}

	private void positionContent(MainView view) {
		JPanel p;

		p = new JPanel();
		p.setLayout(new GridLayout(7, 0, 5, 10));
		p.setAlignmentY(CENTER_ALIGNMENT);
		p.add(separator);
		p.add(actors);
		p.add(actor1);
		p.add(actor2);
		p.add(date);
		p.add(version);

		add(title, BorderLayout.NORTH);
		add(p, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(view);
	}

	private void defineContent() {
		title = new JLabel("Compiladores");
		title.setFont(new Font("VERDANA", Font.BOLD, 18));

		separator = new JLabel(" ");
		actors = new JLabel("Desenvolvido por:");
		actor1 = new JLabel("                 Lucas Just Meller");
		actor2 = new JLabel("                 Milton Bittencourt");
		date = new JLabel("Data: 2012/2");
		version = new JLabel("Vers�o : 1.0");
	}
}
