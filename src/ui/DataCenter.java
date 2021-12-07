package ui;

import java.util.Scanner;
import model.*;

/**
* This class shows the user the functions that they can use and allows them to choose which one they want to use.
* @author <b>Johan Jojoa</b>
*/
public class DataCenter
{	
	//Attributes
	
	private Scanner lector;
	
	//Relations
	
	private DataCenterSystem theSystem;
	
	//Methods
	
	public DataCenter()
	{
		lector = new Scanner(System.in);
	}
	
	public static void main(String[]args)
	{		
		DataCenter dataCenter = new DataCenter();
		
		dataCenter.init();
		
		dataCenter.menu();
	}
	
	public void init()
	{
		theSystem = new DataCenterSystem();
	}
	
	public void menu()
	{
		System.out.println("Wellcome, what do you want to do?");
		
		System.out.print("1. Generate a list with the available mini rooms.\n2. Rent a mini room.");
		System.out.print("\n3. Cancel a rent.\n4. Show a datacenter map.");
		System.out.print("\n5. Pretend to turn on all the rooms.\n6. Simulate the switching off of the mini rooms.");
		System.out.println("\n0. Exit.");
		
		int answer = lector.nextInt();
		lector.nextLine();
		
		if(answer < 0 || answer > 6)
		{
			System.out.println("That is not an option.");
			menu();
		}
		else
		{
			toDo(answer);
		}	
	}
	
	public void toDo(int answer)
	{
		switch(answer)
		{
			case 0:
				System.out.println("Bye.");
				break;
			
			case 1:
				generateList(false, false);
				menu();
				break;
			
			case 2:
				rentRoom();
				break;
			
			case 3:
				cancelRoom();
				break;
			
			case 4:
				showMap();
				menu();
				break;
				
			case 5:
				powerOn();
				break;
				
			case 6:
				powerOff();
				break;		
		}
	}
	
	public void generateList(boolean condition, boolean windowB)
	{
		if(condition == true)
		{			
			String answer = theSystem.generateList(true, windowB);
			System.out.println(answer);
			
			if(answer.startsWith("Error"))
			{
				menu();
			}
		}
		else
		{
			String answer = theSystem.generateList(false, false);
			System.out.println(answer);
			menu();
		}
	}
	
	public void rentRoom()
	{
		String registerStatus = registerUser();
		String nit = "";
		
		if(registerStatus.startsWith("Error"))
		{
			menu();
		}
		else
		{
			if(registerStatus.startsWith("."))
			{
				nit = registerStatus.substring(1);
			}
			else
			{
				String result = registerStatus.substring(0, 32);
				System.out.println(result);
			}
			
			System.out.println("Do you want a miniroom with a window?");
			System.out.println("1. Yes.\n2. No.");
			
			int window = lector.nextInt();
			lector.nextLine();
			
			while(window < 1 || window > 2)
			{
				System.out.println("Error: Option not available.");
				System.out.println("");
				
				System.out.println("Do you want a miniroom with a window?");
				System.out.println("1. Yes.\n2. No.");
				
				window = lector.nextInt();
				lector.nextLine();
			}
			
			boolean windowB = false;
			
			switch(window)
			{
				case 1:
					windowB = true;
					break;
				
				case 2:
					windowB = false;
					break;		
			}
			
			generateList(true, windowB);
			
			System.out.println("\n Above is a list of rooms that meet your condition \n");
			
			System.out.println("Write the actual date.");
			String rentalDate = lector.nextLine();
			lector.nextLine();
			
			System.out.println("Write the room number you want to rent");
			int roomNumber = lector.nextInt();
			lector.nextLine();
			
			System.out.println("How many servers do you want to host?");
			int hostedServers = lector.nextInt();
			lector.nextLine();
			
			while(hostedServers < 0)
			{
				System.out.println("Error: The hosted servers can’t be lower than 0. ");
				System.out.println("");
				System.out.println("How many servers do you want to host?");
				hostedServers = lector.nextInt();
				lector.nextLine();
			}
			
			for(int i = 0; i < hostedServers; i++)
			{
				initServer(i, roomNumber, hostedServers);
			}
			
			if(registerStatus.startsWith("."))
			{
				System.out.println(theSystem.rentRoom(nit, roomNumber, rentalDate));
			}
			else
			{
				String userId = registerStatus.substring(32);
				
				System.out.println(theSystem.rentRoom("ICESI", userId, roomNumber, rentalDate));
			}
		}
		menu();
	}
	
	public String registerUser()
	{
		String message = "";
		
		System.out.println("The client identifies itself as:");
		System.out.println("1. Company.\n2. Research project.");
		int clientType = lector.nextInt();
		lector.nextLine();
		
		while(clientType < 1 || clientType > 2)
		{
			System.out.println("Error: Option not available.");
			System.out.println("");
			
			System.out.println("The client identifies itself as:");
			System.out.println("1. Company.\n2. Research project.");
			clientType = lector.nextInt();
			lector.nextLine();
		}
		
		switch(clientType)
		{			
			case 1:
				System.out.println("Do you already have mini rooms in use?");
				System.out.println("1. Yes.\n2. No.");
				int usingRooms = lector.nextInt();
				lector.nextLine();
				
				while(usingRooms < 1 || usingRooms > 2)
				{
					System.out.println("Error: Option not available.");
					System.out.println("");
					System.out.println("Do you already have mini rooms in use?");
					System.out.println("1. Yes.\n2. No.");
					usingRooms = lector.nextInt();
					lector.nextLine();
				}
				
				switch(usingRooms)
				{
					case 1:
						System.out.println("Enter your nit.");
						String usedNit = lector.nextLine();
						lector.nextLine();
						
						message = "."+usedNit;
						break;
					
					case 2:
						System.out.println("Welcome.");
						System.out.println("");
						
						System.out.println("Register your company nit.");
						String nit = lector.nextLine();
						lector.nextLine();
						
						System.out.println("Register your company name.");
						String companyName = lector.nextLine();
						lector.nextLine();
						
						message = theSystem.registerUser(nit, companyName)+nit;
						break;		
				}
				break;
			
			case 2:
				System.out.println("Welcome.");
				System.out.println("");
				
				System.out.println("Register registry number.");
				String registryNumber = lector.nextLine();
				lector.nextLine();
				
				message = theSystem.registerUser(registryNumber, "ICESI")+registryNumber;
				break;		
		}
		
		return message;
	}
	
	public void initServer(int serverNumber, int roomNumber, int hostedServers)
	{
		String answer ="";
		
		System.out.println("Enter the amount of cache memory (in GB) of the server "+(serverNumber+1)+".");
		double cacheMemory = lector.nextDouble();
		lector.nextLine();
		
		while(cacheMemory < 0)
		{
			System.out.println("Error: The cache memory can’t be lower than 0.");
			System.out.println("");
			System.out.println("Enter the amount of cache memory (in GB).");
			cacheMemory = lector.nextDouble();
			lector.nextLine();
		}
		
		System.out.println("How many processors will the server have?");
		int numberOfProcessors = lector.nextInt();
		lector.nextLine();
		
		while(numberOfProcessors < 1)
		{
			System.out.println("Error: The processors can’t be lower than 1.");
			System.out.println("");
			System.out.println("How many processors will the server have?");
			numberOfProcessors = lector.nextInt();
			lector.nextLine();
		}
		
		System.out.println("How much RAM does the server have? (in GB)");
		double ramMemory = lector.nextDouble();
		lector.nextLine();
		
		while(ramMemory < 0)
		{
			System.out.println("Error: The RAM memory can’t be lower than 0");
			System.out.println("");
			System.out.println("How much RAM does the server have? (in GB)");
			ramMemory = lector.nextDouble();
			lector.nextLine();
		}
		
		System.out.println("How many disks does the server have?");
		int numberOfDiscs= lector.nextInt();
		lector.nextLine();
		
		while(numberOfDiscs < 0)
		{
			System.out.println("Error: The discs can’t be lower than 0.");
			System.out.println("");
			System.out.println("How many disks does the server have?");
			numberOfDiscs= lector.nextInt();
			lector.nextLine();
		}
		
		answer = theSystem.initServer(roomNumber, serverNumber, hostedServers, cacheMemory, numberOfProcessors, ramMemory, numberOfDiscs);
		
		if(answer.startsWith("Error"))
		{
			System.out.println(answer);
			rentRoom();
		}
		else
		{					
			for(int i = 0; i < numberOfProcessors; i++)
			{
				initProcessor(i, roomNumber, serverNumber);
			}
			for(int i = 0; i < numberOfDiscs; i++)
			{
				initDisk(i, roomNumber, serverNumber);
			}
		}
	}
	
	public void initProcessor(int processorNumber, int roomNumber, int serverNumber)
	{
		System.out.println("What brand is the processor "+(processorNumber+1)+"?");
		System.out.println("1. INTEL\n2. AMD");
		
		int brand = lector.nextInt();
		lector.nextLine();
		
		while(brand < 1 || brand> 2)
		{
			System.out.println("Error: Option not available.");
			System.out.println("");
			
			System.out.println("What brand is the processor?");
			System.out.println("1. INTEL\n2. AMD");
			
			brand = lector.nextInt();
			lector.nextLine();
		}
		theSystem.initProcessor(roomNumber, serverNumber, processorNumber, brand);
	}
	
	public void initDisk(int diskNumber, int roomNumber, int serverNumber)
	{
		System.out.println("Write down the capacity of the disk "+(diskNumber+1)+" (in teras).");		
		double diskCapacity = lector.nextDouble();
		lector.nextLine();
		
		while(diskCapacity < 0)
		{
			System.out.println("Error: The disk capacity can’t be lower than 0.");
			System.out.println("");
			System.out.println("Write down the capacity of the disks (in teras).");		
			diskCapacity = lector.nextDouble();
			lector.nextLine();
		}
		theSystem.initDisk(roomNumber, serverNumber, diskNumber, diskCapacity);
	}

	public void cancelRoom()
	{
		int roomToCancel;
		
		System.out.println("The client identifies itself as:");
		System.out.println("1. Company.\n2. Research project.");
		int clientType = lector.nextInt();
		lector.nextLine();
		
		while(clientType < 1 || clientType > 2)
		{
			System.out.println("Error: Option not available.");
			rentRoom();
		}
		
		switch(clientType)
		{			
			case 1:
				System.out.println("Write the company nit");
				String nit = lector.nextLine();
				lector.nextLine();
				
				System.out.println("Do you want to cancel all the mini rooms you rent or just one?");
				System.out.println("1. All the mini rooms.\n2. Only one mini room.");
				int decision = lector.nextInt();
				lector.nextLine();
				
				while(decision < 1 || decision > 2)
				{
					System.out.println("Error: Option not available.");
					System.out.println("");
					
					System.out.println("Do you want to cancel all the mini rooms you rent or just one?");
					System.out.println("1. All the mini rooms.\n2. Only one mini room.");
					decision = lector.nextInt();
					lector.nextLine();
				}
				
				if(decision == 2)
				{
					System.out.println("Which room do you want to cancel the rental of?");
					roomToCancel = lector.nextInt();
					lector.nextLine();
					
					String flow = theSystem.validateCancelation(nit, roomToCancel);
					System.out.println(flow);
					
					if(flow.startsWith("Error"))
					{
						menu();
					}
					else
					{
						System.out.println("1. Yes.\n2. No.");
						int answer = lector.nextInt();
						lector.nextLine();
						
						while(answer < 1 || answer > 2)
						{
							System.out.println(theSystem.validateCancelation(nit, roomToCancel));
							System.out.println("1. Yes.\n2. No.");
							
							answer = lector.nextInt();
							lector.nextLine();
						}
						
						if(answer == 1)
						{
							System.out.println(theSystem.cancelRoom(nit, roomToCancel));
						}
						else
						{
							menu();
						}
					}
				}
				else
				{
					System.out.println(theSystem.validateMassiveCancelation(nit));
					System.out.println("1. Yes.\n2. No.");
					int answer = lector.nextInt();
					lector.nextLine();
					
					while(answer < 1 || answer > 2)
					{
						System.out.println(theSystem.validateMassiveCancelation(nit));
						System.out.println("1. Yes.\n2. No.");
						
						answer = lector.nextInt();
						lector.nextLine();
					}
					
					if(answer == 1)
					{
						System.out.println(theSystem.cancelAllRooms(nit));
					}
					else
					{
						menu();
					}
				}
				break;
				
			case 2:
				System.out.println("Write the registry number of your project.");
				String registryNumber = lector.nextLine();
				lector.nextLine();
				
				roomToCancel = theSystem.findRoom(registryNumber);
				
				if(roomToCancel == -1)
				{
					System.out.println("Error: Project registry number not found");
				}
				else
				{
					System.out.println(theSystem.validateCancelation("---_---", roomToCancel));
					System.out.println("1. Yes.\n2. No.");
					int answer = lector.nextInt();
					lector.nextLine();
					
					while(answer < 1 || answer > 2)
					{
						System.out.println(theSystem.validateCancelation("---_---", roomToCancel));
						System.out.println("1. Yes.\n2. No.");
						
						answer = lector.nextInt();
						lector.nextLine();
					}
					
					if(answer == 1)
					{
						System.out.println(theSystem.cancelRoom(registryNumber, roomToCancel));
					}
					else
					{
						menu();
					}
				}
				break;
		}
		menu();
	}
	
	public void showMap()
	{
		String answer = theSystem.showMap();
		
		System.out.println(answer);
	}
	
	public void powerOn()
	{
		String answer = theSystem.simulatePowerOn();
		
		System.out.println("\r Right now, the building looks like this:");
		showMap();
				
		System.out.println("\r If you apply the changes, the building would be as follows:\n");
		System.out.println(answer);
		
		System.out.println("Do you want to apply the changes to the building?");
		System.out.println("1. Yes.\n2. No.");
		int decision = lector.nextInt();
		lector.nextLine();
		
		while(decision < 1 || decision > 2)
		{
			System.out.println("Error: Option not available.");
			System.out.println("");
			
			System.out.println("Do you want to apply the changes to the building?");
			System.out.println("1. Yes.\n2. No.");
			
			decision = lector.nextInt();
			lector.nextLine();
		}
		
		if(decision == 1)
		{
			theSystem.powerOn();
		}
		menu();
	}
	
	public void powerOff()
	{
		String answer;
		
		int position = -1;
		
		System.out.println("Welcome, in this section you can simulate a room shutdown system, the available letters are:\r\n" + 
				"\"L\"\r\n" + 
				"\"Z\"\r\n" + 
				"\"H\"\r\n" + 
				"\"O\"\r\n" + 
				"\"M\"\r\n" + 
				"\"P\" ");
		
		System.out.println("Do you want to see what each letter does?");
		System.out.println("1. Yes.\n2. No.");
		int decision = lector.nextInt();
		lector.nextLine();
		
		while(decision < 1 || decision > 2)
		{
			System.out.println("Error: Option not available.");
			System.out.println("");
			
			System.out.println("Do you want to see what each letter does?");
			System.out.println("1. Yes.\n2. No.");
			
			decision = lector.nextInt();
			lector.nextLine();
		}
		
		if(decision == 1)
		{
			showLettersMenu();
		}
		
		System.out.println("\n Press the letter you want to test, and then press Enter.");
		String desiredLetter = lector.nextLine();
		lector.nextLine();
		
		System.out.println("\r Right now, the building looks like this:");
		showMap();
		
		if(desiredLetter.equalsIgnoreCase("l") || desiredLetter.equalsIgnoreCase("z") || desiredLetter.equalsIgnoreCase("h") || desiredLetter.equalsIgnoreCase("o"))
		{				
			System.out.println("\r If you apply the changes, the building would be as follows:\n");
			
			answer = theSystem.simulatePowerOff(desiredLetter);
			
			System.out.println(answer);
		}
		else if(desiredLetter.equalsIgnoreCase("m"))
		{
			System.out.println("Which column do you want to turn off?");
			int desiredColumn = lector.nextInt();
			lector.nextLine();
			
			while(desiredColumn < 1 || desiredColumn> 50)
			{
				System.out.println("Error: Option not available.");
				System.out.println("");
				
				System.out.println("Which column do you want to turn off?");
				desiredColumn = lector.nextInt();
				lector.nextLine();
			}
			position = desiredColumn-1;
			
			System.out.println("\r If you apply the changes, the building would be as follows:\n");
			System.out.println(theSystem.simulatePowerOffColumn((desiredColumn-1)));
		}
		else if(desiredLetter.equalsIgnoreCase("p"))
		{
			System.out.println("Which row do you want to turn off?");
			int desiredRow = lector.nextInt();
			lector.nextLine();
			
			while(desiredRow < 1 || desiredRow> 8)
			{
				System.out.println("Error: Option not available.");
				System.out.println("");
				
				System.out.println("Which row do you want to turn off?");
				desiredRow = lector.nextInt();
				lector.nextLine();
			}
			position = desiredRow-1;
			
			System.out.println("\r If you apply the changes, the building would be as follows:\n");
			System.out.println(theSystem.simulatePowerOffRow((desiredRow-1)));
		}
		else
		{
			System.out.println("Error: Option not available");
			powerOff();
		}
		
		System.out.println("Do you want to apply the changes to the building?");
		System.out.println("1. Yes.\n2. No.");
		decision = lector.nextInt();
		lector.nextLine();
		
		while(decision < 1 || decision > 2)
		{
			System.out.println("Error: Option not available.");
			System.out.println("");
			
			System.out.println("Do you want to apply the changes to the building?");
			System.out.println("1. Yes.\n2. No.");
			
			decision = lector.nextInt();
			lector.nextLine();
		}
		
		if(decision == 1)
		{
			desiredLetter = desiredLetter.toLowerCase();
			theSystem.powerOff(desiredLetter, position);
		}
		menu();
	}
	
	public void showLettersMenu()
	{
		System.out.println("Letter L: turn off the first mini-rooms of all corridors, along with the mini-rooms of the first corridor.\r\n" + 
				"\r\n" + 
				"Letter Z: turn off the mini-rooms in the first and last corridor, along with the mini-rooms on the reverse diagonal.\r\n" + 
				"\r\n" + 
				"Letter H: turn off the mini-rooms located in the odd-numbered hallways.\r\n" + 
				"\r\n" + 
				"Letter O: turn off the mini-rooms located in the windows.\r\n" + 
				"\r\n" + 
				"Letter M: Turn off all mini-rooms in a column.\r\n" + 
				"\r\n" + 
				"Letter P: turn off the mini-rooms in a corridor.\n");
	}
}