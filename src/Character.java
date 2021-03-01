import java.util.Scanner;
public class Character {
	public static int level (){//get the level from the user
		int level;
		do{ 
			System.out.println("Please enter the Level number");
			Scanner input=new Scanner(System.in); 
				level=input.nextInt();
			if(level<1||level>20) {
				System.err.println("Invalid Level");
			}
		}while(level<1||level>20);
			 return(level);
	}
	public static int choose (){//choose a character method
		int characterClass;
		do{
			Scanner sc=new Scanner(System.in);
			System.out.print("Characters available \n 1)Barbarian- d12 \n 2)Bard- d8 \n 3)Fighter- d10 \n 4)Sorcerer- d6 \n"
					+ " 5)Cleric- d8 \n 6)Druid- d8 \n 7)Paladin- d10 \n 8)Ranger-d10 \n 9)Wizard- d6 \n");
			System.out.println("Select a character by Entering the character number");
	 		characterClass=sc.nextInt();
	 		if (characterClass<1||characterClass>9) {
	 			System.err.println("Input Invalid! Please Enter a correct character number");
	 		}
	}while((characterClass<1||characterClass>9));
	 	return (characterClass);
	}
	public static int decision() {//method decicion method
		int decision;
		do {
			Scanner sc=new Scanner(System.in);
			System.out.println("How do you want to get values for the attributes? Type the method number \n 1) Enter the attributes directly \n 2) Roll 4d6 and discard the lowest value \n"
					+ " 3) Roll 4d6 and discard the lowest value and if the attribute is 16 or higher, add the value of an additional 1d6.\n"
					+ " 4) Roll method IX");
			decision=sc.nextInt();
			if (decision<1 || decision>4) {
	 			System.err.println("Input Invalid! Please Enter a correct method");
	 		}
		}while (decision<1 || decision>4);
		return decision;
	}	
	}