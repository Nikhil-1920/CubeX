import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


class RubiksCubeUI extends JFrame {

	private RubiksNet rubiksnet = new RubiksNet(new RubiksConfiguration());
	private JButton   solveBtn  = new JButton("SOLVE");
    private JButton   resetBtn  = new JButton("RESET");
	private JButton   randomizeBtn = new JButton("RANDOM");
	private JLabel    solution = new JLabel(), time = new JLabel();
	private JLabel    scramble = new JLabel();
	
	public RubiksCubeUI() {

		this.setContentPane(rubiksnet);
		this.addComponents();
		this.buildFrames();
		this.setVisible(true);
	}
	
	private void buildFrames() {

		this.setTitle("CubeX");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(980, 800);
		this.setResizable(true);
		this.requestFocus();
	}
	
	private void addComponents() {

		solveBtn.setFont(new Font("SF Pro Display", Font.BOLD, 18));
        solveBtn.setForeground(new Color(255, 240, 245));
		solveBtn.setBackground(new Color(220, 20, 60));
		solveBtn.setBorder(new LineBorder(new Color(220, 20, 60).darker().darker(), 3));
		solveBtn.addMouseListener(solveBtnListener);
		solveBtn.setFocusable(false);
		solveBtn.setSize(100, 38);
		solveBtn.setLocation(480, 100);
        solveBtn.setOpaque(false);
      
		randomizeBtn.setFont(new Font("SF Pro Display", Font.BOLD, 18));
		randomizeBtn.setForeground(new Color(255, 240, 245));
		randomizeBtn.setBackground(new Color(220, 20, 60));
		randomizeBtn.setBorder(new LineBorder(new Color(220, 20, 60).darker().darker(), 3));
		randomizeBtn.addMouseListener(randomBtnListener);
		randomizeBtn.setFocusable(false);
		randomizeBtn.setSize(120, 38);
		randomizeBtn.setLocation(600, 100);
		randomizeBtn.setOpaque(false);
      
		resetBtn.setFont(new Font("SF Pro Display", Font.BOLD, 18));
        resetBtn.setForeground(new Color(255, 240, 245));
		resetBtn.setBackground(new Color(220, 20, 60));
		resetBtn.setBorder(new LineBorder(new Color(220, 20, 60).darker().darker(), 3));
		resetBtn.addMouseListener(resetBtnListener);
		resetBtn.setFocusable(false);
		resetBtn.setSize(100, 38);
		resetBtn.setLocation(740, 100);
		resetBtn.setOpaque(false);
      
		scramble.setFont(new Font("SF Pro Display", Font.BOLD, 18));
		scramble.setSize(500, 38);
		scramble.setForeground(new Color(255, 240, 245));
		scramble.setLocation(480, 160);

		solution.setFont(new Font("SF Pro Display", Font.BOLD, 18));
		solution.setSize(500, 38);
		solution.setForeground(new Color(255, 240, 245));
		solution.setLocation(480, 200);

		time.setFont(new Font("SF Pro Display", Font.BOLD, 18));
		time.setSize(500, 38);
		time.setForeground(new Color(255, 240, 245));
		time.setLocation(480, 240);
		
		rubiksnet.add(solveBtn);
		rubiksnet.add(randomizeBtn);
		rubiksnet.add(resetBtn);
		rubiksnet.add(scramble);
		rubiksnet.add(solution);
		rubiksnet.add(time);
	}
	
    MouseListener solveBtnListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent event) { }

		@Override
		public void mousePressed(MouseEvent event) { }

		@Override
		public void mouseReleased(MouseEvent event) {
        
		    long startTime = System.currentTimeMillis();
		    solution.setText("SOLUTION:  " + Main.solve(rubiksnet.state));
		    time.setText("TIME ELAPSED:  " + (System.currentTimeMillis() - startTime) + "ms.");
		    rubiksnet.state.moveInSequence(solution.getText());
		    rubiksnet.repaint();
        }

		@Override
		public void mouseEntered(MouseEvent event) {
		    solveBtn.setBackground(new Color(250, 128, 114));
		}

		@Override
		public void mouseExited(MouseEvent event) {
		    solveBtn.setBackground(new Color(220, 20, 60));
		}
	};
	
    MouseListener randomBtnListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent event) { }

		@Override
		public void mousePressed(MouseEvent event) { }

		@Override
		public void mouseReleased(MouseEvent event) {

		    scramble.setText("SCRAMBLE:  " + rubiksnet.state.randomize());
		    solution.setText("");
		    time.setText("");
		    rubiksnet.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		    randomizeBtn.setBackground(new Color(250, 128, 114));
		}

		@Override
		public void mouseExited(MouseEvent event) {
		    randomizeBtn.setBackground(new Color(220, 20, 60));
		}
    };

    MouseListener resetBtnListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent event) { }

		@Override
		public void mousePressed(MouseEvent event) { }

		@Override
		public void mouseReleased(MouseEvent event) {

		    rubiksnet.state = new RubiksConfiguration();
		    scramble.setText("");
		    solution.setText("");
		    time.setText("");
		    rubiksnet.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		    resetBtn.setBackground(new Color(250, 128, 114));
		}

		@Override
		public void mouseExited(MouseEvent event) {
		    resetBtn.setBackground(new Color(220, 20, 60));
		}
    };
}
