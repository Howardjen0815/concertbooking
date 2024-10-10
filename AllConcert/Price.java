package AllConcert;

/**
 * The Price class store the information of left area, right area, middle area.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class Price {

    /**
     * The Price of left area, middle area, right area of the concert all of them contain the VIP, STANDING, SEATING
     */
    private double leftSeatPrice;
    private double middleSeatPrice;
    private double rightSeatPrice;
    private double leftStandPrice;
    private double middleStandPrice;
    private double rightStandPrice;
    private double leftVIPPrice;
    private double middleVIPPrice;
    private double rightVIPPrice;
    //the price will include three parts, left, middle and right

    /**
     * COnstruct the concert Price
     * @param leftStandPrice leftStandPrice
     * @param leftSeatPrice leftSeatPrice
     * @param leftVIPPrice leftVIPPrice
     * @param middleStandPrice iddleStandPrice
     * @param middleSeatPrice middleSeatPrice
     * @param middleVIPPrice middleVIPPrice
     * @param rightStandPrice rightStandPrice
     * @param rightSeatPrice rightSeatPrice
     * @param rightVIPPrice rightVIPPrice
     */
    public Price(double leftStandPrice, double leftSeatPrice, double leftVIPPrice, double middleStandPrice, double middleSeatPrice, double middleVIPPrice, double rightStandPrice, double rightSeatPrice, double rightVIPPrice) {
        setLeftPrice(leftStandPrice, leftSeatPrice, leftVIPPrice);
        setMiddlePrice(middleStandPrice,middleSeatPrice, middleVIPPrice);
        setRightPrice(rightStandPrice,rightSeatPrice, rightVIPPrice);
        //Constructor of create the price
    }

    /**
     * set the left area price
     * @param leftStandPrice leftStandPrice
     * @param leftSeatPrice leftSeatPrice
     * @param leftVIPPrice leftVIPPrice
     */
    private void setLeftPrice(double leftStandPrice, double leftSeatPrice, double leftVIPPrice) {
        this.leftSeatPrice = leftSeatPrice;
        this.leftStandPrice = leftStandPrice;
        this.leftVIPPrice = leftVIPPrice;
    }

    /**
     * set the middle area price
     * @param middleStandPrice middle Stand Price
     * @param middleSeatPrice middle Seat Price
     * @param middleVIPPrice middle VIP Price
     */
    private void setMiddlePrice(double middleStandPrice, double middleSeatPrice, double middleVIPPrice) {
        this.middleSeatPrice = middleSeatPrice;
        this.middleStandPrice = middleStandPrice;
        this.middleVIPPrice = middleVIPPrice;
    }

    /**
     * set the right area price
     * @param rightStandPrice right stand price
     * @param rightSeatPrice right seat price
     * @param rightVIPPrice right vip price
     */
    private void setRightPrice(double rightStandPrice, double rightSeatPrice, double rightVIPPrice) {
        this.rightSeatPrice = rightSeatPrice;
        this.rightStandPrice = rightStandPrice;
        this.rightVIPPrice = rightVIPPrice;
    }


    /**
     * Retrieves the price of the left seating area.
     * @return The price of the left seating area.
     */
    public double getLeftSeatPrice() {
        return leftSeatPrice;
    }

    /**
     * Sets the price of the left seating area.
     * @param leftSeatPrice The new price of the left seating area.
     */
    public void setLeftSeatPrice(double leftSeatPrice) {
        this.leftSeatPrice = leftSeatPrice;
    }

    /**
     * Retrieves the price of the middle seating area.
     * @return The price of the middle seating area.
     */
    public double getMiddleSeatPrice() {
        return middleSeatPrice;
    }

    /**
     * Sets the price of the middle seating area.
     * @param middleSeatPrice The new price of the middle seating area.
     */
    public void setMiddleSeatPrice(double middleSeatPrice) {
        this.middleSeatPrice = middleSeatPrice;
    }

    /**
     * Retrieves the price of the right seating area.
     * @return The price of the right seating area.
     */
    public double getRightSeatPrice() {
        return rightSeatPrice;
    }

    /**
     * Sets the price of the right seating area.
     * @param rightSeatPrice The new price of the right seating area.
     */
    public void setRightSeatPrice(double rightSeatPrice) {
        this.rightSeatPrice = rightSeatPrice;
    }

    /**
     * Retrieves the price of the left standing area.
     * @return The price of the left standing area.
     */
    public double getLeftStandPrice() {
        return leftStandPrice;
    }

    /**
     * Sets the price of the left standing area.
     * @param leftStandPrice The new price of the left standing area.
     */
    public void setLeftStandPrice(double leftStandPrice) {
        this.leftStandPrice = leftStandPrice;
    }

    /**
     * Retrieves the price of the middle standing area.
     * @return The price of the middle standing area.
     */
    public double getMiddleStandPrice() {
        return middleStandPrice;
    }

    /**
     * Sets the price of the middle standing area.
     * @param middleStandPrice The new price of the middle standing area.
     */
    public void setMiddleStandPrice(double middleStandPrice) {
        this.middleStandPrice = middleStandPrice;
    }

    /**
     * Retrieves the price of the right standing area.
     * @return The price of the right standing area.
     */
    public double getRightStandPrice() {
        return rightStandPrice;
    }

    /**
     * Sets the price of the right standing area.
     * @param rightStandPrice The new price of the right standing area.
     */
    public void setRightStandPrice(double rightStandPrice) {
        this.rightStandPrice = rightStandPrice;
    }

    /**
     * Retrieves the price of the left VIP area.
     * @return The price of the left VIP area.
     */
    public double getLeftVIPPrice() {
        return leftVIPPrice;
    }

    /**
     * Sets the price of the left VIP area.
     * @param leftVIPPrice The new price of the left VIP area.
     */
    public void setLeftVIPPrice(double leftVIPPrice) {
        this.leftVIPPrice = leftVIPPrice;
    }

    /**
     * Retrieves the price of the middle VIP area.
     * @return The price of the middle VIP area.
     */
    public double getMiddleVIPPrice() {
        return middleVIPPrice;
    }

    /**
     * Sets the price of the middle VIP area.
     * @param middleVIPPrice The new price of the middle VIP area.
     */
    public void setMiddleVIPPrice(double middleVIPPrice) {
        this.middleVIPPrice = middleVIPPrice;
    }

    /**
     * Retrieves the price of the right VIP area.
     * @return The price of the right VIP area.
     */
    public double getRightVIPPrice() {
        return rightVIPPrice;
    }

    /**
     * Sets the price of the right VIP area.
     * @param rightVIPPrice The new price of the right VIP area.
     */
    public void setRightVIPPrice(double rightVIPPrice) {
        this.rightVIPPrice = rightVIPPrice;
    }
}