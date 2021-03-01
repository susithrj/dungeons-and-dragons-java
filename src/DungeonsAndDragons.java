import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class DungeonsAndDragons {
	public static void main(String[] args) {
		int []attributes = new int[6];
		String []attributesString= {"Str","Dex","Con","Int","Wis","Cha"};
		String []characterNames= {"Barbarian","Bard", "Fighter", "Sorcerer","Cleric","Druid","Paladin","Ranger","Wizard"};
		int []bonusVal=new int[6];
		int reroll=0;
		int diceType;
		int hitPoints;
		int level=Character.level();//get level by calling the method
		int character=Character.choose();//get character by calling the method
		int decision=Character.decision();//get method of rolling the attributes by calling the method
		do {
		if (decision==1) {
			attributes=takeAttributes(attributesString);
		}
		if (decision==2) {
			attributes=diceRollReduction();
		}else if(decision==3) {
			attributes=diceRollReduction();
			for(int i=0;i<attributes.length;i++) {
				if (attributes[i]>=16) {
					attributes[i]=attributes[i]+(int)((Math.random()*1000 %(6)+1));
				}
			}
		}else if(decision==4) {
			attributes=methodIX();
		}
		bonusVal=bonus(attributes);
		 diceType=0;
		if (character==1) {//if the user choose Barbarian as the character
			 diceType=12;
		}else if (character==2||character==5||character==6) {//if the user choose Bard, Cleric or Druid as the character
			 diceType=8;
		}else if (character==3||character==7||character==8) {//if the user choose Fighter, Paladin or Ranger as the character
			diceType=10;	
		}else if (character==4||character==9) {//if the user choose Sorcerer or Wizard as the character
			diceType=6;
		}
		int roll=(int)((Math.random()*1000 %(diceType)+1));//roll with hitDie to find hitpoints
		
		int conBonus=bonusVal[2];
		int conBonusCal=0;
		if(conBonus<0) {// if the conbonus is less than 0 assign a minimum value as 1
			conBonusCal=1;
		}
	      hitPoints=roll+conBonusCal;
		for(int i=0;i<attributesString.length;i++) {
			System.out.println(attributesString[i]+" : "+ "["+attributes[i]+"]"+ "["+ bonusVal[i]+"]");
		}
		System.out.println("HitPoints : "+ hitPoints);
		Scanner user=new Scanner(System.in);
		System.out.println("Do You want to reroll or re enter? If \"YES\" press 1 If \"NO\" press any other number.");
		 reroll=user.nextInt();
		}while(reroll==1);
		
		int skillNumSize=0;// to get an amount of skills which should be equal to the level 
		do {
		readFile("Skill.txt");//pass the skill file to the read file method to read
		Scanner skill=new Scanner(System.in);
		System.out.println("****Select a Skill by reading the above list. You need to press the Skill number. Keep a space in between numbers");//show to enter skills numbers
		if(skill.hasNextLine()) {//get the whole number line as inputs
		String skillString=skill.nextLine();
		String [] skillNumArr=skillString.split(" ");//split each number by the space
		int []skillNum=new int[skillNumArr.length];
		for(int i=0; i<skillNumArr.length;i++) {
			skillNum[i] = Integer.parseInt(skillNumArr[i]);//put the string values converted as numbers to the array
		}
		if(skillNum.length!=level) {
			System.err.println("Selected skills should be equal to level. Re enter skills!");
		}
		skillNumSize=skillNum.length;
		}
	}while(skillNumSize!=level);
		
		int skillPoints=skillPoints(bonusVal[3],character,level);//get skill points by calling method
		int totalBAB=countBAB(character,level);//get BAB by calling method
		int combat=totalBAB+bonusVal[0];//calculate combat
		int damage=bonusVal[0];//calculate damage
		Scanner characterNameSc=new Scanner(System.in);
		System.out.println("Enter a character name");//prompt and get a character name
		String characterName=characterNameSc.nextLine();
		
		System.out.println("Final details are.........");//show all the generated and taken data from the user
		System.out.println("Level : " +level);
		System.out.println("Character Class : " + characterNames[character-1] );
		System.out.println("Character Name : " + characterName );
		System.out.println("HitPoints : "+ hitPoints);
		System.out.println("HitDie : d"+ diceType);
		System.out.println("Skill-Points : "+ skillPoints);
		System.out.println("Base Attack Bonus  : "+ totalBAB);
		System.out.println("Combat : "+ combat);
		System.out.println("Damage : "+ damage);
		
		System.out.println("Do you want to save a copy of the results in a file? Press \"y\" to confirm or press any other key to bypass");
		Scanner result=new Scanner (System.in);
		String confirmation=result.next();
		confirmation=confirmation.toLowerCase();//if user type "Y" as the input convert it to "y"
		if(confirmation.equals("y")) {
		writeFile(level,characterNames[character-1],hitPoints,diceType,skillPoints,totalBAB,combat,damage,characterName);
		}
		else {
			System.exit(0);
		}
	}
	private static int[] takeAttributes(String [] attributesString) {//get attributes directly from the user
		int[] attributes= new int[6];
		  for(int i=0;i<attributesString.length;i++) {
			Scanner sc = new Scanner(System.in);
			 System.out.println("Enter Value for "+attributesString[i]);
			  attributes[i]	=sc.nextInt();
		}
		  return(attributes);
	}
	private static int[]bonus(int []attributes){//calculate the bonus
	int[] bonusVal= new int[6];
		for(int i=0;i<bonusVal.length;i++) {
			bonusVal[i]=(int)(attributes[i]/2)-5;//bonus equation
		}
		return bonusVal;
	}
	private static int[] diceRollReduction() {//roll 4 times and reduce the minium value
		int min=6;//assuming the the minimum value of a single dice roll is the dice's maximum value	
		int sumDice=0;//total sum of all 4 dice rolls
		int sumTotal=0;//total sum of the highest 3 dice rolls
		int[] arrayDice=new int[6];	//create an array to hold the statistics
		for(int i=0;i<arrayDice.length;i++) {//continue to find 6 sumTotal for the 6 statistics
			for(int j=0;j<4;j++){//roll d6 4 times and deduct the minimum
				int roll=(int)(Math.random()*1000 %(6)+1);
					if (roll<min) {
						min=roll;
					}
				sumDice=sumDice+ roll;
			}
			sumTotal=sumDice-min;
			arrayDice[i]=sumTotal;
			sumTotal=0;//make the sumtotal back to zero
			sumDice=0;
			min=6;
		}
		return (arrayDice);
	}
	private static int[]methodIX(){
		int[] arrayDice=new int[6];
		int[]sortArr=new int[9];
		for(int i=0;i<arrayDice.length;i++) {//get random numbers for main attributes such as str,dex,con
			for(int j=0;j<(9-i);j++) {//roll dices for most to least important attributes
				int roll=(int)((Math.random()*1000 %(6)+1));
				sortArr[j]=roll;
			}
			for (int k = 0; k < sortArr.length; k++) {//sort the array from the highest value to the lowest value
				    for (int l = 0; l < sortArr.length; l++) {
				        if (sortArr[k] > sortArr[l]) {
				            int temp = sortArr[k];//shifting values in the indexes
				            sortArr[k] = sortArr[l];
				            sortArr[l] = temp;
				        }
				    }
			}
			arrayDice[i]=sortArr[0]+sortArr[1]+sortArr[2];//add the highest 3 values for one perticular attribute
		}
		return arrayDice;
	}
	private static void readFile(String file){//read the skill file method
		try {
			File fileRead=new File(file);
			Scanner sc=new Scanner(fileRead);
			while(sc.hasNextLine()) {
			System.out.println(sc.nextLine());
			}
		}
		catch(FileNotFoundException e) {
			System.err.println("File Not Found");
		}
	}
	private static int skillPoints(int intBonus,int character,int level) {//calculate skillpoints method
		int skillPoints=0;
		if(intBonus<0) {
			intBonus=1;
		}
		if (character==1||character==6) {
			skillPoints=(4+intBonus);
		}
		else if(character==2||character==8) {
			skillPoints=(6+intBonus);
		}
		else if(character==3||character==4||character==5||character==7||character==9) {
			skillPoints=(2+intBonus);
		}
		if(level==1) {
			skillPoints=skillPoints*4;
		}
		return skillPoints;
	}	
	private static int countBAB(int character,int level) {// calculate BAB method
		int totalBAB;
		if(character==1||character==3||character==6||character==8) {
			totalBAB=level;
		}
		else if(character==4||character==7) {
			totalBAB=(level*3)/4;
		}
		else{
			totalBAB=level/2 ;
		}
		return(totalBAB);
	}
	private static void writeFile(int level,String characterClass,int hitPoints,int diceType,int skillPoints,int totalBAB,int combat, int damage,String characterName) {
		try {//write the file
			File file=new File("Your Stats.txt");
			if(file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			pw.println("Final details are.........");
			pw.println("Level : " + level);
			pw.println("Character Class : " + characterClass);
			pw.println("Character Name : " + characterName);
			pw.println("HitPoints : "+ hitPoints);
			pw.println("Skill-Points : "+ skillPoints);
			pw.println("Base Attack Bonus  : "+ totalBAB);
			pw.println("Combat : "+ combat);
			pw.println("Damage : "+ damage);
			pw.println("Dice Type : d"+diceType);
			pw.close();
			System.out.println("FILE GOT CREATED");
			}
			catch(IOException e){
				System.err.println("Error writting file");
			}
	}
}