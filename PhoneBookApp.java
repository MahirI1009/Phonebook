import java.io.File;
import java.util.*;

/* This project is a project I did for my CISC3115 class, this java file "PhoneBookApp.java" consists of 6 classes. This project is meant to implement many essential techniques
 * of OOP which is the reason that this project is structured the way it is. The first class called "PhoneBookApp" contains the main method, in it a Phonebook oject is instant-
 * iated, (the Phonebook class is defined later on in this file). The main method takes in a file titled "phonebook.txt", there is a sample file in the same repository as this 
 * file which shows how the format of the file should be if one wants to test this project with their own custom input. Essentially what the .txt file does is provide data to
 * create a Phonebook object filled with data such as names and various different phone numbers. Then the main method wants input from the keyboard, it will ask if the user 
 * wants to look up a name in the Phonebook or quit the app, choosing to quit ends the program. If the user proceeds to look up a name, then the user will be prompted to enter
 * a last name and a first name, then the main method creates an instance of the PhoneBookEntry object (which is defined later on in this file), this object is a data field of
 * Phonebook class. Then the main will search the Phonebook app to see if such a name exists, if it does then the number(s) of the person the user entered will be printed and the
 * user will once again be asked if they want to lookup another name or if they want to quit. If the name does not exist, then "Name not found is printed". There are four classes 
 * after PhoneBookApp, a class called Name, a class called PhoneNumber, a class called ExtendedPhoneNumber, this class mainly exists for the sake of using a superclass and a
 * subclass for this project's requirements, and a class called PhoneBookEntry which is an aggregate class made up of the Name and ExtendedPhoneNumber classes. This class 
 * represents an individual entry within a traditional phonebook. Lastly, there is the Phonebook class itself. This class implements the TreeMap data structure, however the
 * object is instantiated using polymorphism, though that's not too important. The class itself uses the TreeMap and fills it up with individual PhoneBookEntry objects, the 
 * Name object is the Key, while the PhoneBookEntry object is the value. So within the first class of this file, an input file is read in which then creates individual 
 * PhoneBookEntry classes, and then each entry is added to a TreeMap within the Phonebook class, the program asks the user to enter a name to look up and then searches the 
 * TreeMap for the name that was entered as that is the Key in the Key, Value pair, and if the Key is found then the number is returned. This project is thus a neat implem-
 * entation of various techniques of OOP such as aggregation, inheritance and polymorphism as well as the TreeMap data structure to create a nice Phonebook Application. */

public class PhoneBookApp {
	
	/* Since what this class what does was explained earlier, another detailed explanation will not be needed. A scanner will read in an input file and create
	  * a Phonebook class, then the while loop prompts the user to enter a name, then it checks if the name is in the phonebook, if it is then all the phone 
	  * belonging to that person will be printed, then the loop starts again. */
		
	public static void main(String[] args) throws Exception {
			
	String file = "phonebook.txt"; //input file
	        
	Phonebook phonebook = new Phonebook(file); //creates a Phonebook object
			
	Scanner keyboard = new Scanner(System.in); //scanner for user input
			
	System.out.print("lookup, quit (l/q)? "); //asks if user wants to look up a name, if user enters l then user is prompted to enter a name, otherwise program ends.
			
        String choice = keyboard.next();
		
	while (!choice.equals("q")) { //the while loop continues until user decides to quit when asked if they want to look up another name or quit.
		
        	if (choice.equals("l")) {
			
			//the following executes when the user types in l:
			
			System.out.print("last name? ");
				
	                String lastName = keyboard.next();
				
	                System.out.print("first name? ");
	                String firstName = keyboard.next();
	                
			/* the lastName and firstName Strings are used as parameters for a name object so that the TreeMap containing all the names in the Phonebook
			 * class can be searched to see if the name exists, as the name is the Key, while PhonebookEntry is an individual entry and the Value in the
			 * Key, Value pair. */
	                PhonebookEntry result = phonebook.lookup(new Name(lastName, firstName));
	                   
			//if the result is null then the name does not exist in the Phonebook
			if (result != null) 
			//result has two getter methods called getName and getPhoneNumbers which return the name and all numbers associated with it.
	                        System.out.println(result.getName() + "'s phone numbers: " + result.getPhoneNumbers());
				
	                else System.out.println("-- Name not found");  
			} 
			
		//the program only accepts l or q, so if anything else is typed then the program asks again to enter l or q
		else System.out.println("Invalid choice -- please enter an 'l' or a 'q' for your choice");

            	System.out.print("\nlookup, quit (l/q)? ");
			
	    	choice = keyboard.next();
                }
	//when the user enters q, then the loop ends and thus the program ends as the end of the main method has been reached
	keyboard.close();
	} //end of main method
}//end of PhoneBookApp class


/* the Name object will be defined below, the name object has two fields which are Strings, they are for the last and first names. The constructor takes two Strings and
 * assigns them to their fields. Then there is a copy constructor, a custom equals method, a compareTo method as this class does in fact implement the Comparable interface
 * a custom toString method, and a static method called read which is takes a Scanner object as a parameter and reads in two Strings to create the Name object */

class Name implements Comparable<Name> {
	
	private String first, last;

	public Name(String last, String first) {
        	this.last = last;
		this.first = first;
	}

	public Name(Name other) {
        	this.first = other.first;
	        this.last = other.last;
	}

	public boolean equals(Object obj) {
	        
		if (obj instanceof Name) {
	            Name other = (Name)obj;
	            return this.first.equals(other.first) && this.last.equals(other.last);
	        }
	        else return false;
	}

	public int compareTo(Name other) {
	        int lastResult = this.last.compareTo(other.last);
	        
		if (lastResult != 0)
	            return lastResult;
	
		else return this.first.compareTo(other.first);
	}

	public String toString() { return first + " " + last; }

	public static Name read(Scanner scanner) {
	        if (!scanner.hasNext()) 
	            return null;
	       
		String last = scanner.next();
	        String first = scanner.next();
	
		return new Name(last, first);
	}
}//end of Name class

/* the PhoneNumber class is defined below, this class is a superclass which is extended by the class after it called ExtendedPhoneNumber. This class has a single
 * private String field which stores an individual phonebumber as a String. It has a constructor which only takes in a String as a parameter and assigns it to the
 * number field. Then there's a copy constructor, a custom equals method and toString method, and lastly, a static read method which does the same as the read method
 * in the name class, except that it reads in one string only and creates a phonenumber object. */

class PhoneNumber {
	
    private String number;

    public PhoneNumber(String number) { this.number = number; }

    public PhoneNumber(PhoneNumber old) { this.number = old.number; }

    public boolean equals(Object obj) {
        if (obj instanceof PhoneNumber)
            return this.number.equals(((PhoneNumber)obj).number);
        else return false;
    }

    public String toString() { return number; }

    public static PhoneNumber read(Scanner scanner) {
        if (!scanner.hasNext()) 
            return null;
	
	 String number = scanner.next();
	 return new PhoneNumber(number);
   }
}//end of PhoneNumber class

/* The ExtendedPhoneNumber class is defined below, this class inherits from the PhoneNumber class and it extends the class, this class mainly works with the format
 * of the input file that the Scanner in the main method will read. In the input file the format is: firstName lastName description number description number ....
 * Within the input file there is a String before the number that describes what the following number after it is, such as work, home, mobile, etc, so this class has
 * a field for the description String and then has a constructor which takes in two Strings, one for the description and one for the number, the superclass's constructor
 * is called with the String for the number. Then there is a copy constructor a custom toString method and once again a Static read method. */

class ExtendedPhoneNumber extends PhoneNumber {
	
     private String description;

     public ExtendedPhoneNumber(String description, String number) {
        super(number);
	this.description = description;
     }

    public ExtendedPhoneNumber(ExtendedPhoneNumber old) {
  	super(old);
       	this.description = old.description;
    }

    public String toString() {return description + ": " + super.toString();}
	
    public static ExtendedPhoneNumber read(Scanner scanner) {
    	if (!scanner.hasNext()) 
	            return null;
        String description = scanner.next();
        String number = scanner.next();
        return new ExtendedPhoneNumber(description, number);
    }
}//end of ExtendedPhoneNumber class

/* The following class, PhoneBookEntry is an aggregate class, it uses the previously defined Name object and ExtendedPhoneNumber object as fields. An ArrayList 
 * ExtendedPhoneNumber class is used as a data field, the reason an ArrayList is used is because in the input file that the main method will use, the format allows
 * a single person to have multiple numbers, so the ArrayList will hold all of the numbers associated with a name, and each individual phone number is an ExtendedPhoneNumber
 * object. The constructor takes in a Name object and an ArrayList of ExtendedPhoneNumber objects as parameters and assigns them to their respective fields. There is a
 * copy constructor, a getter method called getName which returns the Name object (which really just calls the toString method of the Name object) and a getter method that
 * returns the ArrayList that contains the phone number(s) which are themselves ExtendedPhoneNumber objects. There is a custom toString method for this class, and lastly a
 * static read method, within in the input file, after a name there is a number that specifies how many numbers a certain name has, thus the read method has a for loop which
 * calls the read method of the ExtendedPhoneNumber class that amount of times, and each phonenumber is then added to an ArrayList. When all of the numbers have been read then 
 * the for loop ends and the read method creates a PhoneBookEntry object with the Name that it read in, and the ArrayList it made containing all the numbers that it read in. */

class PhonebookEntry { 
	
    private Name name; //Name object name field 
    private ArrayList<ExtendedPhoneNumber> phoneNumbers; //ArrayList for phone numbers

    public PhonebookEntry(Name name, ArrayList<ExtendedPhoneNumber> phoneNumbers) { //the object is created with a Name object and phone number
        this.name = new Name(name);
        this.phoneNumbers = new ArrayList<ExtendedPhoneNumber>(phoneNumbers);
    }

    public PhonebookEntry(PhonebookEntry other) {
        this.name = new Name(other.name);
        this.phoneNumbers = new ArrayList<ExtendedPhoneNumber>(other.phoneNumbers);
    }

    public Name getName() { return new Name(name); }

    public ArrayList<ExtendedPhoneNumber> getPhoneNumbers() {
        return new ArrayList<ExtendedPhoneNumber>(phoneNumbers);
    }
	
    public String toString() { return name + ": " + phoneNumbers; }

    public static PhonebookEntry read(Scanner scanner) { //PhonebookEntry object created by calling the object
        if (!scanner.hasNext()) 
            return null;
        
	Name name = Name.read(scanner); // a name object is created by calling the read scanner
        
	ArrayList<ExtendedPhoneNumber> phoneNumbers = new ArrayList<>();
        
	int header = scanner.nextInt();
        for (int i = 1; i <= header; i++)
            phoneNumbers.add(ExtendedPhoneNumber.read(scanner));
	        return new PhonebookEntry(name, phoneNumbers);
    	}
} //end of PhoneBookEntry class

/* The following class is the last class within this file, it defines the Phonebook class, it has a single field which is a TreeMap object, it is instantiated using
 * polymorphism as Map is a superclass of TreeMap, the Key is the Name object and the Value is the PhoneBookEntry object which contains a Name object and all PhoneNumbers
 * belonging to that name. The constructor takes in a single String, this String is meant to be the name of the input file, within the constructor a Scanner object is 
 * created for the input file, then a while loop reads in the entire file, it uses the read method of the PhoneBookEntry class which in turns calls the read methods of 
 * the classes that are its fields which are the Name and ExtendedPhoneNumber classes. So a PhoneBookEntry object is created titled entry and then the Name is obtained
 * using entry's getName method and is used as the Key while entry itself is the Value of the Key, Value pair of the TreeMap. The while loop reads in all the data of the
 * input file. After that the class has a single method called lookup, this method is what is used in the main method to look up a number, it takes a Name object as a 
 * parameter and then searches the TreeMap for the name using Map's get method, if the Name is found then the Value is returned which is the PhoneBookEntry object. Then
 * the main method uses the PhoneBookEntry's getter methods to print out the name and all the phone numbers belonging to it. If a name is not found then the method returns null.*/

class Phonebook {
	
    private Map<Name, PhonebookEntry> map;

    public Phonebook(String phonebookName) throws Exception {
        map = new TreeMap<Name, PhonebookEntry>();
        Scanner scanner = new Scanner(new File(phonebookName)); //create a scanner object with the file

        PhonebookEntry entry = PhonebookEntry.read(scanner); //create a PhonebookEntry object and name it entry, sent it the scanner
        while (entry != null) {
            map.put(entry.getName(), entry);
            entry = PhonebookEntry.read(scanner);
        }
    }

    public PhonebookEntry lookup(Name name) {
        PhonebookEntry result = map.get(name);
        return result == null? result : new PhonebookEntry(result);
    }
} //end of Phonebook class
//end of this project
