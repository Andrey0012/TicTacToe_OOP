package Game;

//TODO сущность ИГРА

import javax.swing.*;

public class Game {
    private GameBoard board; //ссылка игровое поле
    private GamePlayer [] gamePlayers = new GamePlayer[2]; //массив игроков
    private int playersTurn = 0; //индес текущего игрока, то есть ходить будет сначала реальный игрок а не компьютер
    public Game(){
        this.board = new GameBoard (this); // this означает что мы передаем текущтй класс нашей игры
    }
    public void initGame () {
        gamePlayers [0] = new GamePlayer(true, 'X');
        gamePlayers [1] = new GamePlayer(false, '0');
    }
    //TODO передачи хода. Если индекс реального игра то он передает ход компьютеру и наоборот
    void passTurn () {
        if (playersTurn == 0)
            playersTurn = 1;
        else playersTurn = 0;
    }
    //TODO получаем объект текущего игрока
    GamePlayer getCurrentPlayer () {return gamePlayers[playersTurn];}

    // TODO метод показа всплывающего сообшения
    void showMessage (String messageText) {
        JOptionPane.showMessageDialog(board,messageText); //привязываем игровое поле и текс сообщения
        //теперь можем отправлять какие то собщения
    }
}

