import java.awt.event.MouseListener;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


class RubiksNet extends JPanel implements Renderable {

	private static final int CUBELET_SIZE = 90;
	RubiksConfiguration state;
	List<RubikCubelet> cubelets = new ArrayList<RubikCubelet>();
	
	public RubiksNet(RubiksConfiguration state) {

		this.state = state;
		this.addMouseListener(mouseInput);
		this.setBackground(new Color(30, 30, 30));
		this.setLayout(null);
		this.repaint();
	}

	@Override
	public void render(Graphics2D graphics) {

	    // drawing top cubelet
	    cubelets.add(drawCubelet(graphics, 0, 290, 100));
	    cubelets.add(drawCubelet(graphics, 1, 380, 100));
	    cubelets.add(drawCubelet(graphics, 2, 380, 190));
	    cubelets.add(drawCubelet(graphics, 3, 290, 190));
        
	    // drawing left cubelet
	    cubelets.add(drawCubelet(graphics, 4, 100, 290));
	    cubelets.add(drawCubelet(graphics, 5, 190, 290));
	    cubelets.add(drawCubelet(graphics, 6, 190, 380));
	    cubelets.add(drawCubelet(graphics, 7, 100, 380));
        
        // drawing front cubelet
	    cubelets.add(drawCubelet(graphics, 8, 290, 290));
	    cubelets.add(drawCubelet(graphics, 9, 380, 290));
	    cubelets.add(drawCubelet(graphics, 10, 380, 380));
	    cubelets.add(drawCubelet(graphics, 11, 290, 380));
        
        // drawing right cubelet
	    cubelets.add(drawCubelet(graphics, 12, 480, 290));
	    cubelets.add(drawCubelet(graphics, 13, 570, 290));
	    cubelets.add(drawCubelet(graphics, 14, 570, 380));
	    cubelets.add(drawCubelet(graphics, 15, 480, 380));
        
        // drawing bottom cubelet
	    cubelets.add(drawCubelet(graphics, 16, 290, 480));
	    cubelets.add(drawCubelet(graphics, 17, 380, 480));
	    cubelets.add(drawCubelet(graphics, 18, 380, 570));
	    cubelets.add(drawCubelet(graphics, 19, 290, 570));
    
        // drawing back cubelet
	    cubelets.add(drawCubelet(graphics, 20, 670, 290));
	    cubelets.add(drawCubelet(graphics, 21, 760, 290));
	    cubelets.add(drawCubelet(graphics, 22, 760, 380));
	    cubelets.add(drawCubelet(graphics, 23, 670, 380));
	}
	
	private RubikCubelet drawCubelet(Graphics2D graphics, int idx, int X, int Y) {

		int color = -1;
	    char opts = state.positions[idx];

		switch(opts) {
				
		    case 'w':
				color = 0;
				graphics.setColor(Color.WHITE);
				break;

			case 'g':
				color = 1;
				graphics.setColor(Color.GREEN);
				break;

		    case 'o':
				color = 2;
				graphics.setColor(new Color(255, 125, 0));
				break;

		    case 'r':
				color = 3;
				graphics.setColor(Color.RED);
				break;

		    case 'y':
				color = 4;
				graphics.setColor(Color.YELLOW);
				break;

		    case 'b':
				color = 5;
				graphics.setColor(Color.BLUE);
				break;
		}
		
		graphics.fillRect(X, Y, CUBELET_SIZE, CUBELET_SIZE);
		graphics.setColor(Color.BLACK);
		graphics.setStroke(new BasicStroke(4));
		graphics.drawRect(X, Y, CUBELET_SIZE, CUBELET_SIZE);
		return new RubikCubelet(idx, X, Y, color);
    }
  
	MouseListener mouseInput = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent event) {

		    int X = event.getX();
		    int Y = event.getY();

		    for(RubikCubelet cubelet : cubelets) {

				if(X >= cubelet.netX && X <= cubelet.netX + CUBELET_SIZE && Y >= cubelet.netY && Y <= cubelet.netY + CUBELET_SIZE) {

					state.positions[cubelet.stateIndex] = cubelet.colorCycle();
					RubiksNet.this.repaint();
				}
		    }
        }

		@Override
		public void mousePressed(MouseEvent event)  { }

		@Override
		public void mouseReleased(MouseEvent event) { }

		@Override
		public void mouseEntered(MouseEvent event)  { }

		@Override
		public void mouseExited(MouseEvent event)   { }
	};

	@Override
	protected void paintComponent(Graphics graphics) {

		super.paintComponent(graphics);
		RubiksNet.this.render((Graphics2D) graphics);
	}
}


class RubikCubelet {

    char colors[] = {'w', 'g', 'o', 'r', 'y', 'b'};
    int stateIndex = 0;
    int netX, color, netY;
    
    public RubikCubelet(int stateIndex, int netX, int netY, int color) {

        this.stateIndex = stateIndex;
        this.netX = netX;
        this.netY = netY;
        this.color = color;
    }
    
    public char colorCycle() {

        color++; color %= 6;
        return colors[color];
    }
}
