package Game;

//TODO сущность игровое поле

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3; //размерность
    static int cellSize = 150;   //велчина одной клетки
    static char [][] gameField; // матрица игры
    private GameButton [] gameButtons; //массив кнопок
    static char nullSymbol = '\u0000'; // null символ

    private Game game; //ссылка на игру

    public GameBoard (Game currentGame) { // передаем экземпляр класса игры
        this.game = currentGame;
        initField (); //метод инициализации поля
    }
    private void initField () {
        setBounds(cellSize * dimension,cellSize * dimension,400,300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  //выход при закрытии окна

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(); // панель управелния игрой
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField(); //отвечает за очистку поля
            }
        });
        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS)); //менеджер размещения контрольной панели
        controlPanel.add(newGameButton); // добавляем кнопку в менеджер размещения
        controlPanel.setSize(dimension * cellSize,150); //устанавливаем размер нашей кнопки

        JPanel gameFieldPanel = new JPanel(); //панель самой игры
        gameFieldPanel.setLayout(new GridLayout(dimension,dimension)); //менеджер размещения игрового поля
        gameFieldPanel.setSize(dimension * cellSize, dimension * cellSize);

        gameField = new char[dimension][dimension]; //инициилизируем матрицу нашей игры
        gameButtons = new GameButton[dimension*dimension]; //одномерный масив кнопок

        //иницилизируем игровое поле
        for (int i = 0; i < (dimension*dimension); i++) {
            GameButton fieldButton = new GameButton(i,this);
            gameFieldPanel.add(fieldButton);      //добавляем на игровое поле нашу кнопку
            gameButtons [i] = fieldButton; //добавляем в маасив полученный номер кнопки
        }
        getContentPane().add(controlPanel,BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel,BorderLayout.CENTER);
        setVisible(true);

        }
        //TODO метод очистки поля и матрицы игры
    void emptyField () {
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");
            int x = i/GameBoard.dimension;
            int y = i % GameBoard.dimension;
            gameField [x][y] = nullSymbol; // это значение присваиваеться при очистке поля
        }
    }
    Game getGame () { // гетор для игры
        return game;
    }
    //TODO метод проверки доступности клетки для хода
    // х по горизонтали , у по вертикали
    boolean isTurnable (int x, int y) {
        boolean result = false;
        if (gameField[x][y] == nullSymbol) {
            result = true;
        }
        return result;
    }
    //TODO обномелние матрицы игры после хода
    void updateGameField (int x, int y) {
        gameField [x][y] = game.getCurrentPlayer().getPlayerSign();
    }
    //TODO проверка победы по линиям и столбцам
    boolean chekWin () {
        boolean result = false;
        char playerSymbol = getGame().getCurrentPlayer().getPlayerSign();
        if (chekWinDiagonals (playerSymbol) || chekWinLines (playerSymbol)) {
            result = true;
        }
        return result;
    }
    //TODO метод проверки заполненности поля
    boolean isFull () {
        boolean result = true;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] ==nullSymbol)
                    result = false;
            }
        }
        return result;
    }

    public GameButton getButton (int buttonIndex) {
        return gameButtons[buttonIndex];
    }

    private  boolean chekWinLines (char playerSymbol) {
        boolean cols, rows, result;
        result = false;
        for (int col = 0; col < dimension; col++) {
            cols = true;
            rows = true;
            for (int row = 0; row <  dimension; row++) {
                cols &= (gameField[col][row] == playerSymbol);
                rows &= (gameField[row][col] == playerSymbol);
            }
            //это условие после каждой колонки и столбца
            //позволяеи остановить дальнейшее выполнение, без проверки
            //всех остальных столбцов и строк
            if (cols||rows){
                result = true;
                break;
            }
            if (result) {
                break;
            }
        }
        return result;
    }
    private  boolean chekWinDiagonals (char playerSymbol) {
        boolean diagLeft, diagRight, result;
        result = false;
        diagLeft = true;
        diagRight = true;
        for(int col = 0; col < dimension; col++) {
            for (int row = 0; row < dimension; row++) {
                if (col == row) {
                    diagLeft &= (gameField[col][row] == playerSymbol);
                }
                if ((col + row) == (dimension - 1)) {
                    diagRight &= (gameField[col][row] == playerSymbol);
                }

            }
        }
        if(diagLeft || diagRight) result = true;
        return result;
    }
 }
