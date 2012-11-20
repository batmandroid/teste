package controller;

import view.MainView;

public class Environment {

	Controller controller;
	
	public void start() {

		controller = new Controller();
		
		MainView view = new MainView(controller);
		view.setVisible(true);
		
	}

	
	
}
