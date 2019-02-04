package chess;

public class King extends Piece {


    /*   킹 시작 위치 : WHITE 가 아군이라 가정.   */
    private static final int BLACK_KING_START_X = 4;
    private static final int BLACK_KING_START_Y = 7;
    private static final int WHITE_KING_START_X = 4;
    private static final int WHITE_KING_START_Y = 0;


    /*   Constructor   */
    public King(String newColor) {

        if( newColor.equals("Black") ) {
            this.setX(BLACK_KING_START_X);
            this.setY(BLACK_KING_START_Y);
        }
        else if( newColor.equals("White") ){
            this.setX(WHITE_KING_START_X);
            this.setY(WHITE_KING_START_Y);
        }
        this.setColor(newColor);

    }



    @Override
    public boolean movable(Piece[][] board, int newX, int newY) {

        boolean correctMove = false;

        correctMove = ( (newX == this.x() + 1) || (newX == this.x() - 1) || (newX == this.x()) )  &&    // x : 0 or 1 움직임.
                      ( (newY == this.y() + 1) || (newY == this.y() - 1) || (newY == this.y()) );     // y : 0 or 1 움직임.

        // 말에 알맞는 이동방향, 목적지에 같은 편 없음, 말판 벗어나지 않음. (체크상태는 move 에서 판정)
        correctMove = correctMove && !this.friendlyInDestination(board, newX, newY) && !this.outOfRange(newX, newY);

        return correctMove;

    }
    

}
