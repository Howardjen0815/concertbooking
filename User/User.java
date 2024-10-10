package User;
import AllConcert.ConcertDetails;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * The User class to show the user choice.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */

public abstract class User{

    /**
     * default constructor
     */

    /**
     * used to show the mainMeny of the user(admin or customer)
     */
    public abstract void concertOptionPanel();

    /**
     * sahow the select message to hint the user input
     * @param concertDetails the concert details for read
     * @param keyboard let the user read the input
     * @param concertId read the concertid
     * @return concertNumber
     */
    public abstract int showSelectMessage(ConcertDetails concertDetails, Scanner keyboard, ArrayList<Integer> concertId);


    /**
     * read the user input
     * @param scanner read the input
     * @return concertNumber(which will-1 for enter to the array)
     */
    public int readCustomerInput(Scanner scanner){
        int NumberForChoice = scanner.nextInt()-1;
        return NumberForChoice;
    }//we need to read the customer input

}