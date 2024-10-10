package ExceptionFamily;

/**
 * The exception used to check the file data point
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class InvalidLineException extends Exception {

    /**
     * not going to create the instance
     */

    /**
     * the detail of all the information of the line, which it needs to check
     */

    /**
     * concert invalid
     */
    public static final int CONCERT = 1;

    /**
     * booking invalid
     */
    public static final int BOOKING = 2;

    /**
     * customer invalid
     */
    public static final int CUSTOMER = 3;

    /**
     * constructor of InvalidLine
     * @param messageNumber which problem happen
     */
    public InvalidLineException(int messageNumber) {
        super(getErrorMessage(messageNumber));
    }

    /**
     * @param messageNumber the invalid type
     * @return throw the invalid message, I do not think this should be not static, because create the instance of this is weird
     */
    private static String getErrorMessage(int messageNumber) {
        switch (messageNumber) {
            case CONCERT:
                return "Invalid Concert Files. Skipping this line.";
            case BOOKING:
                return "Invalid booking Files. Skipping this line.";
            case CUSTOMER:
                return "Invalid Customer Files. Skipping this line.";
            default:
                throw new IllegalArgumentException("Unknown message type: " + messageNumber);
        }
    }
}