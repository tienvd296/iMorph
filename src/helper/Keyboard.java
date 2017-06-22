package helper;

public class Keyboard {
	
	private static boolean ctrl = false;
	
	private static boolean maj = false;
	
	

	public static boolean isCtrl() {
		return ctrl;
	}

	public static void setCtrl() {
		Keyboard.ctrl = !Keyboard.ctrl;
		System.out.println(Keyboard.ctrl);
	}

	public static boolean isMaj() {
		return maj;
	}

	public static void setMaj(boolean maj) {
		Keyboard.maj = maj;
	}
	
	
}
