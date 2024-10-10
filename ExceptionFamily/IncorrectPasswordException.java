package ExceptionFamily;

/**
 * The exception used to alarm the exception when the password is incorrect
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class IncorrectPasswordException extends Exception{

    /**
     * The exception used to alarm the exception when the password is incorrect
     */
    public IncorrectPasswordException(){
        super("Incorrect Password. Terminating Program");
    }


    /**
     * The exception used to alarm the exception when the password is incorrect
     * @param message print message
     */
    public IncorrectPasswordException(String message){
        super(message);
    }
}
