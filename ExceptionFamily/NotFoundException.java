package ExceptionFamily;

/**
 * The exception when the customer is not exist in the customer.csv
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class NotFoundException extends Exception{

    /**
     * not going to create the contructor due to is will not have the instance
     */

    /**
     * print the error message
     */
    public NotFoundException(){
        super("Customer does not exist. Terminating Program");
    }

    /**
     * print the error message
     * @param message the message you want to enter
     */
    public NotFoundException(String message){
        super(message);
    }
}