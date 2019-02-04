package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	static JButton[][] button = new JButton[8][8];
	static JButton[] pmbutton = new JButton[4];
	JPanel panel = new JPanel();
	JPanel panelA = new JPanel();
	JPanel panelB = new JPanel();
	Listener listener = new Listener();
	
	JLabel black = new JLabel("흑의 점수");
	JLabel white = new JLabel("백의 점수");
	public static JLabel message = new JLabel();
	static JLabel bs = new JLabel("");
	static JLabel ws = new JLabel("");
	
	public Frame() {
		setSize(480, 380);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chess");
		
		
		
		panel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
		panelA.setLayout(new GridLayout(8,8));
		
		
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
			button[i][j] = new JButton();
			panelA.add(button[i][j]);

			button[i][j].setBorder(null);
			button[i][j].setFont(new Font("",Font.PLAIN,40));
			button[i][j].setText("");
			button[i][j].setPreferredSize(new Dimension(40, 40));
			if((i-j)%2==0)
				button[i][j].setBackground(new Color(255, 206, 158));
			else
				button[i][j].setBackground(new Color(209, 139, 71));
			
			button[i][j].addActionListener(listener);
			}
		} //체스판 생성, 초기화
		
		
		panelB.setLayout(new BoxLayout(panelB, BoxLayout.Y_AXIS));
		
		
		
		black.setAlignmentX(CENTER_ALIGNMENT);
		white.setAlignmentX(CENTER_ALIGNMENT);
		bs.setAlignmentX(CENTER_ALIGNMENT);
		ws.setAlignmentX(CENTER_ALIGNMENT);
		message.setAlignmentX(CENTER_ALIGNMENT);
		
		panelB.add(black);
		panelB.add(bs);
		panelB.add(white);
		panelB.add(ws);
		bs.setText(String.valueOf(AppController.bs));
		ws.setText(String.valueOf(AppController.ws));
		 //점수판
		
		for(int i=0; i<4; i++) {

			pmbutton[i] = new JButton();
			panelB.add(pmbutton[i]);

			pmbutton[i].setBorder(null);
			pmbutton[i].setFont(new Font("",Font.PLAIN,40));
			pmbutton[i].setPreferredSize(new Dimension(40, 40));
			pmbutton[i].addActionListener(listener);
		}
		pmbutton[0].setText("♛");
		pmbutton[1].setText("♞");
		pmbutton[2].setText("♝");
		pmbutton[3].setText("♜");
		// 승급버튼
		panelB.add(message);
		
		printBoard();
		
		 //말 배치 출력
		
		
		
		
		panel.add(panelA);
		panel.add(panelB);
		add(panel);
		setVisible(true);
				
	}
	class Listener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if (e.getSource() == button[i][j]) {
						int convertedRow = 7 - i;
						int convertedCol = j;
						if((!AppController.st) && (AppController.getBoard(i,j) != null)) {
							AppController.setX(convertedCol);
							AppController.setY(convertedRow);
							button[i][j].setEnabled(false);
							AppController.st = true;
						}
						else if(AppController.st&&!AppController.ds){
							AppController.set_X(convertedCol);
							AppController.set_Y(convertedRow);
							button[7-AppController.getY()][AppController.getX()].setEnabled(true);
							AppController.ds = true;
						}
					}
				}				
			}
			if (e.getSource() == pmbutton[0]) {
				AppController.pmn = 1;
				AppController.pm = true;
			}
			else if (e.getSource() == pmbutton[1]) {
				AppController.pmn = 2;
				AppController.pm = true;
			}
			else if (e.getSource() == pmbutton[2]) {
				AppController.pmn = 3;
				AppController.pm = true;
			}
			else if (e.getSource() == pmbutton[3]) {
				AppController.pmn = 4;
				AppController.pm = true;
			}
			
		}			
	} // 버튼 클릭 반응 - 각 단계(움직일말 선택 or 목적지 선택)에 따라 해당위치를 변수에 받음
	public static void printBoard(){
		
		bs.setText(String.valueOf(AppController.bs));
		ws.setText(String.valueOf(AppController.ws));
		
		for(int i = 0; i < 8; i++) {
			 for(int j = 0; j < 8; j++) {
				 if(AppController.getBoard(i, j) == null) 
					 button[i][j].setText("");				 
				 else if(AppController.getBoard(i, j).getClass().getSimpleName().equals("Knight")) {
					 button[i][j].setText("♞");
					 if(AppController.getBoard(i, j).color().equals("White"))
						 button[i][j].setForeground(Color.WHITE);
					 else if(AppController.getBoard(i, j).color().equals("Black"))
						 button[i][j].setForeground(Color.BLACK);
				 }
				 else if(AppController.getBoard(i, j).getClass().getSimpleName().equals("Queen")) {
					 button[i][j].setText("♛");
					 if(AppController.getBoard(i, j).color().equals("White"))
						 button[i][j].setForeground(Color.WHITE);
					 else if(AppController.getBoard(i, j).color().equals("Black"))
						 button[i][j].setForeground(Color.BLACK);
				 }
				 else if(AppController.getBoard(i, j).getClass().getSimpleName().equals("Bishop")) {
					 button[i][j].setText("♝");
					 if(AppController.getBoard(i, j).color().equals("White"))
						 button[i][j].setForeground(Color.WHITE);
					 else if(AppController.getBoard(i, j).color().equals("Black"))
						 button[i][j].setForeground(Color.BLACK);
				 }
				 else if(AppController.getBoard(i, j).getClass().getSimpleName().equals("King")) {
					 button[i][j].setText("♚");
					 if(AppController.getBoard(i, j).color().equals("White"))
						 button[i][j].setForeground(Color.WHITE);
					 else if(AppController.getBoard(i, j).color().equals("Black"))
						 button[i][j].setForeground(Color.BLACK);
				 }
				 else if(AppController.getBoard(i, j).getClass().getSimpleName().equals("Rook")) {
					 button[i][j].setText("♜");
					 if(AppController.getBoard(i, j).color().equals("White"))
						 button[i][j].setForeground(Color.WHITE);
					 else if(AppController.getBoard(i, j).color().equals("Black"))
						 button[i][j].setForeground(Color.BLACK);
				 }
				 else if(AppController.getBoard(i, j).getClass().getSimpleName().equals("Pawn")) {
					 button[i][j].setText("♟");
					 if(AppController.getBoard(i, j).color().equals("White"))
						 button[i][j].setForeground(Color.WHITE);
					 else if(AppController.getBoard(i, j).color().equals("Black"))
						 button[i][j].setForeground(Color.BLACK);
				 }
			 }
		}
	}
}
		/* 
		 if(rod == 1) {
			 if(!this.isCheckmated("White")) {
				 AppView.outputLine("<<백의 순서입니다>>");	
			 }
		 }
		 else if(rod == 0) {
			if(!this.isCheckmated("Black")) {
				AppView.outputLine("<<흑의 순서입니다>>");	
			}
		 }
	 */
	
	