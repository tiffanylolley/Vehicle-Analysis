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
 * [Tiffany Lolley, tlolley1, 10/14/26]
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
    * @throws IOException if the file cannot be read.
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
   
      loadVehicleData(filename, entry, price, brand, year, mileage, region);
   
   
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
            
               brandQuery(keyboard, entry, price, brand);
               break; 
            case MILEAGE_CATEGORY_QUERY:
            
               mileageQuery(keyboard, year, mileage);
               break; 
            case VEHICLES_IN_PRICE_RANGE_IN_REGION_QUERY:
            
               regionPriceQuery(keyboard, entry, price,
                  brand, year, mileage, region);
               break;
            case TWO_OLDEST_VEHICLES_QUERY:
            
               twoOldestVehicles(entry, price, brand, year,
                  mileage, region);
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
    * @throws IOException if the file cannot be opened.
    */
   public static int countFileLines(String filename)
                                    throws IOException {
                                    
      Scanner input = new Scanner(new FileInputStream(filename));
      int count = 0;
      
      while (input.hasNextLine()) {
         input.nextLine();
         count++;
      }
      
      input.close();
      return count;
      
   }

  /**
   * Loads vehicle data into parallel arrays after skipping two headers.
   *
   * @param filename the name of the input file
   * @param entry the array of vehicle entry numbers
   * @param price the array of vehicle prices
   * @param brand the array of vehicle brands
   * @param year the array of vehicle model years
   * @param mileage the array of vehicle mileages
   * @param region the array of vehicle regions
   * @throws IOException if the input file can't be opened
   */
   public static void loadVehicleData(String filename, int[] entry,
      int[] price, String[] brand, int[] year, int[] mileage,
      String[] region) throws IOException {
      
      Scanner input = new Scanner(new FileInputStream(filename));
      
      // The first 2 lines have headers instead of vehicle data. 
      input.nextLine();
      input.nextLine();
      
      int index = 0;
      while (input.hasNextLine()) {
         String[] columns = input.nextLine().split(",");
      
         entry[index] = 
              Integer.parseInt(columns[ENTRY_INDEX].trim());
         price[index] =
              Integer.parseInt(columns[PRICE_INDEX].trim());
         brand[index] = columns[BRAND_INDEX].trim();
         year[index] =
              Integer.parseInt(columns[YEAR_INDEX].trim());
         mileage[index] =
              Integer.parseInt(columns[MILEAGE_INDEX].trim());
         region[index] = columns[REGION_INDEX].trim();
      
         index++;
      }
      
      input.close();
   }
   
   /**
    * Reports a brand's count and its lowest and highest priced vehicles.
    * Price ties are resolved using the first matching vehicle in the file.
    * 
    * @param keyboard the scanner used for user input
    * @param entry the array of vehicle entry numbers
    * @param price the array of vehicle prices
    * @param brand the array of vehicle brands
    */
   public static void brandQuery(Scanner keyboard, int[] entry,
       int[] price, String[] brand) {
       
       // Consume the newline left by the menu's nextInt call.
      keyboard.nextLine();
      System.out.print("Please enter a vehicle brand: ");
      String requestedBrand = keyboard.nextLine().trim();
       
      int count = 0;
      int lowest = -1;
      int highest = -1;
       
      for (int i = 0; i < brand.length; i++) {
         if (brand[i].equalsIgnoreCase(requestedBrand)) {
            if (count == 0) {
               lowest = i;
               highest = i;
            } else {
                   // Strict comparisons preserve the first price tie.
               if (price[i] < price[lowest]) {
                  lowest = i;
               }
               if (price[i] > price[highest]) {
                  highest = i;
               }
            } 
            count++;
         }
      }
       
      if (count == 0) {
         System.out.println("Brand " + requestedBrand
               + " is not present.");
      } else {
         String label = "vehicles";
         if (count == 1) {
            label = "vehicle";
         }
           
         System.out.println("Found " + count + " " + requestedBrand
               + " " + label + ".");
         System.out.println("Lowest-priced " + requestedBrand
               + " is entry " + entry[lowest] + ", which costs $"
               + price[lowest] + ".");
         System.out.println("Highest-priced " + requestedBrand
               + " is entry " + entry[highest] + ", which costs $"
               + price[highest] + ".");
      }
   }
   
   /**
    * Reports 3 mileage counts and the low-mileage average year.
    *
    * @param keyboard the scanner used for user input
    * @param year the array of vehicle model years
    * @param mileage the array of vehicle mileages
    */
   public static void mileageQuery(Scanner keyboard, int[] year,
       int[] mileage) {
       
      System.out.print("Please enter positive, inclusive threshold for "
           + "\"low mileage\" category: ");
      int threshold = keyboard.nextInt();
       
       // Long arithmetic prevents overflow when doubling the threshold. 
      long upperThreshold = 2L * threshold;
      int lowCount = 0;
      int middleCount = 0;
      int highCount = 0;
      long lowYearTotal = 0;
       
      for (int i = 0; i < mileage.length; i++) {
         if (mileage[i] <= threshold) {
            lowCount++;
            lowYearTotal += year[i];
         } else if (mileage[i] <= upperThreshold) {
            middleCount++;
         } else {
            highCount++;
         }
      }
    
      System.out.printf(
           "%4d vehicles have mileage less than or equal to %d miles.\n",
            lowCount, threshold);
            
      if (lowCount > 0) {
         System.out.println("     The low-mileage vehicles have "
                + "average year " + (lowYearTotal / lowCount) + ".");
      }
       
      System.out.printf(
           "%4d vehicles have mileage greater than %d but less than "
           + "or equal to %d miles.\n",
           middleCount, threshold, upperThreshold);
           
      System.out.printf(
           "%4d vehicles have mileage greater than %d miles.\n",
           highCount, upperThreshold);
           
      System.out.println();
   }
   
   /**
    * Checks whether a region matches one of the six accepted names.
    * @param region the region name to check
    * @return true if the region is valid, ignoring capitalization. 
    */
   public static boolean isValidRegion(String region) {
      return "west".equalsIgnoreCase(region)
         || "southwest".equalsIgnoreCase(region)
         || "midwest".equalsIgnoreCase(region)
         || "southeast".equalsIgnoreCase(region)
         || "northeast".equalsIgnoreCase(region)
         || "international".equalsIgnoreCase(region);
   }          
   /**
    * Writes vehicles matching a region and inclusive price range to a file.
    * Reports the number of matches and preserves original vehicle order. 
    * @param keyboard the scanner used for user input
    * @param entry the array of vehicle entry numbers
    * @param price the array of vehicle prices
    * @param brand the array of vehicle brands
    * @param year the array of vehicle model years
    * @param mileage the array of vehicle mileages
    * @param region the array of vehicle regions
    * @throws IOException if the output file cannot be opened
    */
    
   public static void regionPriceQuery(Scanner keyboard, int[] entry,
       int[] price, String[] brand, int[] year, int[] mileage,
       String[] region) throws IOException {
       
       // Consume the newline left by the menu's nextInt call.
      keyboard.nextLine();
      System.out.print("Please enter a region: ");
      String requestedRegion = keyboard.nextLine().trim();
       
      if (!isValidRegion(requestedRegion)) {
         System.out.println("Invalid region name.");
      } else {
         System.out.print("Please enter price lower limit: ");
         int lowerPrice = keyboard.nextInt();
           
         System.out.print("Please enter price upper limit: ");
         int upperPrice = keyboard.nextInt();
           
         System.out.print("Please enter an output filename: ");
         String filename = keyboard.next();
           
           // Create the file even if no vehicles match the criteria.
         PrintWriter output = 
               new PrintWriter(new FileOutputStream(filename));
         int matches = 0;
           
         for (int i = 0; i < entry.length; i++) {
            if (region[i].equalsIgnoreCase(requestedRegion)
                   && price[i] >= lowerPrice
                   && price[i] <= upperPrice) {
                   
               output.println(entry[i] + ", " + price[i] + ", "
                       + brand[i] + ", " + year[i] + ", "
                       + mileage[i] + ", " + region[i]);
               matches++;
            }
         }
            
         output.close();
            
         System.out.println("Found " + matches
                + " matching entries for the " + requestedRegion
                + " region");
         System.out.println("with price between $" + lowerPrice
                + " and $" + upperPrice + ", inclusive.");
      }
   }
   
   /**
    * Displays the two oldest vehicles, resolving year ties by file order. 
    * @param entry the array of vehicle entry numbers
    * @param price the array of vehicle prices
    * @param brand the array of vehicle brands
    * @param year the array of vehicle model years
    * @param mileage the array of vehicle mileages
    * @param region the array of vehicle regions
    */
   public static void twoOldestVehicles(int[] entry, int[] price,
       String[] brand, int[] year, int[] mileage, String[] region) {
       
      int oldest = 0;
      int secondOldest = 1;
       
       // Put the first two vehicles in year order, keeping ties. 
      if (year[secondOldest] < year[oldest]) {
         oldest = 1;
         secondOldest = 0;
      }
       
      for (int i = 2; i < year.length; i++) {
         if (year[i] < year[oldest]) {
            secondOldest = oldest;
            oldest = i;
         } else if (year[i] < year[secondOldest]) {
            secondOldest = i;
         }
      }
       
      System.out.println("The two oldest vehicles are:");
       
      outputVehicleToScreen(entry[oldest], price[oldest],
           brand[oldest], year[oldest], mileage[oldest],
           region[oldest]);
           
      outputVehicleToScreen(entry[secondOldest], price[secondOldest],
           brand[secondOldest], year[secondOldest],
           mileage[secondOldest], region[secondOldest]);
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
