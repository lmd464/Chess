package chess;

public class Knight extends Piece {


    /*   Constructor : Knight 가 2개씩 있으므로, 생성할 때 좌표 대입 필요  */
    public Knight(String newColor, int x, int y) {

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


    @Override
    public boolean movable(Piece[][] board, int newX, int newY) {

        boolean correctMove = false;

        correctMove = ( (newX == this.x() + 1) && (newY == this.y() + 2) ) ||       // x : +1, y : +2    이동
                      ( (newX == this.x() + 2) && (newY == this.y() + 1) ) ||       // x : +2, y : +1
                      ( (newX == this.x() + 1) && (newY == this.y() - 2) ) ||       // x : +1, y : -2
                      ( (newX == this.x() + 2) && (newY == this.y() - 1) ) ||       // x : +2, y : -1

                      ( (newX == this.x() - 1) && (newY == this.y() + 2) ) ||       // x : -1, y : +2
                      ( (newX == this.x() - 2) && (newY == this.y() + 1) ) ||       // x : -2, y : +1
                      ( (newX == this.x() - 1) && (newY == this.y() - 2) ) ||       // x : -1, y : -2
                      ( (newX == this.x() - 2) && (newY == this.y() - 1) );         // x : -2, y : -1


        // 말에 알맞는 이동방향, 목적지에 같은 편 없음, 말판 벗어나지 않음. (체크상태는 move 에서 판정)
        correctMove = correctMove && !this.friendlyInDestination(board, newX, newY) && !this.outOfRange(newX, newY);

        return correctMove;

    }


}
