package AllConcert;
import Constants.Constants;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ConcertDetails class store all concert information.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class ConcertDetails {


    /**
     * use to store all the concert in concerts array
     */
    private final ArrayList<Concert> concerts = new ArrayList<>();
    // The Concert array store the concert, I am not going to let the user get the whole array, but I will let admin and customer can change each concert information


    /**
     * @synopsis
     */

    /**
     * Show all the detail of concert information in concerts array
     * @param concertId the concert id
     */
    public void showConcertDetails(ArrayList<Integer> concertId) {//place the concertNumber form concert.csv, should be arraylist
        //System.out.println("Show Timings");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.printf(Constants.CONCERT_FORMAT, "#", "Date", "Artist Name", "Timing", "Venue Name", "Total Seats", "Seats Booked", "Seats Left");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < concertId.size(); i++) {
            System.out.printf(Constants.CONCERT_FORMAT, concertId.get(i), concerts.get(i).getDate().getConcertDate(), concerts.get(i).getArtistName(), concerts.get(i).getDate().getConcertTime(), concerts.get(i).getPlace(), concerts.get(i).getVenue().getTotalSeats(), concerts.get(i).getVenue().getBookedSeat(), concerts.get(i).getVenue().getRemainSeat());
        }//used to print all the concert information, the reason why i +1 because the concert1 is stored in index 0
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Show each zone price of the concert
     * @param concertNumber is the user input which concert want to check the price
     */
    public void showAllPrice(int concertNumber) {
        System.out.printf(Constants.PRICE_STATEMENT_FORMAT, "STANDING");
        System.out.println("Left Seats:   "+ concerts.get(concertNumber).getPrice().getLeftStandPrice());//print the left price
        System.out.println("Center Seats: "+ concerts.get(concertNumber).getPrice().getMiddleStandPrice());//print the middle price
        System.out.println("Right Seats:  "+ concerts.get(concertNumber).getPrice().getRightStandPrice()); //print the right price
        System.out.println("------------------------------");
        System.out.printf(Constants.PRICE_STATEMENT_FORMAT, "SEATING");
        System.out.println("Left Seats:   "+ concerts.get(concertNumber).getPrice().getLeftSeatPrice());//print the left price
        System.out.println("Center Seats: "+ concerts.get(concertNumber).getPrice().getMiddleSeatPrice());//print the middle price
        System.out.println("Right Seats:  "+ concerts.get(concertNumber).getPrice().getRightSeatPrice()); //print the right price
        System.out.println("------------------------------");
        System.out.printf(Constants.PRICE_STATEMENT_FORMAT, "VIP");
        System.out.println("Left Seats:   "+ concerts.get(concertNumber).getPrice().getLeftVIPPrice());//print the left price
        System.out.println("Center Seats: "+ concerts.get(concertNumber).getPrice().getMiddleVIPPrice());//print the middle price
        System.out.println("Right Seats:  "+ concerts.get(concertNumber).getPrice().getRightVIPPrice()); //print the right price
        System.out.println("------------------------------");
    }

    /**
     * show the customer booking price for themselves.
     * @param concertNumber user choice
     * @param showBookingDetail the hashmap used to store the booking information
     * @param bookingId the bookingid Of the concert
     */
    public void showCustomerPrice(int concertNumber, HashMap<Integer,ArrayList<String>> showBookingDetail, int bookingId){
        if(bookingId==-1){
            System.out.println("No Bookings found for this concert");
            System.out.println();
        }//press 0 and exit
        else{
            System.out.println("Bookings");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.printf(Constants.BOOKINGPRICELIST, "Id", "Concert Date", "Artist Name", "Timing", "Venue Name", "Seats Booked", "Total Price");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            for(int i = 1; i <=showBookingDetail.size(); i++) {
                System.out.printf(Constants.BOOKINGPRICELIST, bookingId+i-1, concerts.get(concertNumber).getDate().getConcertDate(), concerts.get(concertNumber).getArtistName(), concerts.get(concertNumber).getDate().getConcertTime(), concerts.get(concertNumber).getPlace(),showBookingDetail.get(i).get(showBookingDetail.get(i).size()-2), showBookingDetail.get(i).get(showBookingDetail.get(i).size()-1));
            }//get the booking detail of concert
            //used to print all the concert information, the reason why i +1 because the concert1 is stored in index 0
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("Ticket Info");//Print the each booking id of concert
            for(int i = 0; i <showBookingDetail.size();i++){
                System.out.printf("############### Booking Id: %s ####################\n",bookingId+i);
                System.out.printf(Constants.TICKETINFOLIST, "Id", "Aisle Number", "Seat Number", "Seat Type", "Price");
                System.out.println("##################################################");
                for (int j = 0; j < showBookingDetail.get(i+1).size()/4;j++){//need to divide 4 because it need to seperate the array to the each seat part which contain row, column, type, price
                    int index = j + 3*j;
                    System.out.printf(Constants.TICKETINFOLIST, j+1, showBookingDetail.get(i+1).get(index+1), showBookingDetail.get(i+1).get(index+2), showBookingDetail.get(i+1).get(index+3), showBookingDetail.get(i+1).get(index+4));
                }
                System.out.println("##################################################");
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * show the concert for admin, it needs to read all the concert, and concertId and userid should match
     * @param concertNumber user choice
     * @param showBookingDetail the hashmap used to store the booking information
     * @param customerAllId all concertId in bookings
     * @param bookingFileInfortmation the content which is booking csv
     */
    public void showAllCustomerPrice(int concertNumber, HashMap<Integer,ArrayList<String>> showBookingDetail, ArrayList<Integer> customerAllId, ArrayList<String> bookingFileInfortmation){
        if(concerts.get(concertNumber).getVenue().getBookedSeat()==0){
            System.out.println("No Bookings found for this concert");
            System.out.println();
        }
        else {
            System.out.println("Bookings");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.printf(Constants.BOOKINGPRICELIST, "Id", "Concert Date", "Artist Name", "Timing", "Venue Name", "Seats Booked", "Total Price");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            //印一個在印一個
            ArrayList<String> bookingIdArray = new ArrayList<>();
            for(int i = 1; i < bookingFileInfortmation.size()+1; i++) {
                String concertId = bookingFileInfortmation.get(i-1).split(",")[3];
                String id = bookingFileInfortmation.get(i-1).split(",")[0];
                if(concertNumber+1 == Integer.parseInt(concertId)){
                    bookingIdArray.add(id);
                }
            }

            for(int i = 1; i <=customerAllId.size(); i++) {
                System.out.printf(Constants.BOOKINGPRICELIST, bookingIdArray.get(i-1), concerts.get(concertNumber).getDate().getConcertDate(), concerts.get(concertNumber).getArtistName(), concerts.get(concertNumber).getDate().getConcertTime(), concerts.get(concertNumber).getPlace(),showBookingDetail.get(i).get(showBookingDetail.get(i).size()-2), showBookingDetail.get(i).get(showBookingDetail.get(i).size()-1));
            }
            //used to print all the concert information, the reason why i +1 because the concert1 is stored in index 0
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("Ticket Info");
            ArrayList<String> bookingIdDetailArray = new ArrayList<>();
            for(int i = 1; i < bookingFileInfortmation.size()+1;i++){
                String concertId = bookingFileInfortmation.get(i-1).split(",")[3];
                String id = bookingFileInfortmation.get(i-1).split(",")[0];
                if(concertNumber+1 == Integer.parseInt(concertId)){
                    bookingIdDetailArray.add(id);
                }
            }

            for(int i = 0; i <showBookingDetail.size();i++){
                System.out.printf("############### Booking Id: %s ####################\n",bookingIdDetailArray.get(i));
                System.out.printf(Constants.TICKETINFOLIST, "Id", "Aisle Number", "Seat Number", "Seat Type", "Price");
                System.out.println("##################################################");
                for (int j = 0; j < showBookingDetail.get(i+1).size()/4;j++){//need to divide 4 because it need to seperate the array to the each seat part which contain row, column, type, price
                    int index = j + 3*j;
                    System.out.printf(Constants.TICKETINFOLIST, j+1, showBookingDetail.get(i+1).get(index+1), showBookingDetail.get(i+1).get(index+2), showBookingDetail.get(i+1).get(index+3), showBookingDetail.get(i+1).get(index+4));
                }
                System.out.println("##################################################");
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * get the concert array, and due to the customer is going to change the detail of the customer, so it is not going to be private leak
     * @return concerts array
     */
    public ArrayList<Concert> getConcerts() {
        return concerts;
    }
}

