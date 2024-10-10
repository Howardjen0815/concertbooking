import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import ExceptionFamily.*;
import Handler.*;
import User.*;



/**
 * The main class for booking the ticket of concerts.
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */

public class TicketManagementEngine {

    /**
     * the keyboard used to read the input
     */
    private static Scanner keyboard= new Scanner(System.in);//used to read the user input

    /**
     * Default constructor that initializes the TicketManagementEngine.
     */

    public TicketManagementEngine() {
    }
    /**
     * main program
     * @param args command line
     */
    public static void main(String[] args) {
        TicketManagementEngine tme = new TicketManagementEngine();
        String mode = args[0];//judge the customer or admin first
        try{
            FileHandler fileHandler = new FileHandler();
            runningMode(tme, mode, args, fileHandler,keyboard);
            //catch all the exception
        }catch(FileNotFoundException fne){
            System.out.println(fne.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(IncorrectPasswordException ipe){
            System.out.println(ipe.getMessage());
        }catch(NotFoundException nfe){
            System.out.println(nfe.getMessage());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        } catch (InvalidLineException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException ime){
            System.out.println(ime.getMessage());
        }catch (ArithmeticException ae){
            System.out.println(ae.getMessage());
        }
    }

    /**
     * check customer mode or admin mode
     * @param tme TicketmanagementEngine
     * @param mode Mode
     * @param args the command line
     * @param fileHandler in charge of file read and save
     * @param keyboard read the input
     * @throws InvalidLineException line incorrect
     * @throws IncorrectPasswordException password incorrect
     * @throws NotFoundException  user not found
     * @throws IOException Input, output exception
     * @throws InvalidFormatException data point incorrect
     */
    public static void runningMode(TicketManagementEngine tme,String mode, String[] args, FileHandler fileHandler,Scanner keyboard) throws InvalidLineException, IncorrectPasswordException, NotFoundException, IOException, InvalidFormatException {
        UserHandler userHandler = new UserHandler();
        switch (mode) {
            case "--customer":
                Customer customer = new Customer();//create the new customer
                userHandler.handleCustomer(args, fileHandler,keyboard,customer);//include the new customer and existed customer
                tme.displayMessage();
                customer.checkOption(keyboard,userHandler.getConcertDetails(),userHandler.getConcertAllId(),userHandler.getBookingInformation(),userHandler.getBookFilePath());
                //enter to the customer
                break;
            case "--admin":
                Admin admin = new Admin();//Create the new Admin
                userHandler.handleAdmin(args, fileHandler);//use userHandler to check all the file information
                tme.displayMessage();
                admin.checkOption(keyboard,userHandler.getConcertDetails(),userHandler.getConcertAllId(),userHandler.getBookingInformation());
                break;
            default:
                System.out.println("Invalid user mode. Terminating program now.");
        }
    }

    /**
     * Show the welcome message
     */
    public void displayMessage(){


        System.out.print("\n" +
                " ________  ___ _____ \n" +
                "|_   _|  \\/  |/  ___|\n" +
                "  | | | .  . |\\ `--. \n" +
                "  | | | |\\/| | `--. \\\n" +
                "  | | | |  | |/\\__/ /\n" +
                "  \\_/ \\_|  |_/\\____/ \n" +
                "                    \n" +
                "                    \n");
    }
}