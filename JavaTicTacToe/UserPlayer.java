import java.util.InputMismatchException;
import java.util.Scanner;

public class UserPlayer extends Player{
    private Scanner scanner;

    public UserPlayer(game game , String marker) {
        super(game, marker);
        this.scanner = new Scanner(System.in);
    }

    @Override 
    public int selectPosition() {
        while(true) {
            System.out.println("Select " + marker + " position:- ");
            try {
                int choice = scanner.nextInt();
                if ((game.getFreePositions().contains(choice))) {
                    return choice;
                }
                System.out.println("Position " + choice + " is not available. Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Enter valid position");
                scanner.next();
            }  
        }  
    }

    @Override
    public String toString() {
        return "Human";
    }
}
