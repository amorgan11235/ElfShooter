package package01;

import java.awt.Graphics2D;
import java.awt.Component;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GameSetupAndPlayer extends JPanel{
    private final int GAME_WIDTH = 1040;
	private final int GAME_HEIGHT = 720;
	
	private ArrayList<Drawables> drawables= new ArrayList<Drawables>();
	private ArrayList<Dwarf> dwarfList= new ArrayList<Dwarf>();
	private JLabel statusbar;
	private Elf elf;
	
	//private JFrame testFrame =new JFrame("JFrame Color Example");
	
	public GameSetupAndPlayer(JLabel statusbar) {
	    this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.statusbar = statusbar;
        
        elf= new Elf();
        createCards();
    }

	//getters and Setters
	public int gameWidth() {
		return GAME_WIDTH;
	}
	public int gameHeight() {
		return GAME_HEIGHT;
	}

	public ArrayList<Dwarf> getDwarfList(){
		return dwarfList;
	}
	public void setDwarfList(ArrayList<Dwarf> newDwarf) {
		dwarfList=newDwarf;
	}
	//This contains the things that need to be set up before the game starts
	private void createCards() {
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		addMouseListener(new CardListener());
		Drawables.setGame(this);
        newGame();
	}
	
	
	private void newGame() {
		/*
		//draw all the nessary things
		int space=0;
		for(int i =0; i<=GAME_WIDTH-CARD_WIDTH-space; i=i+CARD_WIDTH+space) {

			drawables.add(new Card(i));
		}
		*/
		drawables.add(new Background());
		drawables.add(new Elf());
		drawables.add(new Dwarf());
		drawables.add(new Dwarf());
	}
	

	/**
	 * 
	 * okay this is a son of a female dog 
	 * 	This is what is executed whenever you want to paint something 
	 * Can not have multiple seperate methods for separate things
	 * Thus we go through the drawables arraylist and paint each one of those instead
	 * to modify what is painted modify that
	 */

	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);     
        
        Graphics2D g2d = (Graphics2D) g.create();
        for (Drawables drawable : drawables) {
            drawable.paint(g2d);
        }
        g2d.dispose();
        //drawables=new ArrayList<Drawables>();
	}


	//This handles the user interaction
	private class CardListener extends MouseAdapter{
		 public void mousePressed(MouseEvent e) {
			 int x = e.getX();
			 int y = e.getY();
			 
			 if(elf.canShoot()) {
				 elf.shoot(x,y);
					 
			 }
			 
			 repaint();
			 System.out.println("PRESSED");
		 }
	}
}
