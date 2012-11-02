package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5597128573950583380L;

	JDialog aboutDialog;
	
	public MainView(){
		super("Compilando maneiro djow");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel painel = new JPanel();
		add(painel);
		
        setResizable(false);
        setLocationRelativeTo(null);
        setJMenuBar(new MenuBar(this));
        setPreferredSize(new Dimension(600, 450));
        pack();
        setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
        MenuOption option = MenuOption.valueOf(e.getActionCommand());

        switch (option) {
            case EXIT:
                System.exit(0);
            case ABOUT:
            	aboutDialog = new AboutDialog(this);
    			break;                
        }
	}
	
}
