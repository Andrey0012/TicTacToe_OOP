package Game;

//TODO сущность ИГРОК

public class GamePlayer {
    private char playerSign; //символ игрока
    private boolean realPlayer = true; //признак реальный игрок или компьютер
    public GamePlayer (boolean isRealPlayer, char playerSign) {
        this.realPlayer = isRealPlayer;
        this.playerSign = playerSign;
    }

    public char getPlayerSign() {
        return playerSign;
    }
    public boolean isRealPlayer () {
        return realPlayer;
    }
}
