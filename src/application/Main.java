package application;

public class Main {

	public static void main(String[] args) {


		ControlHome CM = new ControlHome();
		ControlDashboard CD = new ControlDashboard();
		CM.newProject("WUP");
		CD.saveProject();

	}

}
