package AllConcert;

import Constants.Constants;

/**
 * The Venue class store the seat layout.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */

public class Venue {
    // define what data fields will be used to define the venue layout
    /**
     * the venue will have row, leftColumn, middleColumn, rightColumn and totalSeats,
     * it will have a layout to set the current seat which have been choiced, and also I have the total column for each row, and I separate all level in my venu class
     */
    private int row;
    private int leftColumn;
    private int middleColumn;
    private int rightColumn;
    private int totalSeats;
    private char[][] venue_layout;
    private int remainSeat;
    private int bookedSeat;
    private int bookedLeftVIPSeat ;
    private int bookedMiddleVIPSeat;
    private int bookedRightVIPSeat;
    private int bookedSeatingLeftSeat;
    private int bookedSeatingMiddleSeat;
    private int bookedSeatingRightSeat;
    private int bookedStandingLeftSeat;
    private int bookedStandingMiddleSeat;
    private int bookedStandingRightSeat;
    private int totalColumn;
    private int vipTotalRow;
    private int seatingTotalRow;
    private int standingTotalRow;
    private int seatingStartRow;

    private int standingStartRow;


    /**
     * Construct the Venue, and make the totalSeats be defined, and initialize the booked left, right, middle seat is 0
     * we also have the remainSeat, initially it will be as same as total seats, due to nobody booked yet
     * @param row          put the row in the layout
     * @param leftColumn   put the rightColumn in the layout
     * @param middleColumn put the leftColumn in the layout
     * @param rightColumn  put the middleColumn in the layout
     * @param vipTotalRow  the total row if vip
     * @param seatingTotalRow  the total row if seating
     * @param standingTotalRow the total standing row
     */
    // define constructors
    public Venue(int row, int leftColumn, int middleColumn, int rightColumn, int vipTotalRow, int seatingTotalRow, int standingTotalRow) {
        setRow(row);
        setLeftColumn(leftColumn);
        setMiddleColumn(middleColumn);
        setRightColumn(rightColumn);
        setTotalSeats(row, leftColumn, middleColumn, rightColumn);
        bookedLeftVIPSeat = 0;
        bookedMiddleVIPSeat = 0;
        bookedRightVIPSeat = 0;
        bookedSeatingLeftSeat = 0;
        bookedSeatingMiddleSeat = 0;
        bookedSeatingRightSeat = 0;
        bookedStandingLeftSeat = 0;
        bookedStandingMiddleSeat = 0;
        bookedStandingRightSeat = 0;
        remainSeat = totalSeats;
        bookedSeat = bookedLeftVIPSeat + bookedMiddleVIPSeat + bookedRightVIPSeat + bookedSeatingLeftSeat + bookedSeatingMiddleSeat + bookedSeatingRightSeat + bookedStandingLeftSeat +bookedStandingMiddleSeat + bookedStandingRightSeat;
        totalColumn = leftColumn + middleColumn + rightColumn;//the total column of one row include _ and space
        venue_layout = new char[row][totalColumn];// create the 2D array to store the information of seat
        setVipTotalRow(vipTotalRow);
        setSeatingTotalRow(seatingTotalRow);
        setStandingTotalRow(standingTotalRow);
        seatingStartRow = vipTotalRow+1;
        standingStartRow = vipTotalRow + seatingTotalRow+1;
    }


    //set venue layout


    /**
     * set the initial layout of venue without anyone booked
     * @param row the total row
     * @param column the column of all
     * @param type the type of booking
     */
    public void setVenue_layout(int row, int column, String type) {
        switch (type){//check each type, and put x in the layout, when it is booked, and update the remain seat, booked seat altogether
            case "VIP":
                venue_layout[row-1][column-1] ='X';
                if (column<leftColumn){
                    bookedLeftVIPSeat++;
                    bookedSeat++;
                    remainSeat--;
                }else if(column < leftColumn+middleColumn){
                    bookedMiddleVIPSeat++;
                    bookedSeat++;
                    remainSeat--;
                }else{
                    bookedRightVIPSeat++;
                    bookedSeat++;
                    remainSeat--;
                }
                break;
            case "SEATING":
                venue_layout[seatingStartRow+row-2][column-1] = 'X';
                if (column<leftColumn){
                    bookedSeatingLeftSeat++;
                    bookedSeat++;
                    remainSeat--;
                }else if(column < leftColumn+middleColumn){
                    bookedSeatingMiddleSeat++;
                    bookedSeat++;
                    remainSeat--;
                }else{
                    bookedSeatingRightSeat++;
                    bookedSeat++;
                    remainSeat--;
                }
                break;
            case "STANDING":
                venue_layout[standingStartRow+row-2][column-1] = 'X';
                if (column < leftColumn){
                    bookedStandingLeftSeat++;
                    bookedSeat++;
                    remainSeat--;
                }else if(column < leftColumn + middleColumn){
                    bookedStandingMiddleSeat++;
                    bookedSeat++;
                    remainSeat--;
                }else{
                    bookedStandingRightSeat++;
                    bookedSeat++;
                    remainSeat--;
                }
                break;
        }
    }

    /**
     * Print the recent venue booking situation
     */
    // print all the venue
    public void printVenue() {
        for (int i = 0; i < row; i++) {
            if (i ==vipTotalRow || i == standingStartRow-1){
                System.out.println();
            }//if finish the VIP part jump to the next line
            if (i <vipTotalRow){
                System.out.printf("%s%d ", Constants.VIP,i+1);
            }
            else if (i >= seatingStartRow -1&& i <seatingStartRow+seatingTotalRow - 1){
                System.out.printf("%s%d ", Constants.SEATING,i-seatingStartRow + 2);
            }
            else {
                System.out.printf("%s%d ", Constants.STANDING,i-standingStartRow + 2);
            }
            for (int j = 0; j < totalColumn; j++) {
                if (j == leftColumn ||j == leftColumn+middleColumn){
                    System.out.print(" ");
                }
                System.out.print('[');
                if (venue_layout[i][j] == 'X'){
                    System.out.print('X');
                }
                else{
                    System.out.print(j+1);
                }
                System.out.print(']');
            }
            if (i <vipTotalRow){
                System.out.printf(" %s%d", Constants.VIP,i+1);
            }
            else if (i >= seatingStartRow -1 && i <seatingStartRow+seatingTotalRow - 1){
                System.out.printf(" %s%d", Constants.SEATING,i-seatingStartRow+2);
            }
            else {
                System.out.printf(" %s%d", Constants.STANDING,i-standingStartRow+2);
            }
            System.out.println();
        }
    }

    /**
     * get the total seat of venue
     *
     * @return all seats
     */
    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * set the row of concert
     *
     * @param row the row of concert
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * set the leftColumn of concert
     *
     * @param leftColumn the left column of concert
     */
    public void setLeftColumn(int leftColumn) {
        this.leftColumn = leftColumn;
    }

    /**
     * get the all booked seat of venue
     *
     * @return bookedseat in concert
     */
    public int getBookedSeat() {
        return bookedSeat;
    }

    /**
     * set the middleColumn of concert
     *
     * @param middleColumn the middle column of concert
     */
    public void setMiddleColumn(int middleColumn) {
        this.middleColumn = middleColumn;
    }

    /**
     * set the rightColumn of venue
     *
     * @param rightColumn the right column of concert
     */
    public void setRightColumn(int rightColumn) {
        this.rightColumn = rightColumn;
    }

    /**
     * set the total seats, by row*(rightColumn + leftColumn + middleColumn)
     *
     * @param row          put the row in the layout
     * @param middleColumn put the leftColumn in the layout
     * @param rightColumn  put the middleColumn in the layout
     * @param leftColumn   put the rightColumn in the layout
     */
    public void setTotalSeats(int row, int leftColumn, int middleColumn, int rightColumn) {
        this.totalSeats = row * (rightColumn + leftColumn + middleColumn);
    }


    /**
     * get the venu 2D layout
     *
     * @return the whole venue layout
     */
    public char[][] getVenue_layout() {
        return venue_layout;
    }

    /**
     * get the remain seat which is not being booked
     *
     * @return the concert remainseat
     */
    public int getRemainSeat() {
        return remainSeat;
    }

    /**
     * Retrieves the total number of rows in the VIP area.
     * @return The total number of VIP rows.
     */
    public int getVipTotalRow() {
        return vipTotalRow;
    }

    /**
     * Sets the total number of rows in the VIP area.
     * @param vipTotalRow The new total number of VIP rows.
     */
    public void setVipTotalRow(int vipTotalRow) {
        this.vipTotalRow = vipTotalRow;
    }

    /**
     * Sets the total number of rows in the seating area.
     * @param seatingTotalRow The new total number of seating rows.
     */
    public void setSeatingTotalRow(int seatingTotalRow) {
        this.seatingTotalRow = seatingTotalRow;
    }

    /**
     * Retrieves the total number of rows in the standing area.
     * @return The total number of standing rows.
     */
    public int getStandingTotalRow() {
        return standingTotalRow;
    }

    /**
     * Sets the total number of rows in the standing area.
     * @param standingTotalRow The new total number of standing rows.
     */
    public void setStandingTotalRow(int standingTotalRow) {
        this.standingTotalRow = standingTotalRow;
    }

    /**
     * Retrieves the starting row number for the standing area.
     * @return The starting row number for standing.
     */
    public int getStandingStartRow() {
        return standingStartRow;
    }

    /**
     * Retrieves the number of columns in the left section of the venue.
     * @return The number of columns in the left section.
     */
    public int getLeftColumn() {
        return leftColumn;
    }

    /**
     * Retrieves the number of columns in the middle section of the venue.
     * @return The number of columns in the middle section.
     */
    public int getMiddleColumn() {
        return middleColumn;
    }

}