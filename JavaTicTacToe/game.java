
import java.util.ArrayList;
import java.util.List;

public class game {
    public static final int[][] LINES = {
        {1,2,3},{4,5,6},{7,8,9},
        {1,4,7},{2,5,8},{3,6,9},
        {1,5,9},{3,5,7}
    };

    private String[] board;
    private int currentPlayerID; // switch between players per game
    private Player[] players;

    game(Class<? extends Player> player1Class, Class<? extends Player> player2Class) { // java polymorphism
        board = new String[10]; // Index 0 avoid
        currentPlayerID = 0;
        try {
            players = new Player[] {
                player1Class.getConstructor(game.class, String.class).newInstance(this, "X"),
                player2Class.getConstructor(game.class, String.class).newInstance(this, "O")
            };
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate players", e);
        }
        System.out.println(currentPlayer() + " goes first");
    }

    public void play() {
    while(true) {
        placePlayerMarker(currentPlayer());
        if(playerHasWon(currentPlayer())) {
            System.out.println(currentPlayer() + " WINS!!");
            printboard();
            return;
        } else if (boardFull()) {
            System.out.println("DRAW!");
            printboard();
            return;
        }
        switchPlayers();
        }
    }


    public List<Integer> getFreePositions() {
        List<Integer> freePositions = new ArrayList<>();
        for(int i = 1; i<=9 ; i++) {
            if(board[i] == null) freePositions.add(i);
        }
        return freePositions;
    }

    private void placePlayerMarker(Player player) {
        int position;
        do {
            position = player.selectPosition();
            if (!getFreePositions().contains(position)) {
                System.out.println("Position " + position + " is already taken. Trying again.");
            }
        } while (!getFreePositions().contains(position));
        System.out.println(player + " selects " + player.getMarker() + " position " + position);
        board[position] = player.getMarker();
        printboard();
    }

    private boolean playerHasWon(Player player) {
        String marker = player.getMarker();
        for (int[] line: LINES) {
            boolean won = true;
            for (int position : line) {
                if (!marker.equals(board[position])) { // for each position of winning LINES check if all selected
                    won = false; 
                    break;
                }
            }
            if (won) return true;
        }
        return false;
    }

    private boolean boardFull() {
        return getFreePositions().isEmpty();
    }

     private int otherPlayerId() {
        return 1 - currentPlayerID; // switch player
    }

    private void switchPlayers() {
        currentPlayerID = otherPlayerId();
    }

    public Player currentPlayer() {
        return players[currentPlayerID];
    }

    public Player getOpponent() {
        return players[otherPlayerId()];
    }

    public int getTurnNum() {
        return 10 - getFreePositions().size(); // get Turn num from number of free positions left in board
    }

    public String[] getBoard() {
        return board;
    }

    public int getCurrentPlayerId() {
        return currentPlayerID;
    }
    
    public void printboard() {
        String colSeparator = " | ";
        String rowSeparator = "--+---+--";
        String[][] rows = {
            {board[1] != null ? board[1] : "1", board[2] != null ? board[2] : "2", board[3] != null ? board[3] : "3"},
            {board[4] != null ? board[4] : "4", board[5] != null ? board[5] : "5", board[6] != null ? board[6] : "6"},
            {board[7] != null ? board[7] : "7", board[8] != null ? board[8] : "8", board[9] != null ? board[9] : "9"}
        };
        for (int i = 0; i < 3; i++) {
            System.out.println(String.join(colSeparator, rows[i]));
            if (i < 2) System.out.println(rowSeparator);
        }
    }

}
