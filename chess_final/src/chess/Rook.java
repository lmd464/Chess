package chess;

public class Rook extends Piece{


	public Rook(String newColor,int x, int y) {
		if(newColor.equals("Black")) {
			 this.setX(x);
	         this.setY(y);
		}
		else if(newColor.equals("White")) {
			 this.setX(x);
	         this.setY(y);
		}
		this.setColor(newColor);
	}
	
	@Override
	public boolean movable(Piece[][] board,int newX, int newY) {
		int convertedRow =7 - newY;
		int convertedCol = newX;
        int convertedRow2 = 7 - this.y();
        int convertedCol2 = this.x(); 
		
        int a,b;
        a = convertedRow-convertedRow2;
        b = convertedCol-convertedCol2;
        
        boolean bool = false;
        
       
        if (a==0&& b !=0) { //세로로 이동
        	bool = true;
        	//장애물 체크
        	if (b > 0) {
        		for (int i = 1; i < b; i++) {
        			if ((board[convertedRow2][convertedCol2+i] != null)) {
        				bool = false;
        			}
        		}
        	}
        	else if (b < 0) {
        		for (int i = 1; i < -b; i++) {
        			if (board[convertedRow2][convertedCol2-i] != null) {
        				bool = false;
        			}
        		}
        	}
        }
        
        if (a!=0&&b==0) { //가로로 이동
        	bool = true;
        	//장애물 체크
        	if (a>0) {
        		for (int i = 1; i < a; i++) {
        			if (board[convertedRow2+i][convertedCol2] != null) {
        				bool = false;
        			}
        		}
        	}
        	else if (a<0) {
        		for (int i = 1; i < -a; i++) {
        			if (board[convertedRow2-i][convertedCol2] != null) {
        				bool = false;
        			}
        		}
        	}
        }
        
        return  (!friendlyInDestination(board, newX, newY) && !outOfRange(newX, newY) && bool);
	}
}
