package Game;
import javax.swing.*;

//TODO сущность КНОПКА

public class GameButton extends JButton {
    private int buttonIndex; //номер (индекс) кнопки
    private GameBoard board; // ссылка на игровое поле
    public GameButton (int gameButtonIndex, GameBoard currentGameBoard) { // ссылаемя на номер кнопки и игровое поле
        buttonIndex = gameButtonIndex;
        board = currentGameBoard;
        int rowNum = buttonIndex/GameBoard.dimension; //узнаем номер ряда
        int cellNum = buttonIndex % GameBoard.dimension;//узнамем номер столбце
        setSize(GameBoard.cellSize - 5,GameBoard.cellSize - 5); //задаем размер кнопки , уменьшаем на 5 что
        // что бы не поехаол игровое поле и все кнопки уместились
        addActionListener(new GameActionListener (rowNum,cellNum,this));
    }

    public GameBoard getBoard() {
        return board; // возвращает ссылку на игровую доску для кнопки

    }
}
