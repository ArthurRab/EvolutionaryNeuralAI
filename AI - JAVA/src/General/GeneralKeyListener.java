package General;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GeneralKeyListener implements KeyListener {

	public boolean skip = false;
	public boolean keepSkipping = false;

	@Override
	public void keyPressed(KeyEvent key) {

		if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (key.getKeyCode() == KeyEvent.VK_P) {
			Main.paused = !Main.paused;
		} else if (key.getKeyCode() == KeyEvent.VK_SPACE) {
			skip = true;
		} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			keepSkipping = !keepSkipping;
		}else if(key.getKeyCode() == KeyEvent.VK_S){
			Config.save=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub

	}

}
