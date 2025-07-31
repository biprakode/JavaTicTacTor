public class Main {
    public static void main(String[] args) {

        game auto_game = new game(AIPlayer.class, AIPlayer.class);
        auto_game.play();
        System.out.println();


        game my_game = new game(UserPlayer.class , AIPlayer.class);
        my_game.play();
        System.out.println();
    }
}
