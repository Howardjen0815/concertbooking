package Handler;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.io.FileOutputStream;
import User.*;
import ExceptionFamily.*;

/**
 * The FileHandler class for write, read the file
 * @version	1.0
 * @author Yu-Han Jen, 1508398, YJEN@student.unimelb.edu.au
 */
public class FileHandler{
    /**
     * use to read the file
     */


    /**
     * read the file and save to the list
     * @param fileName the target file
     * @return the list of that file
     * @throws FileNotFoundException if do not found throw exception
     * @throws IOException if has IO problem throw exception
     */
    public List<String> readFile(String fileName) throws FileNotFoundException, IOException {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try{
            File file = new File(fileName);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            return bufferedReader.lines().toList();
        }finally{
            if(fileReader!= null) {
                fileReader.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    /**
     * write the new line and save the file
     * @param fileName the target file
     * @param lines the content
     * @throws FileNotFoundException if not found throw exception
     */

    public void saveFile(String fileName, String lines) throws FileNotFoundException{
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(new FileOutputStream(fileName, true));
            writer.println(lines);
            writer.flush();
        }finally{
            if(writer != null)
                writer.close(); // Closing here ensures the contents of the file is saved to disk.
        }
    }

    /**
     * check the customer exist or not
     * @param Id the customerId
     * @param password the customer password
     * @param fileName the file need to check
     * @throws FileNotFoundException if not found file
     * @throws IOException if IO problem display the situation
     * @throws IncorrectPasswordException  if password incorrect, shut down the program
     * @throws NotFoundException if id is not appear in the customer list
     */
    public void checkCustomer(int Id, String password, String fileName) throws FileNotFoundException, IOException, IncorrectPasswordException, NotFoundException{
        int count = 0;
        boolean isValid = false;
        List<String> content = readFile(fileName);
        for (String line: content){
            String[] params = line.split(",");
            count++;
            if (Integer.parseInt(params[0]) == Id && params[2].equals(password)){//check the id and password match or not
                isValid = true;
                System.out.printf("Welcome %s to Ticket Management System\n", params[1]);
            }
        }
        if (count < Id){
            //go to the end of the file, means no found
            throw new NotFoundException();
        }
        if (!isValid){
            throw new IncorrectPasswordException();
        }
    }

    /**
     * when the new user enter to the system we need to write the new user information to the customer list
     * @param fileName the customer file
     * @param customer the customer which is log in
     * @throws FileNotFoundException if file not found throwe the file not found
     * @throws IOException if IO problem display the situation
     */
    public void writeNewId(String fileName, Customer customer)throws FileNotFoundException, IOException{
        int count = readFile(fileName).size();
        customer.setId(count+1);
        String newCustomer = customer.getId() + "," + customer.getName()  +"," + customer.getPassword();
        saveFile(fileName,newCustomer);
        System.out.printf("Welcome %s to Ticket Management System\n",customer.getName());
    }
}