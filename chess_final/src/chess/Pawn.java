package chess;
import app.AppController;
import app.Frame;



public class Pawn extends Piece{

	public Pawn(String newColor,int x, int y) {
		if(newColor.equals("Black")) {
			this.setX(x);
			this.setY(y);
		}
		else if(newColor.equals("White")) {
			this.setX(x);
			this.setY(y);
		}
		else
			System.out.println("[i] 폰 생성 실패. 매개 변수 확인"); //AppView
		
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
        //이동할 위치에 아군 말이 없고 상대 말이 있을 경우 이동 가능
        //아니면 앞으로 1칸 이동할 경우
        //그렇지 않으면 false
		boolean bool = false;
		
		if(color().equals("Black")) {
			if((this.x()>=0||this.x()<8)&&this.y()==6) {	//폰이 시작일 때 1칸 또는 2칸 이동 가능
				if ((a==1)&&(b==0)||(a==2)&&(b==0)) {
					bool = true;
				}
			}
			if ((a==1)&&(b==0)) { //한칸 앞으로 이동
				bool = true;
				//앞에 말이 있을 경우 이동 불가
				if (board[convertedRow][convertedCol] !=null) {
					bool = false;
				}
			}
			
			if ((a==1)&&((b==1)||(b==-1))) { //상대의 말을 잡을 경우
				if((board[convertedRow][convertedCol] !=null)&&!friendlyInDestination(board,newX,newY)) {
					bool = true;
				}
			}
		}
		
		if(color().equals("White")) {
			if((this.x()>=0||this.x()<8)&&this.y()==1) {	//폰이 시작일 때 1칸 또는 2칸 이동 가능
				if ((a==-1)&&(b==0)||(a==-2)&&(b==0)) {
					bool = true;
				}
			}
			if ((a==-1)&&(b==0)) { //한칸 앞으로 이동
				bool = true;
				//앞에 말이 있을 경우 이동 불가
				if (board[convertedRow][convertedCol] !=null) {
					bool = false;
				}
			}
			
			if ((a==-1)&&((b==1)||(b==-1))) { //상대의 말을 잡을 경우
				if((board[convertedRow][convertedCol] !=null)&&!friendlyInDestination(board,newX,newY)) {
					bool = true;
				}
			}
		}
		return (!outOfRange(newX,newY) && bool);
	}
	@Override
	public void move(Piece[][] board, int newX, int newY) {

		// 보드에서의 위치 갱신 ~ 이동할 위치에 대입
		int convertedRow = 7 - newY;
		int convertedCol = newX;
		board[convertedRow][convertedCol] = this;
		
		// 보드에서의 위치 갱신 ~ 기존 위치 삭제
		convertedRow = 7 - this.y();
		convertedCol = this.x();
		board[convertedRow][convertedCol] = null;
		
		// 말 내부의 필드 갱신
		this.setX(newX);
		this.setY(newY);
		
		Frame.printBoard();
		
		promote(board, newX, newY);
		
	}
	
	
	//끝에 도달할 시 승급 or 5점 이상 존재시 폰을 이동하며 승급
	public void promote(Piece[][] board, int newX, int newY) { 
		int convertedRow = 7 - newY;
        int convertedCol = newX;
        
        if(((this.color().equals("White")&&(AppController.ws >= 5)) || (this.color().equals("Black")&&(AppController.bs >= 5)))
        		|| (convertedRow == 7 || convertedRow ==0)) { // 보유 점수가 5점 이상이거나, 보드의 끝으로 이동시
        	Frame.message.setText("승급할 말을 선택");
			while (board[convertedRow][convertedCol].getClass().getSimpleName().equals("Pawn")) {
				while(AppController.pm) {
					board[convertedRow][convertedCol] =null; // 해당 위치 폰 초기화
		
					
					switch(AppController.pmn) {
					case 1:	
						board[convertedRow][convertedCol] = new Queen(this.color(),newX,newY);
						break;
					case 2:
						board[convertedRow][convertedCol] = new Knight(this.color(),newX,newY);
						break;
					case 3:
						board[convertedRow][convertedCol] = new Bishop(this.color(),newX,newY);
						break;
					case 4:
						board[convertedRow][convertedCol] = new Rook(this.color(),newX,newY);
						break;
					}// 승급 선택지
					AppController.pm = false;
					if((this.color().equals("White")&&(AppController.ws >= 5)) && !(convertedRow == 7 || convertedRow ==0))
						AppController.ws -= 5;
					else if((this.color().equals("Black")&&(AppController.bs >= 5)) && !(convertedRow == 7 || convertedRow ==0))
						AppController.bs -= 5; // 점수로 승급시 점수 차감
				}
			}
		}
	}
}
