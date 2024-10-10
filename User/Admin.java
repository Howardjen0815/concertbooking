package User;
import AllConcert.*;
import Constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The Admin class store each concert information.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class Admin extends User{

    /**
     * admin option
     */
    // extend this class to inherit from User and write appropriate admin related methods here
    private int optionAdmin = 0;

    /**
     * used for the default constructor
     */

    /**
     *  used to let the admin select message
     * @param concertDetails the concert details for read
     * @param keyboard let the user read the input
     * @param concertId read the concertid
     * @return the concert number
     */
    @Override
    public int showSelectMessage(ConcertDetails concertDetails, Scanner keyboard, ArrayList<Integer> concertId) {
        System.out.println("Select a concert or 0 to exit");
        concertDetails.showConcertDetails(concertId);
        System.out.print("> ");
        int concertNumber = readCustomerInput(keyboard);
        if (concertNumber == -1) {
            System.out.println("Exiting admin mode");
        }
        return concertNumber;
    }

    /**
     * Check the admin input which option
     * @param scanner        used to read the admin input
     * @param concertDetails access to concerts
     * @param concertAllid the concertid which stored in arraylist
     * @param bookingDetail the booking detail which stored in the array
     */
    public void checkOption(Scanner scanner, ConcertDetails concertDetails, ArrayList<Integer> concertAllid, ArrayList<String> bookingDetail){
        while (optionAdmin  != Constants.EXIT){
            concertOptionPanel();
            optionAdmin  = scanner.nextInt();
            switch (optionAdmin){
                case Constants.SHOW_TIMING:// as same as customer
                    concertDetails.showConcertDetails(concertAllid);//shwo the price
                    break;
                case Constants.ADMIN_UPDATE_PRICE:
                    int concertNumber = showSelectMessage(concertDetails,scanner,concertAllid);//get the copncert number need to be update, this number is the user input -1
                    updateConcertPrice(scanner,concertNumber,concertDetails);
                    break;
                case Constants.ADMIN_VIEW_BOOKING_DETAIL:
                    int concertNumberDetail = showSelectMessage(concertDetails,scanner,concertAllid);//get the concert number, this number is the user input -1
                    ArrayList<Integer> cusomerAllBookingid = checkAllConcertId(concertNumberDetail,bookingDetail);//stor all booking id
                    HashMap<Integer,ArrayList<String>> customerInformation = showBookingDetail(concertNumberDetail,bookingDetail);
                    concertDetails.showAllCustomerPrice(concertNumberDetail,customerInformation,cusomerAllBookingid,bookingDetail);
                    break;
                case Constants.ADMIN_VIEW_TOTAL_PAYMENT:
                    int concertTotalPaymentChoice = showSelectMessage(concertDetails,scanner,concertAllid);//get the concert number
                    checkBookingPrice(bookingDetail,concertTotalPaymentChoice,concertDetails,concertAllid);
                    break;
                case Constants.EXIT:
                    System.out.println("Exiting admin mode");
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    /**
     * check the booking price of concert
     * @param bookingDetail the information of booking csv
     * @param concertTotalPaymentChoice the concert number
     * @param concertDetails the concerts
     * @param concertAllid all the concert id
     */

    public void checkBookingPrice(ArrayList<String> bookingDetail,int concertTotalPaymentChoice,ConcertDetails concertDetails,ArrayList<Integer> concertAllid){
        double price = 0;
        for (String line: bookingDetail){
            String[] data = line.split(",");
            int ticketAmount = Integer.parseInt(data[4]);
            if (concertTotalPaymentChoice+1 ==Integer.parseInt(data[3])){
                for (int i = 0; i < ticketAmount; i++) {
                    int index = 5 + 5 * i;
                    double ticketPrice = Double.parseDouble(data[index + 4]);
                    price += ticketPrice;
                }
            }
        }
        boolean found = false;
        for (int i = 0; i < concertDetails.getConcerts().size(); i++) {
            if (concertTotalPaymentChoice == concertAllid.get(i) && price!=0) {
                System.out.printf("Total Price for this concert is AUD %.1f\n", price);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No Bookings found for this concert");
        }
    }



    /**
     *Show the admin option for choose
     */
    @Override
    public void concertOptionPanel(){
        //print the admin menu
        System.out.println("Select an option to get started!");
        System.out.println("Press 1 to view all the concert details");
        System.out.println("Press 2 to update the ticket costs");
        System.out.println("Press 3 to view booking details");
        System.out.println("Press 4 to view total payment received for a concert");
        System.out.println("Press 5 to exit");
        System.out.print("> ");
    }


    /**
     * read the admin input of new price
     * @param scanner read the admin input
     * @param concertNumber the concert which we want to update
     * @param concertDetails the detail of the concerts
     */
    public void updateConcertPrice(Scanner scanner, int concertNumber, ConcertDetails concertDetails){
        concertDetails.showAllPrice(concertNumber);
        System.out.print("Enter the zone : VIP, SEATING, STANDING: ");
        String level = scanner.next();
        System.out.println();
        System.out.print("Left zone price: ");
        double leftNewPrice = scanner.nextDouble();
        System.out.print("Centre zone price: ");
        double middleNewPrice = scanner.nextDouble();
        System.out.print("Right zone price: ");
        double rightNewPrice = scanner.nextDouble();
        switch (level){//check which area nned to be update, and update that
            case "VIP":
                concertDetails.getConcerts().get(concertNumber).getPrice().setLeftVIPPrice(leftNewPrice);
                concertDetails.getConcerts().get(concertNumber).getPrice().setMiddleVIPPrice(middleNewPrice);
                concertDetails.getConcerts().get(concertNumber).getPrice().setRightVIPPrice(rightNewPrice);
                break;
            case "SEATING":
                concertDetails.getConcerts().get(concertNumber).getPrice().setLeftSeatPrice(leftNewPrice);
                concertDetails.getConcerts().get(concertNumber).getPrice().setMiddleSeatPrice(middleNewPrice);
                concertDetails.getConcerts().get(concertNumber).getPrice().setRightSeatPrice(rightNewPrice);
                break;
            case "STANDING":
                concertDetails.getConcerts().get(concertNumber).getPrice().setLeftStandPrice(leftNewPrice);
                concertDetails.getConcerts().get(concertNumber).getPrice().setMiddleStandPrice(middleNewPrice);
                concertDetails.getConcerts().get(concertNumber).getPrice().setRightStandPrice(rightNewPrice);
                break;

        }
    }

    /**
     * show the booking detail of concert
     * @param concertNumber the concert number which has been chouce
     * @param bookingInformation the booking information
     * @return the detail of the concert include row, column, type, price, seatBooked, totalprice
     */
    public HashMap<Integer,ArrayList<String>> showBookingDetail(int concertNumber,ArrayList<String> bookingInformation){
        HashMap<Integer,ArrayList<String>> showBookingDetail = new HashMap<>();
        int totalLine = 0;
        for (String line: bookingInformation){
            double totalPrice = 0;
            String[]booking =  line.split(",");
            int seatbooked = Integer.parseInt(booking[4]);
            if (concertNumber+1 == Integer.parseInt(booking[3])){//need to plus one back
                ArrayList<String>allConcertInformation = new ArrayList<String>();
                allConcertInformation.add(booking[0]);
                int ticketAmount = Integer.parseInt(booking[4]);
                for (int i = 0; i < ticketAmount; i++) {
                    int index = 5 + 5 * i;
                    String row = booking[index + 1]; // row
                    allConcertInformation.add(row);
                    String column = booking[index + 2];
                    allConcertInformation.add(column);
                    String type =booking[index + 3];
                    allConcertInformation.add(type);
                    double doubletype = Double.parseDouble(booking[index + 4]);
                    String price = String.valueOf(doubletype);
                    allConcertInformation.add(price);
                    totalPrice+=Double.parseDouble(booking[index+4]);
                }
                allConcertInformation.add(String.valueOf(seatbooked));
                allConcertInformation.add(String.valueOf(totalPrice));
                totalLine++;
                showBookingDetail.put(totalLine,allConcertInformation);
            }//include all the information into the hashmap
        }
        return showBookingDetail;
    }

    /**
     * check all concert id which match the customer id
     * @param concertNumber the concert number which adin chose
     * @param bookingInformation the booking information of
     * @return the all concert id belongs to that customer
     */
    public ArrayList<Integer> checkAllConcertId(int concertNumber, ArrayList<String> bookingInformation){
        HashMap<Integer, Integer> customerIdCount = new HashMap<>();
        ArrayList<Integer> concertAllid = new ArrayList<>();
        for (String information : bookingInformation) {
            String[] line = information.split(",");
            if (concertNumber + 1 == Integer.parseInt(line[3])) {
                int customerId = Integer.parseInt(line[1]);
                if (customerIdCount.containsKey(customerId)) {
                    int newCount = customerIdCount.get(customerId) + 1;
                    customerIdCount.put(customerId, newCount);//the customer id is key, count is the value
                    concertAllid.add(newCount);
                } else {
                    customerIdCount.put(customerId, 1);
                    concertAllid.add(1);
                }
            }
        }
        return concertAllid;
    }

}