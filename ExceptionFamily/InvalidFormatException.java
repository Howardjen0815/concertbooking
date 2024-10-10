package ExceptionFamily;

/**
 * The exception used to hint the user the file has some problem, will skip this line
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class InvalidFormatException extends Exception {

    /**
     * not going to create the instance
     */

    /**
     * the detail of all the information of the format, which it needs to check
     */

    /**
     * booking format
     */
    public static final int BOOKING_FORMAT = 1;
    /**
     * customer format
     */
    public static final int CUSTOMER_FORMAT = 2;

    /**
     * concert format
     */
    public static final int CONCERT_FORMAT = 3;
    /**
     * ticket format
     */
    public static final int TICKET_NUMBER_FORMAT = 4;
    /**
     * zone type format
     */
    public static final int ZONE_TYPE_FORMAT = 5;

    /**
     * The constructor of InvalidFormat
     * @param messageNumber which problem happen
     */
    public InvalidFormatException(int messageNumber) {
        super(getMessage(messageNumber));
    }

    /**
     * The exception used to hint the user the file has some problem, will skip this line, and told which line has problem,I do not think this should be not static, because create the instance of this is weird
     * @paran messageNumber which problem happen
     */
    private static String getMessage(int messageNumber) {
        switch (messageNumber) {
            case BOOKING_FORMAT:
                return "Booking Id is in incorrect format. Skipping this line.";
            case CUSTOMER_FORMAT:
                return "Customer Id is in incorrect format. Skipping this line.";
            case CONCERT_FORMAT:
                return "Concert Id is in incorrect format. Skipping this line.";
            case TICKET_NUMBER_FORMAT:
                return "Incorrect Number of Tickets. Skipping this line.";
            case ZONE_TYPE_FORMAT:
                return "Invalid Zone Type. Skipping this line.";
            default:
                return "Unknown problem, please contact Yu-Han Jen";
        }
    }
}