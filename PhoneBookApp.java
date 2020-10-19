import java.io.File;
import java.util.*;

/* This project is a project I did for my CISC3115 class, this java file "PhoneBookApp.java" consists of 6 classes. This project is meant to implement many essential techniques
 * of OOP which is the reason that this project is structured the way it is. The first class called "PhoneBookApp" contains the main method, in it a Phonebook oject is instant-
 * iated, (the Phonebook class is defined later on in this file). The main method takes in a file titled "phonebook.txt", there is a sample file in the same repository as this 
 * file which shows how the format of the file should be if one wants to test this project with their own custom input. Essentially what the .txt file does is provide data to
 * create a Phonebook object filled with data such as names and various different phone numbers. Then the main method wants input from the keyboard, it will ask if the user 
 * wants to look up a name in the Phonebook or quit the app, choosing to quit ends the program. If the user proceeds to look up a name, then the user will be prompted to enter
 * a last name and a first name, then the main method creates an instance of the PhoneBookEntry object (which is defined later on in this file), this object is a data field of
 * Phonebook class. Then the main will search the Phonebook app to see if such a name exists, if it does then the number of the person the user entered will be printed and the
 * will once again be asked if they want to lookup another name or if they want to quit. If the name does not exist, then "Name not found is printed". There are four classes 
 * after PhoneBookApp, a class called Name, a class called PhoneNumber, a class called ExtendedPhoneNumber, this class mainly exists for the sake of using a superclass and a
 * subclass for this project's requirements, and a class called PhoneBookEntry which is an aggregate class made up of the Name and ExtendedPhoneNumber classes. This class 
 * represents an individual entry within a traditional phonebook. Lastly, there is the Phonebook class itself. This class implements the TreeMap data structure, however the
 * object is instantiated using polymorphism, though that's not too important. The class itself uses the TreeMap and fills it up with individual PhoneBookEntry objects, the 
 * Name object is the Key, while the PhoneBookEntry object is the value. So within the first class of this file, an input file is read in which then creates individual 
 * PhoneBookEntry classes, and then each entry is added to a TreeMap within the Phonebook class, the program asks the user to enter a name to look up and then searches the 
 * TreeMap for the name that was entered as that is the Key in the Key, Value pair, and if the Key is found then the number is returned. This project is thus a neat implem-
 * entation of various techniques of OOP and the TreeMap data structure to create a nice Phonebook Application. */

public class PhoneBookApp {
		
	public static void main(String[] args) throws Exception {
			
	String file = "phonebook.txt";
	        
	Phonebook phonebook = new Phonebook(file); //create a phonebook
			
	Scanner keyboard = new Scanner(System.in);
			
	System.out.print("lookup, quit (l/q)? ");
			
        String choice = keyboard.next();
		
	while (!choice.equals("q")) {
		
        	if (choice.equals("l")) {
			
			System.out.print("last name? ");
				
	                String lasame = keyboard.next();
				
	                System.out.print("first name? ");
	                String fname = keyboard.next();
	                
	                PhonebookEntry result = phonebook.lookup(new Name(lname, fname));
	                    
			if (result != null) 
	                        System.out.println(result.getName() + "'s phone numbers: " + result.getPhoneNumbers());
				
	                else System.out.println("-- Name not found");  
			} 
			
		else System.out.println("Invalid choice -- please enter an 'l' or a 'q' for your choice");

            	System.out.print("\nlookup, quit (l/q)? ");
			
	    	choice = keyboard.next();
                }
	}
}

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
	        else eturn false;
	}

	public int compareTo(Name other) {
	        int lastResult = this.last.compareTo(other.last);
	        
		if (lastResult != 0)
	            return lastResult;
	
		else eturn this.first.compareTo(other.first);
	}

	public String toString() { return first + " " + last; }

	public static Name read(Scanner scanner) {
	        if (!scanner.hasNext()) 
	            return null;
	       
		String last = scanner.next();
	        String first = scanner.next();
	
		return new Name(last, first);
	}
}

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
}

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
}

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
}

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
}
