package app;

import chess.Queen;
import chess.Piece;
import chess.Bishop;
import chess.King;
import chess.Knight;
import chess.Rook;
import chess.Pawn;

public class AppController {

	private static final int SIZE_OF_BOARD = 8;
	public static boolean st = false;
	public static boolean ds = false; // 이동할 말이 선택되었는지 유무 -> st / 도착지가 선택되었는지 유무 -> ds
	public static boolean pm = false; // 승급 유무, 승급시 활성화
	public static int pmn = 0;// 승급 선택 1.퀸 2.나이트 3.비숍 4.룩
	public static int bs, ws = 0; // 흑, 백의 스코어
	public static boolean checkmated = false;
	private static Piece[][] _piece;
	private static int x, y, _x, _y; // 현재 좌표, 이동할 좌표
	

	public static void setX(int newX) {
		x = newX;
	}

	public static void setY(int newY) {
		y = newY;
	}

	public static void set_X(int newX) {
		_x = newX;
	}

	public static void set_Y(int newY) {
		_y = newY;
	}

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public static int get_X() {
		return _x;
	}

	public static int get_Y() {
		return _y;
	}

	private static Piece[][] board() {
		return _piece;
	}

	public void setBoard(Piece[][] newPiece) {
		_piece = newPiece;
	}

	public static Piece getBoard(int i, int j) {
		return board()[i][j];
	}
	public static Piece[][] temp = new Piece[8][8]; // 이전상태 저장용 보드
	
	public AppController() { // 생성자를 통해 말 초기화

		// Piece[][] board = new Piece[this.SIZE_OF_BOARD][this.SIZE_OF_BOARD];
		this.setBoard(new Piece[SIZE_OF_BOARD][SIZE_OF_BOARD]);

		// board 초기화
		for (int row = 0; row < SIZE_OF_BOARD; row++) {
			for (int col = 0; col < SIZE_OF_BOARD; col++)
				board()[row][col] = null;
		}

		// Pawn 배치
		for (int col = 0; col < SIZE_OF_BOARD; col++) {
			int whiteRow = 6;
			int blackRow = 1;
			board()[whiteRow][col] = new Pawn("White", col, 7 - whiteRow);
			board()[blackRow][col] = new Pawn("Black", col, 7 - blackRow);
		}

		// King 배치 : 플레이어가 White 라 가정
		board()[7][4] = new King("White");
		board()[0][4] = new King("Black");

		// Rook 배치
		board()[7][0] = new Rook("White", 0, 0);
		board()[7][7] = new Rook("White", 7, 0);
		board()[0][0] = new Rook("Black", 0, 7);
		board()[0][7] = new Rook("Black", 7, 7);

		// Knight 배치
		board()[7][1] = new Knight("White", 1, 0);
		board()[7][6] = new Knight("White", 6, 0);
		board()[0][1] = new Knight("Black", 1, 7);
		board()[0][6] = new Knight("Black", 6, 7);

		// Bishop 배치
		board()[7][2] = new Bishop("White", 2, 0);
		board()[7][5] = new Bishop("White", 5, 0);
		board()[0][2] = new Bishop("Black", 2, 7);
		board()[0][5] = new Bishop("Black", 5, 7);

		// Queen 배치
		board()[7][3] = new Queen("White", 3, 0);
		board()[0][3] = new Queen("Black", 3, 7);

		@SuppressWarnings("unused")
		Frame f = new Frame();

	}
	

	private void turn() {

		int numberOfTurn = 1;
		while (true) {
			int restOfDivide = numberOfTurn % 2; 

			// White 순서
			if (restOfDivide == 1) {
				if (isChecked(board(), "White")) {
					checkmated = isCheckmated(board(), "White");
					if(checkmated) {
						Frame.message.setText("흑의 승리.");
						break;
					}
					//체크인 경우 체크메이트 확인 - 체크메이트시 승패 결정
				}
				if (!checkmated) { //체크메이트가 아닌경우
					Frame.message.setText("백의 턴입니다.");
					while (ds) {
						int convertedRow = 7 - y; // 현재 좌표
						int convertedCol = x;
						int convertedRow2 = 7 - _y; // 이동할 좌표
						int convertedCol2 = _x;
						if(board()[convertedRow][convertedCol].color().equals("White")) {
							if (board()[convertedRow][convertedCol].movable(board(), _x, _y)) {
								backupBoard(board());
								board()[convertedRow][convertedCol].move(board(), _x, _y); //백업후 이동시행
								if(isChecked(board(), "White")) { //만약 이동후에 체크될경우
									returnBoard(board());
									Frame.printBoard();
									Frame.message.setText("이동이 불가능합니다.");
									st = false;
									ds = false;
								}
								else { // 이동후에 체크상태가 아닐경우
									if(temp[convertedRow2][convertedCol2] != null)
										ws++; // 상대 기물을 잡을경우 점수 증가
									Frame.printBoard();
									/*promote(board(), "White");*/
									numberOfTurn++;
									restOfDivide = numberOfTurn % 2;

									Frame.printBoard();
									st = false;
									ds = false;
								} // 이동이 가능한경우 이동
							}
							else {
								Frame.message.setText("이동이 불가능합니다.");
								st = false;
								ds = false;
							} // 이동 불가능할경우 선택 초기화, 이동불가 메세지 출력
						}
						else {
							Frame.message.setText("이동이 불가능합니다.");
							st = false;
							ds = false;
						}
					}
				}
			}

			else { // restOfDivide = 0 일때 : 흑의 순서
				if (isChecked(board(), "Black")) {
					checkmated = isCheckmated(board(), "White");
					if(checkmated) {
						Frame.message.setText("백의 승리.");
						break;
					}
					//체크인 경우 체크메이트 확인 - 체크메이트시 승패 결정
				}
				if (!checkmated) { //체크메이트가 아닌경우
					Frame.message.setText("흑의 턴입니다.");
					while (ds) {
						int convertedRow = 7 - y; // 현재 좌표
						int convertedCol = x;
						int convertedRow2 = 7 - _y; // 이동할 좌표
						int convertedCol2 = _x;
						if(board()[convertedRow][convertedCol].color().equals("Black")) {
							if (board()[convertedRow][convertedCol].movable(board(), _x, _y)) {
								backupBoard(board());
								board()[convertedRow][convertedCol].move(board(), _x, _y); //백업후 이동시행
								if(isChecked(board(), "Black")) { //만약 이동후에 체크될경우
									returnBoard(board());
									Frame.printBoard();
									Frame.message.setText("이동이 불가능합니다.");
									st = false;
									ds = false;
								}
								else { // 이동후에 체크상태가 아닐경우
									if(temp[convertedRow2][convertedCol2] != null)
										bs++; // 상대 기물을 잡을경우 점수 증가
									Frame.printBoard();
									/*promote(board(), "Black");*/
									numberOfTurn++;
									restOfDivide = numberOfTurn % 2;								
									Frame.printBoard();
									st = false;
									ds = false;
								} // 이동이 가능한경우 이동
							}
							else {
								Frame.message.setText("이동이 불가능합니다.");
								st = false;
								ds = false;
							} // 이동 불가능할경우 선택 초기화, 이동불가 메세지 출력
						}
						else {
							Frame.message.setText("이동이 불가능합니다.");
							st = false;
							ds = false;
						}
					}
				}
			}

		}
	}
	
	public void backupBoard (Piece[][] board) { //현재 보드상태를 백업
	    for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++)
				temp[i][j] = null;
	    }
	    for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++)
				temp[i][j] = board[i][j];
		}
	} 
	public void returnBoard (Piece[][] board) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++)
				board[i][j] = null;
		}
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++)
				board[i][j] = temp[i][j];
		}
	}
	/*//추가기능. 5점 이상 존재시 폰 승급 -> 5점이상 보유시 폰 이동시 승급으로 대체
	private void promote(Piece[][] board, String s) {
		
		boolean pexist = false; // 폰 존재 여부
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null) {
					if(board[i][j].getClass().getSimpleName().equals("Pawn")&&(board[i][j].color().equals(s)))
						pexist = true;
				}
			}
		} // 폰 존재여부 확인
		

		while((s.equals("White") && (ws == 5)) || (s.equals("Black") && (bs == 5))) {
			if(s.equals("White")) {
				if(pexist && (ws == 5)) { //폰이 존재하고 5점 이상일경우
					st = false;
					pm = false;
					int convertedRow = 7 - y;
			        int convertedCol = x;
			        boolean bool = true; //while문 스위치
			        while(!st) {
				        if(board[convertedRow][convertedCol] != null) {
							while(bool) {
								convertedRow = 7 - y;
						        convertedCol = x;
								if(!board[convertedRow][convertedCol].getClass().getSimpleName().equals("Pawn")) 
									bool = false;
								st = false;
							} // 폰 선택
				        }
				        else
							while(bool) {
								convertedRow = 7 - y;
						        convertedCol = x;
								 if(board[convertedRow][convertedCol] != null)
									 if(!board[convertedRow][convertedCol].getClass().getSimpleName().equals("Pawn")) 
										 bool = false;
								 st = false;
														
						}
			        }
			        if(board[convertedRow][convertedCol] != null) {
						while (board[convertedRow][convertedCol].getClass().getSimpleName().equals("Pawn")) {
							while(AppController.pm) {
								board[convertedRow][convertedCol] =null; // 해당 위치 폰 초기화
					
					
								switch(AppController.pmn) {
								case 1:	
									board[convertedRow][convertedCol] = new Queen("White", x, y);
									break;
								case 2:
									board[convertedRow][convertedCol] = new Knight("White", x, y);
									break;
								case 3:
									board[convertedRow][convertedCol] = new Bishop("White", x, y);
									break;
								case 4:
									board[convertedRow][convertedCol] = new Rook("White", x, y);
									break;
								}
								AppController.pm = false;
							}
						}
						ws -= 5;
					}
				}
			}
			else if(s.equals("Black")) {
				if(pexist && (bs == 5)) { //폰이 존재하고 5점 이상일경우
					st = false;
					int convertedRow = 7 - y;
			        int convertedCol = x;
			        
			        Frame.message.setText("승급할 폰을 선택하세요.");
			        if(board[convertedRow][convertedCol] != null) {
						while(!board[convertedRow][convertedCol].getClass().getSimpleName().equals("Pawn")) {
							
					        st = false;
							
						} // 폰 선택
			        }
					if(board[convertedRow][convertedCol] != null) {
						while (board[convertedRow][convertedCol].getClass().getSimpleName().equals("Pawn")) {
							while(AppController.pm) {
								board[convertedRow][convertedCol] =null; // 해당 위치 폰 초기화
					
					
								switch(AppController.pmn) {
								case 1:	
									board[convertedRow][convertedCol] = new Queen("Black", x, y);
									break;
								case 2:
									board[convertedRow][convertedCol] = new Knight("Black", x, y);
									break;
								case 3:
									board[convertedRow][convertedCol] = new Bishop("Black", x, y);
									break;
								case 4:
									board[convertedRow][convertedCol] = new Rook("Black", x, y);
									break;
								}
								AppController.pm = false;
							}
						}
						bs -= 5;
					}
				}
			}
		}
	}*/
	
    /*   이동시 체크상태 되는지 유무   */
	public boolean isChecked(Piece[][] board, String s) {
    	
       
        for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				 if((board[i][j] != null) && !(board[i][j].color().equals(s))) { // 현재 턴 색의 말이 아닌 경우
					 if(s.equals("White")) // 현재 흰색 턴인경우
						 for(int k = 0; k < 8; k++) {
							for(int l = 0; l < 8; l++) {
								if((board[k][l] != null) && board[i][j].color().equals("Black") && board[i][j].movable(board, l, 7-k) 
										&& board[k][l].getClass().getSimpleName().equals("King"))
									return true;
							}
						 }						
					 else if(s.equals("Black"))
						 for(int k = 0; k < 8; k++) {
							 for(int l = 0; l < 8; l++) {
								if((board[k][l] != null) && board[i][j].color().equals("White") && board[i][j].movable(board, l, 7-k) 
										&& board[k][l].getClass().getSimpleName().equals("King"))
									return true;
							}
						 }
				 }
				 // 메인에서 킹의 좌표 전역변수 생성 후 킹 객체에서 이동시 갱신하는 함수 : renewKingPosition() ~ AppController의 멤버 함수
			}
        }
        return false;
        // this.color와 색이 다른경우 this.color의 킹의 좌표로 이동가능한 경우가 하나라도 있으면 체크상태 true
    }


	public boolean isCheckmated(Piece[][] board, String s) {



		if(s.equals("White")) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					for(int k = 0; k < 8; k++) {
						for(int l = 0; l < 8; l++) { //모든 시작지점, 모든 도착지점 탐색
							if((board[i][j] != null) && (board[i][j].color().equals("White"))) {
								if (board[i][j].movable(board, 1, 7-k)) { //이동가능할 경우
									backupBoard(board);
									board[i][j].move(board, 1, 7-k); //백업후 이동시행
									if(isChecked(board, "White")) { //만약 이동후에 체크될경우
										returnBoard(board);
									}
									else { //체크상태를 해제하는 수가 존재할경우 false 리턴
										returnBoard(board);
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		if(s.equals("Black")) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					for(int k = 0; k < 8; k++) {
						for(int l = 0; l < 8; l++) { //모든 시작지점, 모든 도착지점 탐색
							if((board[i][j] != null) && (board[i][j].color().equals("Black"))) {
								if (board[i][j].movable(board, 1, 7-k)) { //이동가능할 경우
									backupBoard(board);
									board[i][j].move(board, 1, 7-k); //백업후 이동시행
									if(isChecked(board, "Black")) { //만약 이동후에 체크될경우
										returnBoard(board);
									}
									else { //체크상태를 해제하는 수가 존재할경우 false 리턴
										returnBoard(board);
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	public void run() {
		this.turn();

	}
}
