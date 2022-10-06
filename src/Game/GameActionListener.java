package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row; //номер ряда
    private int cell; //номер в столбце
    private GameButton button; //ссылка на кнопку
    private static final boolean  updateByAiData = false;
    public GameActionListener (int row, int cell, GameButton gButton){
        this.row = row;
        this.cell = cell;
        this.button = gButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();
        if (board.isTurnable(row, cell)) {
            updateDyPlayersData(board); //ход человека
            if (board.isFull()) {
                board.getGame().showMessage("Ничья");
                board.getGame().passTurn();//передали ход
                board.emptyField();
            }
            else { if (updateByAiData) {
                updateByAiData(board);
            }  else {
                smartUpdateByAiData (board);
            }
            }
        } else {
            board.getGame().showMessage("Некоректный ход");
        }
    }
    //TODO ход человека
    private void updateDyPlayersData (GameBoard board) {
        //обновить матрицу игры
        board.updateGameField(row,cell);
        //обновить содержимоме кнопки
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        if (board.chekWin()){
            board.getGame().showMessage("Вы выйграли");
            board.getGame().passTurn();//передали ход
            board.emptyField(); //чистим поле
        }
        else {
            board.getGame().passTurn();//передали ход компьютеру
        }

    }
    private void updateByAiData (GameBoard board) {
        //TODO генерация  координат хода компьютера
        int x, y;
        Random rnd = new Random();
        do {
            x = rnd.nextInt(GameBoard.dimension);
            y = rnd.nextInt(GameBoard.dimension);
        }
        while (!board.isTurnable(x,y));

        //обновить матрицу игры
        board.updateGameField(x,y);

        //обновить содержимое кнопки
        int cellIndex = GameBoard.dimension*x+y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        //проверить победу
        if (board.chekWin()) {
            button.getBoard().getGame().showMessage("Компьютер выйграл");
            board.emptyField();
        }
        else {
            //передать ход
            board.getGame().passTurn();
        }
    }
    private void smartUpdateByAiData (GameBoard board) {
        int x, y;
        int[] temp = new int[2];
        int priority = 0;
        for(int i = 0; i < GameBoard.dimension; i++){
            for(int j = 0; j < GameBoard.dimension; j++){
                int tempPriority = 0;
                if(GameBoard.gameField[i][j] == GameBoard.nullSymbol){
                    if(i - 1 >= 0 && j - 1 >= 0 && GameBoard.gameField[i-1][j-1] == '0') tempPriority++;
                    if(i - 1 >= 0 && GameBoard.gameField[i-1][j] == '0') tempPriority++;
                    if(i - 1 >= 0 && j + 1 < GameBoard.dimension && GameBoard.gameField[i-1][j+1] == '0') tempPriority++;
                    if(j - 1 >= 0 && GameBoard.gameField[i][j-1] == '0') tempPriority++;
                    if(j + 1 < GameBoard.dimension && GameBoard.gameField[i][j+1] == '0') tempPriority++;
                    if(i + 1 < GameBoard.dimension && j - 1 >= 0 && GameBoard.gameField[i+1][j-1] == '0') tempPriority++;
                    if(i + 1 < GameBoard.dimension && GameBoard.gameField[i][j] == '0') tempPriority++;
                    if(i + 1 < GameBoard.dimension && j + 1 < GameBoard.dimension && GameBoard.gameField[i+1][j+1] == '0') tempPriority++;
                }
                if(tempPriority > priority){
                    priority = tempPriority;
                    temp[0] = i;
                    temp[1] = j;
                }
            }
        }
        System.out.println(priority);
        if(priority != 0){
            x = temp[0];
            y = temp[1];
        }
        else {
            do {
                Random rnd = new Random();
                x = rnd.nextInt(GameBoard.dimension);
                y = rnd.nextInt(GameBoard.dimension);
            }
            while (!board.isTurnable(x, y));
        }

        board.updateGameField(x, y);
        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if(board.chekWin()){
            button.getBoard().getGame().showMessage("компьютер выиграл!");
            board.getGame().passTurn();
            board.emptyField();
        }
        else{
            board.getGame().passTurn();
        }
    }

}