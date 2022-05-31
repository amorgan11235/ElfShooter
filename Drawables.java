package package01;

import java.awt.Graphics2D;
//import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Image;


/**
 * This class is the superclass to all of the things can be drawn
 * can probably be better done in an abstract class but idk
 * to draw something new just make sure it has the paint() method
 *
 */
public class Drawables {
	protected static GameSetupAndPlayer Game;
	
	public static void setGame(GameSetupAndPlayer newGame) {
		Game=newGame;
	}
	
	public void paint(Graphics2D g2d) {
		
	}
}

/**
 * 
 */
class Elf extends Drawables{
	private static int delay=250;
	private static boolean canShoot=true;
	
	public boolean canShoot() {
		return canShoot;
	}

	public void shoot(int x, int y) {
		ArrayList<Integer> removedDwarfs = new ArrayList<Integer>();
		ArrayList<Dwarf> dwarfList = Game.getDwarfList();
		
		for(int i=0;i<dwarfList.size();i++) {
			 
		}
		
	}
}


/**
 *
 */
class Dwarf extends Drawables{
	
}

/**
*
*/
class Background extends Drawables{
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, 0, Game.getWidth(), Game.getHeight());
		g2d.setColor(Color.GRAY);
		g2d.fillRect(Game.getWidth()-240, 0, Game.getWidth(), Game.getHeight());
		g2d.fillRect(0, 300, Game.getWidth(), 20);
	}
}
