package User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import Constants.Constants;
import Handler.FileHandler;
import AllConcert.*;

/**
 * Customer which can book the ticket and use the ticket system
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class Customer extends User{

    /**
     * use the default constructor
     */

    /**
     * customer will have name, id, password
     */

    private String Name;
    private int Id;
    private String password;

    /**
     * set the id of customer
     * @param id its id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * set the new customer
     * @param scanner read input
     */
    public void setCustomer(Scanner scanner){
        System.out.print("Enter your name: ");
        this.Name = scanner.nextLine();
        System.out.print("Enter your password: ");
        this.password = scanner.nextLine();
    }

    /**
     * get the customer password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * check the option, and do the judgement
     * @param scanner the input
     * @param concertDetails the concerts
     * @param concertNumber the concert number
     * @param bookingInformation the booking csv information
     * @param bookFile the address of booling file
     * @throws IOException input and output exception
     */

    public void concertOptionJudgement(Scanner scanner, ConcertDetails concertDetails, int concertNumber, ArrayList<String> bookingInformation, String bookFile) throws IOException {

        int optionForConcert = 0;
        while (optionForConcert!=Constants.EXIT) {
            concertOptionPanel();
            optionForConcert = scanner.nextInt();
            switch (optionForConcert) {
                case Constants.SHOW_TIMING:
                    concertDetails.showAllPrice(concertNumber);
                    break;
                case Constants.CUSTOMER_SHOW_PRICE:
                    concertDetails.getConcerts().get(concertNumber).getVenue().printVenue();
                    break;
                case Constants.CUSTOMER_BOOKING:
                    concertDetails.getConcerts().get(concertNumber).getVenue().printVenue();
                    String[] bookedTicketInformationeatSelection= readAisleSelection(scanner);
                    bookedWriteInFile(bookedTicketInformationeatSelection,bookFile,concertNumber,concertDetails,bookingInformation);
                    break;
                case Constants.CUSTOMER_SHOW_BOOKINGPRICE:
                    HashMap<Integer,ArrayList<String>> customerInformation = showBookingDetail(concertNumber,bookFile);
                    int bookingId = checckConcertIdBelongs(bookFile,concertNumber);
                    concertDetails.showCustomerPrice(concertNumber,customerInformation,bookingId);
                    break;
                case Constants.EXIT:
                    System.out.println("Exiting this concert");
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    /**
     * check the option of customer
     * @param keyboard input
     * @param concertDetails concerts
     * @param concertId the concertnumber
     * @param bookingInformaton the booking csv
     * @param bookFile the file of booking address
     * @throws IOException the input and output exception
     */
    public void checkOption(Scanner keyboard, ConcertDetails concertDetails, ArrayList<Integer> concertId,ArrayList<String> bookingInformaton, String bookFile) throws IOException {
        int concertNumber = 0;
        while (concertNumber != -1) {
            concertNumber = showSelectMessage(concertDetails, keyboard, concertId);
            if (concertNumber == -1) {
                System.out.println("Exiting customer mode");
            } else {
                concertOptionJudgement(keyboard, concertDetails, concertNumber,bookingInformaton,bookFile);
            }
        }
    }

    /**
     * show the detail of booking
     * @param concertNumber the concert number
     * @param bookingFile the file address
     * @return the detail information of booking
     * @throws IOException the input and output exception
     */
    public HashMap<Integer,ArrayList<String>> showBookingDetail(int concertNumber, String bookingFile) throws IOException {
        HashMap<Integer,ArrayList<String>> showBookingDetail = new HashMap<>();
        FileHandler fileHandler = new FileHandler();
        List<String> personalBooking = fileHandler.readFile(bookingFile);
        int totalLine = 0;
        for (String line: personalBooking){
            double totalPrice = 0;
            String[]booking =  line.split(",");
            int seatbooked = Integer.parseInt(booking[4]);
            if (Integer.parseInt(booking[1])==this.getId() && concertNumber+1 == Integer.parseInt(booking[3])){//check the concertid and customer id both matches
                ArrayList<String>personalConcertInformation = new ArrayList<String>();
                personalConcertInformation.add(booking[0]);
                int ticketAmount = Integer.parseInt(booking[4]);
                for (int i = 0; i < ticketAmount; i++) {//go through the booking detail and store all information in the arraylist
                    int index = 5 + 5 * i;
                    String row = booking[index + 1]; // row
                    personalConcertInformation.add(row);
                    String column = booking[index + 2];
                    personalConcertInformation.add(column);
                    String type =booking[index + 3];
                    personalConcertInformation.add(type);
                    double doubletype = Double.parseDouble(booking[index + 4]);
                    String price = String.valueOf(doubletype);
                    personalConcertInformation.add(price);
                    totalPrice+=Double.parseDouble(booking[index+4]);
                }
                personalConcertInformation.add(String.valueOf(seatbooked));
                personalConcertInformation.add(String.valueOf(totalPrice));
                totalLine++;
                showBookingDetail.put(totalLine,personalConcertInformation);
            }
        }
        return showBookingDetail;
    }


    /**
     * update the customer price
     * @param booking the information booking
     * @param concertDetails the concert details
     * @throws IOException the input and output exception
     */
    public void concertUpdate(String booking, ConcertDetails concertDetails) throws IOException {
        String[] bookinginformation = booking.split(",");
        int concertId = Integer.parseInt(bookinginformation[3]);
        int ticketAmount = Integer.parseInt(bookinginformation[4]);
        for (int i = 0; i < ticketAmount; i++) {//it need to use index to retrieve the dat store after 5
            int index = 5 + 5 * i;
            int row = (Integer.parseInt(bookinginformation[index + 1])); // row
            int column = (Integer.parseInt(bookinginformation[index + 2]));
            String type = bookinginformation[index + 3];
            concertDetails.getConcerts().get(concertId - 1).getVenue().setVenue_layout(row, column, type);
        }
    }

    /**
     * write the booking information back to file
     * @param bookedTicketInformationeatSelection the booking information
     * @param bookingFile the place it needs to wirte back
     * @param concertNumber the concert number
     * @param concertDetails the concerts
     * @param bookingInformatiuon the booking csv content
     * @throws IOException alram has exception
     */
    public void bookedWriteInFile(String[] bookedTicketInformationeatSelection, String bookingFile, int concertNumber, ConcertDetails concertDetails,ArrayList<String> bookingInformatiuon) throws IOException {
        FileHandler fileHandler = new FileHandler();
        int newbookingID = getNewBookingId(bookingInformatiuon,concertNumber+1);//need to plus one back to let the concert number correct
        int personId = this.getId();
        String customerName = this.getName();
        int row = Integer.parseInt(bookedTicketInformationeatSelection[1]);
        int column = Integer.parseInt(bookedTicketInformationeatSelection[2]);
        int totalTicket = Integer.parseInt(bookedTicketInformationeatSelection[3]);
        String type = bookedTicketInformationeatSelection[0];
        String booking = newbookingID + "," + personId + "," + customerName + "," + (concertNumber + 1) + "," + totalTicket + ",";
        double price = 0;
        int leftColumn = concertDetails.getConcerts().get(concertNumber).getVenue().getLeftColumn();
        int middleColumn = concertDetails.getConcerts().get(concertNumber).getVenue().getMiddleColumn();
        String seatType = "";
        for (int i = 0; i < totalTicket; i++) {
            //check the type
            switch (type) {
                case "V"://check the position to determine the price
                    if (column < leftColumn+1){
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getLeftVIPPrice();
                    }else if (column <= leftColumn+middleColumn){
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getMiddleVIPPrice();
                    }else{
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getRightVIPPrice();
                    }
                    seatType = "VIP";
                    break;
                case "T"://check the position to determine the price
                    if (column < leftColumn+1){
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getLeftStandPrice();
                    }else if (column <= leftColumn+middleColumn){
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getMiddleStandPrice();
                    }else{
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getRightStandPrice();
                    }
                    seatType = "STANDING";
                    break;
                case "S"://check the position to determine the price
                    if (column < leftColumn+1){
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getLeftSeatPrice();
                    }else if (column <= leftColumn+middleColumn){
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getMiddleSeatPrice();
                    }else{
                        price = concertDetails.getConcerts().get(concertNumber).getPrice().getRightSeatPrice();
                    }
                    seatType = "SEATING";
                    break;
            }
            //put them into the correct foramt
            booking += (i + 1) + ",";
            booking += row + ",";
            booking += (column) + ",";
            column+=1;
            booking += seatType + ",";
            if (i == totalTicket - 1) {
                booking += price;
            } else {
                booking += price + ",";
            }
        }
        fileHandler.saveFile(bookingFile,booking);
        concertUpdate(booking,concertDetails);
    }

    /**
     * get the neww booking id
     * @param bookingInformation the booking csv content
     * @param concertNumber the concert number
     * @return the new id
     */
    public int getNewBookingId(ArrayList<String> bookingInformation ,int concertNumber){
        int count = 0;
        for (String line : bookingInformation) {
            String[] information = line.split(",");
            if (Integer.parseInt(information[1]) == this.getId() && Integer.parseInt(information[3]) == concertNumber) {
                if (Integer.parseInt(information[0]) > count) {
                    count = Integer.parseInt(information[0]);
                }
            }
        }
        return count + 1;//the rule is customer and concert is a pair
    }

    /**
     * when customer book the concert
     * @param scanner read the input
     * @return ticket array which store the type, row, column, seat number
     */
    public String[] readAisleSelection(Scanner scanner){
        System.out.print("Enter the aisle number: ");
        String aisleNumber = scanner.next();
        char type = aisleNumber.charAt(0);
        char row = aisleNumber.charAt(1);
        System.out.print("Enter the seat number: ");
        String column = scanner.next();
        String []ticket = new String[4];
        System.out.print("Enter the number of seats to be booked: ");
        String numberOfSeat = scanner.next(); // read tge seat number
        ticket[0] = String.valueOf(type);
        ticket[1] = String.valueOf(row);
        ticket[2] = column;
        ticket[3] = numberOfSeat; // store the value of seat number
        return ticket;
    }
    /**
     * used to show the main Meny of the user(customer)
     */
    @Override
    public void concertOptionPanel(){
        System.out.println("Select an option to get started!");
        System.out.println("Press 1 to look at the ticket costs");
        System.out.println("Press 2 to view seats layout");
        System.out.println("Press 3 to book seats");
        System.out.println("Press 4 to view booking details");
        System.out.println("Press 5 to exit");
        System.out.print("> ");
    }

    /**
     * check the concertbelongs to who
     * @param bookFilePath the address of the book file
     * @param concertNumber the concert number
     * @return the concertId
     * @throws IOException input and output alarm
     */
    public int checckConcertIdBelongs(String bookFilePath,int concertNumber) throws IOException {
        FileHandler fileHandler = new FileHandler();
        List<String> concertBelong = fileHandler.readFile(bookFilePath);
        for (String line: concertBelong) {
            String[] concertInformation = line.split(",");
            if (((concertNumber+1) == Integer.parseInt(concertInformation[3])) && (this.getId() == Integer.parseInt(concertInformation[1]))) {
                return(Integer.valueOf(concertInformation[0]));//if matches
            }
        }
        return -1;//not belongs
    }
    /**
     * sahow the select message to hint the customer input
     * @param concertDetails the concerts
     * @param keyboard the input
     * @param concertId the concertud
     * @return concertNumber the concertnumber user choice
     */
    @Override
    public int showSelectMessage(ConcertDetails concertDetails, Scanner keyboard, ArrayList<Integer> concertId) {
        System.out.println("Select a concert or 0 to exit");
        concertDetails.showConcertDetails(concertId);
        System.out.print("> ");
        int concertNumber = readCustomerInput(keyboard);
        return concertNumber;
    }

    /**
     * Retrieves the name of the object.
     * This method returns the current name stored in the Name field.
     *
     * @return The current name of the object.
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the name of the object.
     * This method assigns a new name to the Name field.
     *
     * @param name The new name to be set for the object.
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Retrieves the unique identifier of the object.
     * This method returns the current ID stored in the Id field.
     *
     * @return The unique identifier of the object.
     */
    public int getId() {
        return Id;
    }
}