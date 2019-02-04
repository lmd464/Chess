package chess;

public class Bishop extends Piece {
	
	 public Bishop(String newColor, int x, int y) {

	        if( newColor.equals("Black") ) {
	        	this.setX(x);
	            this.setY(y);
	        }
	        else if( newColor.equals("White") ) {
	        	this.setX(x);
	            this.setY(y);
	        }
	        this.setColor(newColor);

	    }
	    
   public boolean movable(Piece[][] board, int newX, int newY) {
    	
    	int convertedRow = 7 - newY;
        int convertedCol = newX; //이동할 위치
        int convertedRow2 = 7 - this.y();
        int convertedCol2 = this.x(); //선택한 말의 위치
        
        int a,b;
        a = convertedRow-convertedRow2;
        b = convertedCol-convertedCol2;
        
        boolean bool = false;
        
        if((a == b)||(-a == b)) {
        	
        	bool = true;
        	
        	if (a == 0) {
        		bool = false;
        	}
        	else if((a == b)&&(a > 0)) {
        		for(int i = 1; i < a; i++) {
        			if(board[convertedRow2+i][convertedCol2+i] != null)
        				bool = false;
        		}
        	}
        	else if((a == b)&&(a < 0)) {
        		for(int i = 1; i < -a; i++) {
        			if(board[convertedRow2-i][convertedCol2-i] != null)
        				bool = false;
        		}
        	}
        	else if((-a == b)&&(a > 0)) {
        		for(int i = 1; i < a; i++) {
        			if(board[convertedRow2+i][convertedCol2-i] != null)
        				bool = false;
        		}
    		}	
        	else if((-a == b)&&(a < 0)) {
        		for(int i = 1; i < -a; i++) {
        			if(board[convertedRow2-i][convertedCol2+i] != null)
        				bool = false;
        		}
    		}	
        }
        
        return  (!this.friendlyInDestination(board, newX, newY) && !this.outOfRange(newX, newY) && bool);
        
    }
}