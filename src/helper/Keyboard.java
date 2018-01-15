package helper;

import facade.Facade;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keyboard extends Parent{

	private static boolean ctrl = false;
	

	private static boolean maj = false;

	public Keyboard(Scene scene)
	{
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent ke){
					if(ke.getCode() == KeyCode.CONTROL)
					{
						ctrl = true;
					}
					else if(ke.getCode() == KeyCode.SHIFT)
					{
						maj = true;
					}
					else if(ke.getCode() == KeyCode.S && ctrl == true && maj == false)
					{
						Facade.saveProject();
					}
					else if(ke.getCode() == KeyCode.S && ctrl == true && maj == true)
					{
						Facade.activeView.saveAsProject();
					}
					else if(ke.getCode() == KeyCode.Z && ctrl == true && maj == false)
					{
						Facade.undo();
						Facade.activeView.refresh();
					}
					else if(ke.getCode() == KeyCode.Z && ctrl == true && maj == true)
					{
						Facade.redo();
						Facade.activeView.refresh();
					}
					else if(ke.getCode() == KeyCode.Y && ctrl == true && maj == false)
					{
						Facade.redo();
						Facade.activeView.refresh();
					}
					
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent ke){
				if(ke.getCode() == KeyCode.CONTROL)
				{
					ctrl = false;
				}
				else if(ke.getCode() == KeyCode.SHIFT)
				{
					maj = false;
				}
					
			}
		});
		
		
	}

	public static boolean isCtrl() {
		return ctrl;
	}

	public static boolean isMaj() {
		return maj;
	}

	public static void setMaj(boolean maj) {
		Keyboard.maj = maj;
	}



}
