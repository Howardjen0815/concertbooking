package Constants;

/**
 * Manages all the constants used throughout the classes
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class Constants{

    /**
     * the deault constructor used
     */


    /**
     * For Customer and Admin show timetable
     */
    public static final int SHOW_TIMING =1;

    /**
     * For Customer show concert price
     */
    public static final int CUSTOMER_SHOW_PRICE = 2;

    /**
     * For Customer booking seat
     */
    public static final int CUSTOMER_BOOKING = 3 ;

    /**
     * For Customer booked seat price
     */
    public static final int CUSTOMER_SHOW_BOOKINGPRICE = 4;

    /**
     * For Customer and User leave customer mode
     */
    public static final int EXIT = 5;

    /**
     * For admin update the concert price
     */
    public static final int ADMIN_UPDATE_PRICE = 2;


    /**
     * For admin view booking detail
     */
    public static final int ADMIN_VIEW_BOOKING_DETAIL = 3;

    /**
     * For admin show the concert price
     */
    public static final int ADMIN_VIEW_TOTAL_PAYMENT = 4;

    /**
     * Price format
     */
    public static final String PRICE_STATEMENT_FORMAT = "---------- %8s ----------%n";

    //For Concert Format


    /**
     * Concert information format
     */
    public static final String CONCERT_FORMAT = "%-5s%-15s%-15s%-15s%-30s%-15s%-15s%-15s%n";

    /**
     * bookingPrice Format
     */
    public static final String BOOKINGPRICELIST = "%-5s%-15s%-15s%-10s%-15s%-15s%-10s%n";


    /**
     * ticketId Format
     */
    public static final String TICKETINFOLIST = "%-5s%-15s%-15s%-10s%-10s%n";

    /**
     * Constant representing the VIP section identifier.
     * This constant is used to denote a VIP section in various parts of the program,
     * ensuring consistent usage of the 'V' for VIP sections.
     */
    public static final String VIP = "V";

    /**
     * Constant representing the Seating section identifier.
     * This constant is used to denote a Seating section in various parts of the program,
     * ensuring consistent usage of the 'T ' for Seating sections.
     */
    public static final String SEATING = "S";

    /**
     * Constant representing the Standing section identifier.
     * This constant is used to denote a Standing section in various parts of the program,
     * ensuring consistent usage of the 'S' for Standing sections.
     */
    public static final String STANDING = "T";

}