import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * EN.500.112, Fall 2026, Project 3: Vehicle Data Analysis 
 * Gateway Computing: Java, Johns Hopkins University
 * Purpose: read data file about vehicles for sale and
 * perform some specific analysis on the data.
 * 
 * [Student TODO: replace this entire line with name, JHED ID, Date]
 */
public class Project3 {

   // column index constants for vehicle data file
   static final int ENTRY_INDEX = 0;
   static final int PRICE_INDEX = 1;
   static final int BRAND_INDEX = 2;
   static final int YEAR_INDEX = 4;
   static final int MILEAGE_INDEX = 6;
   static final int REGION_INDEX = 12;

   // menu options
   static final int BRAND_COUNT_LOW_HIGH_PRICE_QUERY = 1;
   static final int MILEAGE_CATEGORY_QUERY = 2;
   static final int VEHICLES_IN_PRICE_RANGE_IN_REGION_QUERY = 3;
   static final int TWO_OLDEST_VEHICLES_QUERY = 4;
   static final int QUIT = 5;

   /**
    * Drive the vehicle data analysis program.
    * @param args This program does not take command line arguments.
    * @throws IOException
    */
   public static void main(String[] args) throws IOException {
   
      // collect input filename (e.g. "used.csv")
      System.out.print("Welcome! Please enter name of csv-format input file: ");
      Scanner keyboard = new Scanner(System.in);
      String filename = keyboard.next();
   
      // count the number of data rows in the file (ignore two header lines)
      int rowCount = countFileLines(filename) - 2;
      System.out.println("File has " + rowCount + " entries.");
      System.out.println();
   
      // declare and allocate parallel arrays for each column of interest
      int[] entry = new int[rowCount];
      int[] price = new int[rowCount];
      String[] brand = new String[rowCount];
      int[] year = new int[rowCount];
      int[] mileage = new int[rowCount];
      String[] region = new String[rowCount];
   
      // TODO: load column arrays from file


   
      // repeat as long as the user doesn't choose to quit...
      int option = 0;
      while (option != QUIT) {
      
         // display the menu and get an option
         printMenu();
         option = keyboard.nextInt();
         System.out.println();
      
         // handle the option selected by the user
         switch (option) {
            case BRAND_COUNT_LOW_HIGH_PRICE_QUERY: 
               // TODO: fill in code to execute this choice

               break; 
            case MILEAGE_CATEGORY_QUERY:
               // TODO: fill in code to execute this choice

               break; 
            case VEHICLES_IN_PRICE_RANGE_IN_REGION_QUERY:
               // TODO: fill in code to execute this choice

               break;
            case TWO_OLDEST_VEHICLES_QUERY:
               // TODO: fill in code to execute this choice

               break;
            case QUIT:
               System.out.println("Goodbye.");
               break;
            default:
               System.out.println("Invalid option.");
         
         }
      
         // generate empty line before display of next menu
         System.out.println();
      
      }
   
   }




   /**
    * Counts the number of lines in a given plain text file.
    * @param filename The file whose lines are to be counted.
    * @return the number of lines in the file.
    * @throws IOException
    */
   public static int countFileLines(String filename)
                                    throws IOException {
                                    
      // TODO: replace this stub as you write the body of this method
      return 0;      
   
   }

 

   
   /**
    * Output data about one vehicle to the console, using
    * given formatting string. 
    *
    * ---DO NOT MODIFY THIS METHOD---
    *
    * @param entry the entry number to output
    * @param brand the brand name to output
    * @param year the model year to output
    * @param mileage the vehicle mileage to output
    * @param region the region name to output
    * @param price the cost to output
    */
   public static void outputVehicleToScreen(int entry, int price,
                                            String brand, int year,
                                            int mileage, 
                                            String region) {
      System.out.printf("%5d %-10s %d %6d miles  $%6d in %s\n", 
                entry, brand, year, mileage, price, region);
   }
   
   
   
   
   /**
    * Print the program menu to the console.
    *
    * ---DO NOT MODIFY THIS METHOD---
    *
    */
   public static void printMenu() {
      System.out.println("*****");
      System.out.printf(
         " [%d] For given brand, report vehicle count and "
         +  "low and high prices to screen.\n",
         BRAND_COUNT_LOW_HIGH_PRICE_QUERY);
      System.out.printf(
         " [%d] Report count of vehicles in three mileage categories.\n",
         MILEAGE_CATEGORY_QUERY);
      System.out.printf(
         " [%d] Write given-region vehicles to file, "
         + "their average mileage to screen.\n", 
         VEHICLES_IN_PRICE_RANGE_IN_REGION_QUERY);
      System.out.printf(
         " [%d] Two oldest vehicles overall.\n", 
         TWO_OLDEST_VEHICLES_QUERY);
      System.out.printf(
         " [%d] Quit.\n", 
         QUIT);
      System.out.print("***** Please select an option: ");
   
   }

 

}
