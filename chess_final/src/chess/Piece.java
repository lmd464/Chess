package chess;

public abstract class Piece {


    /*   필드 x, y 를 넣은 이유
         이동을 한다 치면 기존 위치에서 말을 삭제해야 하는데, 이를 위해서는 기존 위치에 접근이 필요함.
         따라서 현재 위치를 필드로 저장해놓으면 이를 이용할 수 있음.   */

    /*   2차원 배열에서의 row, col과 왼쪽 아래 기준 좌표 x, y 의 관계 :
         row, col : (7, 3) -> x, y : (3, 0)
         x = col, y = 7 - row
         !!! 배열 Board 에 접근할 때에는 x, y -> row, col 로의 변환 필요 !!!   */

    protected int _x, _y;       // 현재 좌표 : 왼쪽 아래 기준 좌표, 0 부터 시작
    protected String _color;   // 말의 색깔 : 플레이어와 상대방 구분
   


    /*   Getter   */
    public String color() {
        return this._color;
    }
    public void setColor(String newColor) {
    	this._color = newColor;
    }
    public int x( ) {
    	return this._x;
    }
    public void setX(int newX) {
    	this._x = newX;
    }
    public int y( ) {
    	return this._y;
    }
    public void setY(int newY) {
    	this._y = newY;
    }



    /*   move() : 이동이 가능한 위치를 받았을 시에 이동
    1. 목적지 좌표가 비어있음
    2. 목적지 좌표에 적이 존재
    -> 그냥 목적지 좌표에 대입을 해버리면 된다.   */

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
       
    }



/*   movable() ~ move가 불가능한 경우
   1. 목적지 좌표를 가는 경로 (목적지 제외) 에 말이 존재 (우리편 or 상대편) ~ 뛰어넘어서 갈 수는 없음.
   - 나이트는 해당사항 없음
   2. 목적지 좌표에 우리편 말이 존재
   3. 목적지 좌표로 이동할 시에 상대편의 말에 대하여 하나라도 체크 상태가 되는 경우
   4. 말이 할 수 없는 이동 시도 (ex : 폰이 3칸 이동하려 한다..)
   5. 보드 밖으로 이동 시도   */

    public abstract boolean movable(Piece[][] board, int x, int y);



    /*   해당 위치에 아군 말 존재   */
    public boolean friendlyInDestination(Piece[][] board, int newX, int newY) {
        int convertedRow = 7 - newY;
        int convertedCol = newX;
        return (board[convertedRow][convertedCol] != null) && (board[convertedRow][convertedCol].color().equals(this.color()));
    }



    /*   판 밖으로 이동   */
    public boolean outOfRange(int newX, int newY) {
        int convertedRow = 7 - newY;
        int convertedCol = newX;
        return (convertedRow > 7 || convertedRow < 0) || (convertedCol > 7 || convertedCol < 0);
    }

    

}
