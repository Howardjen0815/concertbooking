package Handler;
import AllConcert.*;
import ExceptionFamily.*;
import User.Customer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The UserHandler handle the judgement of customer is new or existed, and the admin, also it is in charge of the file verify handler
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class UserHandler {

    /**
     * the user handler will have concertDetails book filePath, all booking concert AllId, and booking information, after they verified and send back to the
     * management machine so that the user can use the correct information
     */
    private ConcertDetails concertDetails;//need to preload information, and update the information and preload it
    private String bookFilePath;
    private ArrayList<Integer> concertAllId;

    private ArrayList<String> bookingInformation;//store the information of the booking csv


    /**
     * constructor of the userHandler, initalize all fields
     */
    public UserHandler() {
        concertDetails =new ConcertDetails();
        bookFilePath = "";
        concertAllId = new ArrayList<>();
        bookingInformation = new ArrayList<>();
    }

    /**
     * in charge of the admin file judgement
     * @param args read the commandLine
     * @param fileHandler need the file handler to read the file
     * @throws IOException if IO problem happen throw exception
     * @throws InvalidFormatException if format incorrect throw exception
     */
    public void handleAdmin(String[] args, FileHandler fileHandler) throws IOException, InvalidFormatException{
        fileHandler.readFile(args[1]);//check customer csv exist or not.
        String customerFilePath = args[1];
        String concertFilePath = args[2];
        this.bookFilePath = args[3];//stor the booking csv file address
        ArrayList<String> concertInformation = checkConcertValid(fileHandler,concertFilePath);//store the concert informaion in the arraylist
        this.bookingInformation= checkBookingValid(fileHandler,bookFilePath);//read the booking information in to an ArrayList, to easily get the detail back
        this.concertAllId= new ArrayList<>();
        for(String id:concertInformation){//retrieve all concert id
            concertAllId.add(Integer.parseInt(String.valueOf(id.charAt(0))));//transfer them to Integer
        }
        ArrayList<String> customerInformation = checkCustomerValid(fileHandler,customerFilePath);//check the customer information correct or not
        HashMap<String, ArrayList<Integer>> concertVenueMap = new HashMap<>(); //use hash map to store all inforamtion in booking csv
        ArrayList<Integer> defaultvalue = venueDetailsCount("assets/venue_default.txt");//store the default venue in the arraylist
        HashMap<String,Boolean> venueHouse = new HashMap<String, Boolean>();
        concertVenueMap.put("default", defaultvalue);
        //if venue exist put them into the venue house
        for (String concert:concertInformation){
            String venue = concert.split(",")[4];
            venueHouse.put(venue, true);
        }
        //verfy the venue csv is in the concert venue or not , if do not have then load the default
        for(int i = 4; i< args.length; i++){
            String venue = (args[i].split("\\.")[0].substring(13).toUpperCase());
            if(!venueHouse.containsKey(venue)){
                continue;
            }
            ArrayList<Integer> venuedetails = venueDetailsCount(args[i]);
            concertVenueMap.put(venue,venuedetails);//will have the key pair value({venueName:concertInformation})
        }
        this.concertDetails = preLoadConcertDetails(concertVenueMap, concertInformation);//load the concert detail in to the concerts
        bookingVenueInitialize(this.concertDetails,this.bookingInformation);//key is concert id(input), array[0] is customer id array[1] is  row,array[2] is column, array[3] is zone type 0 means VIP,1 means SEATING, 2 means STANDING
        System.out.println("Welcome to Ticket Management System Admin Mode.");
    }

    /**
     * in charge of the customer verify part, it helps to check the customer mode all file is coorect or not and also initialize all information
     * @param args read the commandLine
     * @param fileHandler need the filehandler to read the file
     * @param keyboard read the user input when it is new customer
     * @param customer update the cusstomer information
     * @throws IOException input and output exception
     * @throws IncorrectPasswordException incrroectpassword
     * @throws NotFoundException not found user
     * @throws InvalidFormatException format incorrect
     */
    public void handleCustomer(String[] args, FileHandler fileHandler, Scanner keyboard,Customer customer) throws IOException, IncorrectPasswordException, NotFoundException, InvalidFormatException{
        fileHandler.readFile(args[3]);//check customer csv exist or not.
        boolean registeredCustomer = checkCustomerNew(args[1]);//juge the customer is new or existed
        if (registeredCustomer) {
            int id = Integer.parseInt(args[1]);
            String password = args[2];
            String customerPath = args[3];
            boolean customerIdExist = checkCustomerExist(fileHandler, customerPath, id);//check customer exist or not
            if (!customerIdExist) {
                throw new NotFoundException();//no exist throw the exception
            } else {
                fileHandler.checkCustomer(id, password, customerPath);//check the customer id and password match or not
                customer.setId(id);
                customer.setName(getName(fileHandler, customerPath, id));
                String concertFilePath = args[4];
                this.bookFilePath = args[5];
                ArrayList<String> concertInformation = checkConcertValid(fileHandler,concertFilePath);//check the concert file
                this.bookingInformation= checkBookingValid(fileHandler,this.bookFilePath);//check and store the booking csv in to ArrayList
                for(String idOfConcert:concertInformation){//store the concert id in the arrayList
                    this.concertAllId.add(Integer.parseInt(String.valueOf(idOfConcert.charAt(0))));
                }
                HashMap<String, ArrayList<Integer>> concertVenueMap = new HashMap<>();
                ArrayList<Integer> defaultvalue = venueDetailsCount("assets/venue_default.txt");//retrieve the default venue layout
                HashMap<String,Boolean> venueHouse = new HashMap<String, Boolean>();
                concertVenueMap.put("default", defaultvalue);
                //if venue exist put them into the venue house
                for (String concert:concertInformation){
                    String venue = concert.split(",")[4];
                    venueHouse.put(venue, true);
                }
                concertVenueMap.put("default", defaultvalue);
                for (int i = 6; i < args.length; i++) {
                    String venue = (args[i].split("\\.")[0].substring(13).toUpperCase());//get the venue name without csv
                    if(!venueHouse.containsKey(venue)){
                        continue;
                    }//check it contain in the venuehouse or not
                    ArrayList<Integer> venuedetails = venueDetailsCount(args[i]);
                    concertVenueMap.put(venue, venuedetails);
                }
                this.concertDetails = preLoadConcertDetails(concertVenueMap, concertInformation);//initialize the concert
                bookingVenueInitialize(this.concertDetails,this.bookingInformation);
            }
        }
        else if(!registeredCustomer){// if it is new customer
            String customerFilePath = args[1];//check customer csv exist or not.
            fileHandler.readFile(customerFilePath);//juge the customer is new or existed
            customer.setCustomer(keyboard);
            fileHandler.writeNewId(customerFilePath, customer);//write the new id in the customer file
            String concertFilePath = args[2];
            String bookingFilePath = args[3];
            ArrayList<String> concertInformation = checkConcertValid(fileHandler,concertFilePath);//check the concert file
            this.bookingInformation= checkBookingValid(fileHandler,bookingFilePath);//check and store the booking csv in to ArrayList
            for(String idOfConcert:concertInformation){//store the concert id in the arrayList
                this.concertAllId.add(Integer.parseInt(String.valueOf(idOfConcert.charAt(0))));
            }
            HashMap<String, ArrayList<Integer>> concertVenueMap = new HashMap<>();
            ArrayList<Integer> defaultvalue = venueDetailsCount("assets/venue_default.txt");//retrieve the default venue layout
            HashMap<String,Boolean> venueHouse = new HashMap<String, Boolean>();
            concertVenueMap.put("default", defaultvalue);
            for (String concert:concertInformation){
                String venue = concert.split(",")[4];
                venueHouse.put(venue, true);
            }
            for(int i = 4; i< args.length ;i++){
                String venue = (args[i].split("\\.")[0].substring(13).toUpperCase());//get the venue name without csv
                if(!venueHouse.containsKey(venue)){
                    continue;
                }//check it contain in the venuehouse or not
                ArrayList<Integer> venuedetails = venueDetailsCount(args[i]);
                concertVenueMap.put(venue,venuedetails);
            }
            this.concertDetails = preLoadConcertDetails(concertVenueMap, concertInformation);//initialize the concert
            bookingVenueInitialize(this.concertDetails,this.bookingInformation);//key is concert id(input), array[0] is customer id array[1] is  row,array[2] is column, array[3] is zone type 0 means VIP,1 means SEATING, 2 means STANDING
        }
        else{
            throw new NotFoundException();//if the customer does not exist throw exception
        }
    }

    /**
     * store the venue layout row, let column, middle column, right column, VIP, Seating, Standing total seats
     * @param fileName the file address
     * @return the venue information
     * @throws IOException if input and output has problem throw exception
     * @throws InvalidFormatException if the format has problem throw exception
     */
    //store the detail each venue information into ArrayList
    public ArrayList<Integer> venueDetailsCount(String fileName) throws IOException, InvalidFormatException {
        ArrayList<Integer> venueDetails = new ArrayList<>();
        FileHandler filehandler = new FileHandler();
        List<String> content = filehandler.readFile(fileName);
        int totalRow = 0;
        int leftColumn = 0;
        int middleColumn = 0;
        int rightColumn = 0;
        int VIP = 0;
        int Seating = 0;
        int Standing = 0;
        for (String line : content) {
            try {
                if (line.isEmpty()) {
                    continue;//if the line of that venue is empty, skip it
                }
                String[] lineArray = line.split(" ");
                switch (lineArray[0].charAt(0)) {//get the type of that column
                    case 'V':
                        String leftArray = lineArray[1];
                        leftColumn = countDigits(leftArray);//count how many seat in left area
                        VIP++;
                        break;
                    case 'S':
                        String middleArray = lineArray[2];
                        middleColumn = countDigits(middleArray);//count how many seat in middle area
                        Seating++;
                        break;
                    case 'T':
                        String rightArray = lineArray[3];
                        rightColumn = countDigits(rightArray);//count how many seat in right area
                        Standing++;
                        break;
                    default:
                        throw new InvalidFormatException(5);//if not V,S,T skip that line
                }
                leftColumn = countDigits(lineArray[1]);
                totalRow++;
            }catch (InvalidFormatException ife){
                System.out.println(ife.getMessage());
            }
        }
        //add back all the detail information back
        venueDetails.add(totalRow);
        venueDetails.add(leftColumn);
        venueDetails.add(middleColumn);
        venueDetails.add(rightColumn);
        venueDetails.add(VIP);
        venueDetails.add(Seating);
        venueDetails.add(Standing);
        return venueDetails;
    }

    /**
     * get the name of user
     * @param fileHandler read the file
     * @param fileName the customer information
     * @param id the id of customer
     * @return the name of concert
     * @throws IOException input and output exception
     */
    public String getName(FileHandler fileHandler, String fileName, int id) throws IOException {
        List<String> fileInformation = fileHandler.readFile(fileName);
        for(String line:fileInformation){
            String[] information = line.split(",");
            int customerId = Integer.parseInt(information[0]);
            if(id == customerId){//if match return name
                return information[1];
            }
        }
        return null;//if not return null
    }

    /**
     * count how many seat number in each area
     * @param str the area
     * @return return the area number
     */
    public static int countDigits(String str){
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
    }

    /**
     * loading the concertDetail
     * @param concertHasDefaultVenue store the concertInformation
     * @param concertInformationDetail get the concerts detail
     * @return updated concert details
     */
    //This method will create the concert, and put  all the information into the concerts array
    public ConcertDetails preLoadConcertDetails(HashMap<String, ArrayList<Integer>> concertHasDefaultVenue, ArrayList<String> concertInformationDetail){
        ConcertDetails  concertdetails  = new ConcertDetails();
        for (String concertLine:concertInformationDetail){
            String[] concertInformation = concertLine.split(",");
            String[] standingPrice = concertInformation[5].split(":");
            String[] seatingPrice = concertInformation[6].split(":");
            String[] vipPrice = concertInformation[7].split(":");
            String venueName = concertInformation[4];
            boolean hasDefaultVenue  = concertHasDefaultVenue.containsKey(venueName);
            if(!hasDefaultVenue){//check it has the specific venue or not
                Concert concert = new Concert(new Venue(concertHasDefaultVenue.get("default").get(0), concertHasDefaultVenue.get("default").get(1), concertHasDefaultVenue.get("default").get(2), concertHasDefaultVenue.get("default").get(3),concertHasDefaultVenue.get("default").get(4),concertHasDefaultVenue.get("default").get(5),concertHasDefaultVenue.get("default").get(6)), new Date(concertInformation[1], concertInformation[2]), concertInformation[3], venueName, new Price(Double.parseDouble(standingPrice[1]), Double.parseDouble(seatingPrice[1]), Double.parseDouble(vipPrice[1]), Double.parseDouble(standingPrice[2]), Double.parseDouble(seatingPrice[2]), Double.parseDouble(vipPrice[2]), Double.parseDouble(standingPrice[3]), Double.parseDouble(seatingPrice[3]), Double.parseDouble(vipPrice[3])));
                concertdetails.getConcerts().add(concert);

            }else{
                Concert concert = new Concert(new Venue(concertHasDefaultVenue.get(venueName).get(0), concertHasDefaultVenue.get(venueName).get(1), concertHasDefaultVenue.get(venueName).get(2), concertHasDefaultVenue.get(venueName).get(3),concertHasDefaultVenue.get(venueName).get(4),concertHasDefaultVenue.get(venueName).get(5),concertHasDefaultVenue.get(venueName).get(6)), new Date(concertInformation[1], concertInformation[2]), concertInformation[3], venueName, new Price(Double.parseDouble(standingPrice[1]), Double.parseDouble(seatingPrice[1]), Double.parseDouble(vipPrice[1]), Double.parseDouble(standingPrice[2]), Double.parseDouble(seatingPrice[2]), Double.parseDouble(vipPrice[2]), Double.parseDouble(standingPrice[3]), Double.parseDouble(seatingPrice[3]), Double.parseDouble(vipPrice[3])));
                concertdetails.getConcerts().add(concert);
            }
        }
        return concertdetails;
    }

    /**
     * set the venue layout,
     * @param concertDetails the detail of the concert
     * @param bookingInformation the booking information
     */
    public void bookingVenueInitialize(ConcertDetails concertDetails, ArrayList<String> bookingInformation ){
        for (String bookingdetail : bookingInformation) {
            String[] bookinginformation = bookingdetail.split(",");
            int concertId = Integer.parseInt(bookinginformation[3]);
            int ticketAmount = Integer.parseInt(bookinginformation[4]);//get the total ticket
            ArrayList<Integer> rowNumber = new ArrayList<>();
            rowNumber.add(concertId);
            for (int i = 0; i < ticketAmount; i++) {
                int index = 5 + 5 * i;
                int row = (Integer.parseInt(bookinginformation[index + 1])); // row
                int column = (Integer.parseInt(bookinginformation[index + 2]));
                String type = bookinginformation[index + 3];
                concertDetails.getConcerts().get(concertId - 1).getVenue().setVenue_layout(row, column, type);
            }
        }
    }

    /**
     * check the customer exist or not
     * @param fileHandler file read
     * @param customerfileName customer file address
     * @param id customer id
     * @return exist or not
     * @throws IOException input and output exception
     */

    public boolean checkCustomerExist(FileHandler fileHandler,String customerfileName, int id) throws IOException {
        List<String> customerinformation = fileHandler.readFile(customerfileName);
        for(String line : customerinformation){
            int existId = Integer.parseInt(line.split(",")[0]);
            if (id == existId){
                return true;
            }
        }
        return  false;
    }

    /**
     * check is it the new customer
     * @param Id the customer id
     * @return the check result
     */
    public boolean checkCustomerNew(String Id){
        boolean customerType = true;
        for (int i = 0; i <Id.length(); i++){
            if (!Character.isDigit(Id.charAt(i))){
                customerType = false;//first means new or existed
            }
        }
        return  customerType;
    }

    /**
     * check the concert field valid or not and stored in the array list
     * @param fileHandler read the file
     * @param concertFileName the address of file
     * @return the array list stored concert
     * @throws IOException throw the input and output alarm
     * @throws InvalidFormatException throw the foramt incorrect alarm
     */
    public ArrayList<String> checkConcertValid(FileHandler fileHandler, String concertFileName) throws IOException, InvalidFormatException {
        ArrayList<String> concertInformation = new ArrayList<>();
        List<String> concertInitialInfos = fileHandler.readFile(concertFileName);
        for (String concertInitialInfo : concertInitialInfos) {
            String[] concertPerInfo = concertInitialInfo.split(",");
            try {
                if (concertPerInfo.length != 8) {
                    throw new InvalidLineException(InvalidLineException.CONCERT);
                }else if(!Character.isDigit(concertPerInfo[0].charAt(0))) {
                    throw new InvalidFormatException(InvalidFormatException.CONCERT_FORMAT);
                }else {
                    concertInformation.add(concertInitialInfo);
                }
            } catch (InvalidLineException ile) {
                System.out.println(ile.getMessage());
            }catch(InvalidFormatException ife){
                System.out.println(ife.getMessage());
            }
        }
        return concertInformation;
    }

    /**
     * check the booking file valid or not, and store the booking information
     * @param fileHandler read the file
     * @param bookingFileName the booking file name address
     * @return the array list store booking information
     * @throws IOException input and output exception
     * @throws InvalidFormatException invalid format alarm
     */
    public ArrayList<String> checkBookingValid(FileHandler fileHandler, String bookingFileName) throws IOException, InvalidFormatException {
        ArrayList<String> bookingInformation = new ArrayList<>();
        List<String> bookInitialInfos = fileHandler.readFile(bookingFileName);
        for (String concertInitialInfo : bookInitialInfos) {
            String[] bookPerInfo = concertInitialInfo.split(",");
            try {
                if (bookPerInfo.length % 5 != 0) {//if the format is incoorect, less one inforamtion
                    throw new InvalidLineException(InvalidLineException.BOOKING);
                }else if(!Character.isDigit(bookPerInfo[0].charAt(0))) {//check booking format
                    throw new InvalidFormatException(InvalidFormatException.BOOKING_FORMAT);
                }else if(!Character.isDigit(bookPerInfo[1].charAt(0))){//check customer format
                    throw new InvalidFormatException(InvalidFormatException.CUSTOMER_FORMAT);
                }else if(!Character.isDigit(bookPerInfo[3].charAt(0))){//check concert format
                    throw new InvalidFormatException(InvalidFormatException.CONCERT_FORMAT);
                }else if(!Character.isDigit(bookPerInfo[4].charAt(0)) || Integer.parseInt(bookPerInfo[4])==0){
                    throw new InvalidFormatException(InvalidFormatException.TICKET_NUMBER_FORMAT);
                }else{
                    bookingInformation.add(concertInitialInfo);
                }
            } catch (InvalidLineException ile) {
                System.out.println(ile.getMessage());
            }catch(InvalidFormatException ife){
                System.out.println(ife.getMessage());
            }
        }
        return bookingInformation;
    }

    /**
     * check the customer file valid or not, and store the customer information
     * @param fileHandler read the file
     * @param customerFileName the customer file name address
     * @return the array list store concert information
     * @throws IOException input and output exception
     * @throws InvalidFormatException Invalid format alarm
     */
    public ArrayList<String> checkCustomerValid(FileHandler fileHandler, String customerFileName) throws IOException, InvalidFormatException {
        ArrayList<String> customerInformation = new ArrayList<>();
        List<String> customerInitialInfos = fileHandler.readFile(customerFileName);
        for (String customerInitialInfo : customerInitialInfos) {
            String[] bookPerInfo = customerInitialInfo.split(",");
            try {
                if (bookPerInfo.length != 3) {
                    throw new InvalidLineException(InvalidLineException.CUSTOMER);
                }else if(!Character.isDigit(bookPerInfo[0].charAt(0))) {
                    throw new InvalidFormatException(InvalidFormatException.CUSTOMER_FORMAT);
                }else {
                    customerInformation.add(customerInitialInfo);
                }
            } catch (InvalidLineException ile) {
                System.out.println(ile.getMessage());
            }catch(InvalidFormatException ife){
                System.out.println(ife.getMessage());
            }
        }
        return customerInformation;
    }
    /**
     * Retrieves the detailed information of all concerts.
     * This method returns an instance of ConcertDetails, which includes all the details
     * related to the concerts such as venue, dates, and ticket prices.
     *
     * @return An instance of ConcertDetails containing comprehensive information about all concerts.
     */
    public ConcertDetails getConcertDetails() {
        return concertDetails;
    }

    /**
     * Retrieves a list of all concert IDs.
     * This method returns an ArrayList of integers where each integer represents a unique ID
     * associated with a concert.
     *
     * @return An ArrayList of integers representing the IDs of all concerts.
     */
    public ArrayList<Integer> getConcertAllId() {
        return concertAllId;
    }

    /**
     * Retrieves booking information for all concerts.
     * This method returns an ArrayList of strings, where each string contains booking details
     * for a specific concert. Typically, this includes customer information and seat assignments.
     *
     * @return An ArrayList of strings containing booking information for each concert.
     */
    public ArrayList<String> getBookingInformation() {
        return bookingInformation;
    }

    /**
     * Retrieves the file path of the booking information file.
     * This method returns the file path as a string where the booking information is stored.
     * This can be used to access or modify the booking data file directly.
     *
     * @return A string representing the file path to the booking information.
     */
    public String getBookFilePath() {
        return bookFilePath;
    }
}



