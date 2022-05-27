package package01;

import java.awt.Graphics2D;
//import java.awt.BasicStroke;
import java.awt.Rectangle;

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


class Marker extends Drawables{
	private int xLoc;
	private int yLoc;
	
	public Marker(int x, int y) {
		xLoc=x;
		yLoc=y;
	}
	
	
	public void paint(Graphics2D g2d) {
		//((Graphics2D) g2d).setStroke(new java.awt.BasicStroke(10));
	    g2d.setPaint(Color.red);
		g2d.fillRect((int)((xLoc+0.5)*Game.squareWidth()-(50/2)), (int)((yLoc+0.5)*Game.squareWidth()-(50/2)),50,50 );
		
				
	}
}

/**
 * The Board should only be added at the bottom though drawables.add(0);
 * @author ryanh
 *
 */
class Board extends Drawables{
	private ChessPiece[][] pieces;
	private ChessBase base;
	
	public Board(ChessPiece[][] pieceArray) {
		pieces=pieceArray;
		base=new ChessBase();
	}
	
	public Board() {
		base=new ChessBase();
		//okay put how we build the board here 
	}
	
	public boolean isNone(int x, int y){
		if((pieces[x][y].getPiece()).equals("none")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void paint(Graphics2D g2d) {
		base.paint(g2d);
		/*
		for(ChessPiece[] row: pieces) {
			for(ChessPiece p: row) {
				p.paint(g2d);
			}
		}
		*/
	}
	
	
	
	/**
	 * The board below the chess pieces, contains a matrix of squares
	 * @author ryanh
	 *
	 */
	class ChessBase extends Drawables{
		private ChessSquare[][] boardBottom;
		private Color color;
		
		public ChessBase(){
			boardBottom=new ChessSquare[8][8];
			for(int i=0;i<8;i++) {
				for(int j=0; j<8; j++) {
					boardBottom[i][j]=new ChessSquare(i,j);
				}
			}
			
			
			color= new Color(0,0,0);
		}
		public ChessBase(Color c) {
			boardBottom=new ChessSquare[8][8];
			color= c;
		}
		
		public void paint(Graphics2D g2d) {
			for(ChessSquare[] row: boardBottom) {
				for(ChessSquare p: row) {
					p.paint(g2d);
				}
			}
		}
		
		
		
		/**
		 * The individual squares on the board
		 * Can add highlighting later on
		 * @author ryanh
		 *
		 */
		class ChessSquare extends Drawables{
			private int xLoc;
			private int yLoc;
			private boolean colored;
			private Color coloredColor;
			
			//can add the highligted functionality later
			private boolean highlighted=false;
			private Color highlightedColor;
			
			public ChessSquare(int x, int y) {
				xLoc=x;
				yLoc=y;
				
				if((xLoc+yLoc)%2==1){
					colored=true;
				}
			}
			
			public void paint(Graphics2D g2d) {
				Color oldColor =g2d.getColor();
				if(colored) {
					g2d.setColor(coloredColor);
				}
				else {
					g2d.setColor(Color.WHITE);
				}
				g2d.fillRect(xLoc*Game.squareWidth(),yLoc*Game.squareHeight(),Game.squareWidth(),Game.squareHeight());
				g2d.setColor(oldColor);
			}
		}

	}
	
	
	
	/**
	 * The individual ChessPieces
	 * Is a superclass for all of the indvidual pieces 
	 * @author ryanh
	 *
	 */
	class ChessPiece extends Drawables{
		protected int xLoc;
		protected int yLoc;
		private String pieceName;
		protected String team;
		private String path;
		private Image img;
		
		public String getTeam() {
			return team;
		}
		
		public String getPiece() {
			return pieceName;
		}
		
		
		public ChessPiece(int x, int y, String name, String newTeam) {
			xLoc=x;
			yLoc=y;
			pieceName=name;
			team=newTeam;
			path = "src/pieceImages" + team + ".png";
			
			if(name.equals("none")) {
				img=null;
			}
			else {
				img = (new ImageIcon(path)).getImage();
			}
		}
		
		
		public boolean hasPiece(int x,int y) {
			if(pieces[x][y].getPiece().equals("none")) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean isSameTeam(int x, int y) {
			if(!isNone(x, y)) {
				if(team.equals(pieces[x][y].getTeam())) {
					return true;
				}
				else {
					return false;
				}
			}
			else {return false;}
		}
		public boolean isDifferentTeam(int x, int y) {
			if(!isNone(x, y)) {
				if(team.equals(pieces[x][y].getTeam())) {
					return false;
				}
				else {return true;}
			}
			else {return false;}
		}
		
		public boolean isValidMove(int x, int y) {
			return isSameTeam(x,y);
		}
	
		public void paint(Graphics2D g2d) {
			g2d.drawImage(img, xLoc, yLoc, Game);
		}
	
	}
	
	
	/**
	 * The class for spots with no Piece
	 * @author ryanh
	 *
	 */
	
	class noPiece extends ChessPiece{
		public noPiece(int x, int y) {
			super(x, y, "none", "none");
			// TODO Auto-generated constructor stub
		}

		public boolean isSameTeam(int x, int y) {
			return false;
		}
	}

	/**
	 * Pawn subclas, implement enPassant
	 * @author ryanh
	 *
	 */
	class Pawn extends ChessPiece{
		private boolean hasMoved =false;
		
		public Pawn(int x, int y, String team) {
			super(x, y, "pawn", team);
		}
		public boolean isValidMove(int x, int y) {
			
			//pawns can move 2 if they haven't moved before 
			if(!hasMoved) {
				if(team.equals("elf")) {
					if(!hasPiece(x,y-1) && !hasPiece(x,y-2)) {
						return true;
					}
				}
				if(team.equals("dwarf")) {
					if(!hasPiece(x,y+1) && !hasPiece(x,y+2)) {
						return true;
					}
				}
			}
			
			//normally can just more foward

			
			//attack sideways
			
			return false;
			
		}
	}
}