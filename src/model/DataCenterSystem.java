package model;

/**
 * This class implements the functions chosen by the user.
 * @author johan jojoa
 */
public class DataCenterSystem
{
	public final int CORRIDORS = 8;
	public final int ROOMS_PER_CORRIDOR = 50;
	public final int MAXROOMS = (CORRIDORS*ROOMS_PER_CORRIDOR);
	
	//Relations
	
	private MiniRoom[][] miniRooms;
	private User[] users;
	
	//Methods
	
	/**
	 * DataCenterSystem constructor, initialize the mini rooms and the users.
	 */
	public DataCenterSystem()
	{
		miniRooms = new MiniRoom[CORRIDORS][ROOMS_PER_CORRIDOR];
		users = new User[MAXROOMS];
		
		fillAndAsignate();
	}

	/**
	 * Initialize each room with the basic information that the user does not provide. 
	 */
	public void fillAndAsignate()
	{
		boolean window = true;
		int roomNumber = 101;
		
        for(int i = 0; i < (CORRIDORS - (CORRIDORS - 1)); i++)
        {
            for(int j = 0; j < miniRooms[i].length; j++)
            {
            	miniRooms[i][j] = new MiniRoom(window, roomNumber);
            	roomNumber++;
            }
        }
        
        roomNumber += 50;
        
        for(int i = (CORRIDORS - (CORRIDORS - 1)); i <= (miniRooms.length - 2) ; i++)
        {
            for(int j = 0; j < miniRooms[i].length; j++)
            {
            	if(j == 0 || j == (miniRooms[i].length-1))
            	{
            		window = true;
            	}
            	else
            	{
            		window = false;
            	}
            	
            	miniRooms[i][j] = new MiniRoom(window, roomNumber);
            	roomNumber++;
            }
            roomNumber += 50;
        }
        
        for(int i = (CORRIDORS - 1); i < miniRooms.length; i++)
        {
            for(int j = 0; j < miniRooms[i].length; j++)
            {
            	miniRooms[i][j] = new MiniRoom(window, roomNumber);
            	roomNumber++;
            }
        }
    }
	
	/**
	 * Create a list of available rooms.
	 * @param condition If this condition is true, only the list of rooms that are in windows or those that are not can be displayed.
	 * @param windowB It expresses whether a room is sought in the window or not.
	 * @return The list that will be displayed to the user on the screen.
	 */
	public String generateList(boolean condition, boolean windowB) 
	{
		String answer = "| Room number | Have a window | Corridor | Column | Rental cost";
		
		if(condition == false)
		{
			 for(int i = 0 ; i < miniRooms.length; i++)
			 {
				 for(int j = 0; j < miniRooms[i].length; j++)
				 {
					 if(miniRooms[i][j].isAvailable() == true)
					 {
						 answer += miniRooms[i][j].toString();
					 }
				 }
			 }
		}
		else if(condition == true)
		{
			for(int i = 0; i < miniRooms.length; i++)
	        {
	            for(int j = 0; j < miniRooms[i].length; j++)
	            {
	            	if(miniRooms[i][j].isAvailable() == true)
	            	{
	            		if(miniRooms[i][j].haveWindow() == windowB)
		            	{
		            		answer += miniRooms[i][j].toString();
		            	}
	            	}
	            }
	        }
		}
		
		if(answer == "")
		{
			answer = "There are no mini rooms available";
		}
		
		return answer;
	}
	
	/**
	 * Register an user in the system. 
	 * @param userId String that represents the nit of a company or the registration number of a project.
	 * @param companyName Name of the company that will be in the room, if it is a project, the company will be "ICESI".
	 * @return Message that validates the correct registration of the user or shows an error if any data was entered incorrectly.
	 */
	public String registerUser(String userId, String companyName)
	{
		String message = "";
		boolean condition = true;
		
		if(companyName.equalsIgnoreCase("ICESI"))
		{
			for(int i = 0; i < users.length; i++)
			{
				if(users[i] != null)
				{
					Project p = (Project)users[i];
					if(p.getRegistryNumber() == userId)
					{
						message = "Error: The project already has a mini room";
						condition = false;
					}
				}
			}
			
			if(condition)
			{
				for(int i = 0; i < users.length; i++)
				{
					if(users[i] == null)
					{
						users[i] = new Project(companyName, userId);
						
						message = "User was successfully registered";
					}
				}
			}
		}
		else
		{
			for(int i = 0; i < users.length; i++)
			{
				if(users[i] != null)
				{
					Company c = (Company)users[i];
					if(c.getNit() == userId)
					{
						message = "Error: The company already has one or more mini rooms";
						condition = false;
					}
				}
			}
			
			if(condition)
			{
				for(int i = 0; i < users.length; i++)
				{
					if(users[i] == null)
					{
						users[i] = new Company(companyName, userId, MAXROOMS);
						
						message = "User was successfully registered";
					}
				}
			}
		}
		
		if(message == "")
		{
			message = "Error: No room for more users.";
		}
		return message;
	}
	
	/**
	 * Assign a project a room.
	 * @param companyName The name of the company that will be in the room, "ICESI".
	 * @param userId String that represents the registration number of a project.
	 * @param roomNumber The room to which the values will be modified and assigned to the project.
	 * @param rentalDate The date the rent is made.
	 * @return Message that validates the correct rent of a room or shows an error if any data was entered incorrectly.
	 */
	public String rentRoom(String companyName, String userId, int roomNumber, String rentalDate)
	{
		String answer = "The room has been rented successfully";
		
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		searchedRoom.setAvailable(false);
		searchedRoom.setOwnerId(userId);
		searchedRoom.setRentalDate(rentalDate);
		searchedRoom.setOn(true);
		
		Project user = searchForRegistryNumber(userId);
		
		user.rentRoom(searchedRoom);
		
		return answer;
	}
	
	/**
	 * Assign a company a room.
	 * @param userId String that represents the nit of a company.
	 * @param roomNumber The room to which the values will be modified and assigned to the company.
	 * @param rentalDate The date the rent is made.
	 * @return Message that validates the correct rent of a room or shows an error if any data was entered incorrectly.
	 */
	public String rentRoom(String userId, int roomNumber, String rentalDate)
	{
		String answer = "The room has been rented successfully";
		
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		searchedRoom.setAvailable(false);
		searchedRoom.setOwnerId(userId);
		searchedRoom.setRentalDate(rentalDate);
		searchedRoom.setOn(true);
		
		Company user = searchForNit(userId);
		
		user.rentRoom(searchedRoom);
		
		return answer;
	}
	
	/**
	 * Initialize a mini room Server.
	 * @param roomNumber The mini room to which the server belongs.
	 * @param serverNumber The number in the room's server array to initialize.
	 * @param hostedServers The number of servers housed in the room.
	 * @param cacheMemory The cache that the server will have.
	 * @param numberOfProcessors The number of processors the server will have.
	 * @param ramMemory The RAM that the server will have.
	 * @param numberOfDiscs The number of discs the server will have.
	 * @return Message that validates the correct initialization of a server or shows an error if any data was entered incorrectly.
	 */
	public String initServer(int roomNumber, int serverNumber, int hostedServers, double cacheMemory, int numberOfProcessors, double ramMemory, int numberOfDiscs)
	{
		String answer = "";
		
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		if(searchedRoom == null)
		{
			answer = "Error: that mini room does not exist.";
		}
		else if(searchedRoom.isAvailable() == false)
		{
			answer = "Error: That mini room is not available.";
		}
		else
		{
			answer = searchedRoom.initServer(serverNumber, hostedServers, cacheMemory, numberOfProcessors, ramMemory, numberOfDiscs);
		}
		return answer;
	}
	
	/**
	 * Initialize a server processor.
	 * @param roomNumber The mini room to which the processor belongs.
	 * @param serverNumber The server to which the processor belongs.
	 * @param processorNumber The processor number in the server's array to initialize.
	 * @param brand Is the processor's brand.
	 */
	public void initProcessor(int roomNumber, int serverNumber, int processorNumber, int brand)
	{
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		searchedRoom.initProcessor(serverNumber, processorNumber, brand);
	}
	
	/**
	 * Initialize a server disk.
	 * @param roomNumber The mini room to which the disk belongs.
	 * @param serverNumber The server to which the disk belongs.
	 * @param diskNumber The disk number in the server's array to initialize.
	 * @param diskCapacity Is the capacity of the disk.
	 */
	public void initDisk(int roomNumber, int serverNumber, int diskNumber, double diskCapacity)
	{
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		searchedRoom.initDisk(serverNumber, diskNumber, diskCapacity);
	}
	
	/**
	 * Asks the company to verify if they want to cancel the rental of a room, showing their processing capacity.
	 * @param nit Company's nit.
	 * @param roomToCancel The room that the company is about to cancel.
	 * @return A message asking the company if you really want to cancel your rental.
	 */
	public String validateCancelation(String nit, int roomToCancel)
	{
		String answer = "";
		
		if(nit == "---_---")
		{
			answer = "Do you really want to cancel the rental?";
			
			MiniRoom searchedRoom = searchForRoomNumber(roomToCancel);
			
			int totalDisk = searchedRoom.calculateDisk();
			
			int totalRam = searchedRoom.calculateRam();
			
			answer += "\n\nYou have a processing capacity of:\r\n" + 
					"total disk capacity: "+totalDisk+"\r\n" + 
					"total RAM memory: "+totalRam;
		}
		else
		{
			Company company = searchForNit(nit);
			
			if(company != null)
			{
				answer = "Do you really want to cancel the rental?";
				
				MiniRoom searchedRoom = searchForRoomNumber(roomToCancel);
				
				int totalDisk = searchedRoom.calculateDisk();
				
				int totalRam = searchedRoom.calculateRam();
				
				answer += "\n\nYou have a processing capacity of:\r\n" + 
						"total disk capacity: "+totalDisk+"\r\n" + 
						"total RAM memory: "+totalRam;
			}
			else
			{
				answer = "Error: Company nit not found.";
			}
		}
		
		return answer;
	}
	
	/**
	 * Cancel the rent of a room for a user.
	 * @param userId String that represents the nit of a company or the registration number of a project.
	 * @param roomToCancel The room that the user wants to cancel.
	 * @return Message that validates the correct the correct cancellation of the rental or shows an error if any data was entered incorrectly.
	 */
	public String cancelRoom(String userId, int roomToCancel)
	{
		String answer = "";
		
		MiniRoom searchedRoom = searchForRoomNumber(roomToCancel);
		
		if(searchedRoom == null)
		{
			answer = "Error: that mini room does not exist.";
		}
		else if(searchedRoom.isAvailable() == true)
		{
			answer = "Error: That mini room is available.";
		}
		else
		{
			searchedRoom.setAvailable(true);
			searchedRoom.setOwnerId("");
			searchedRoom.setRentalDate("");
			searchedRoom.setHostedServers(0);
			searchedRoom.deleteServers();
			searchedRoom.setOn(false);
			
			Project project = searchForRegistryNumber(userId);
			
			if(project == null)
			{
				Company company = searchForNit(userId);
				company.cancelRoom(roomToCancel);
			}
			else
			{
				project.cancelRoom();
			}
		}
		
		return answer;
	}
	
	/**
	 * Asks the company to verify if they want to cancel the rent of all the rooms, showing their total processing capacity.
	 * @param nit Company's nit.
	 * @return A message asking the company if you really want to cancel the rent of all the rooms.
	 */
	public String validateMassiveCancelation(String nit)
	{
		int totalDisk = 0;
		int totalRam = 0;
		
		String answer = "Do you really want to cancel the rental?";
		
        for(int i = 0; i < CORRIDORS; i++)
        {
            for(int j = 0; j < miniRooms[i].length; j++)
            {
            	if(miniRooms[i][j].getOwnerId() == nit)
            	{
            		totalDisk += miniRooms[i][j].calculateDisk();
            		
            		totalRam += miniRooms[i][j].calculateRam();
            	}
            }
        }
        
		answer += "\n\nYou have a processing capacity of:\r\n" + 
				"total disk capacity: "+totalDisk+"\r\n" + 
				"total RAM memory: "+totalRam;
		
		return answer;
	}
	
	/**
	 * Cancel the rent of all the rooms for a company.
	 * @param nit Company's nit.
	 * @return Message that validates the correct cancellation of all the rooms or shows an error if any data was entered incorrectly.
	 */
	public String cancelAllRooms(String nit)
	{
		String answer = "";
		
		for(int i = 0; i < CORRIDORS; i++)
        {
            for(int j = 0; j < miniRooms[i].length; j++)
            {
            	if(miniRooms[i][j].getOwnerId() == nit)
            	{
            		miniRooms[i][j].setAvailable(true);
            		miniRooms[i][j].setOwnerId("");
            		miniRooms[i][j].setRentalDate("");
            		miniRooms[i][j].setHostedServers(0);
            		miniRooms[i][j].deleteServers();
            		miniRooms[i][j].setOn(false);
            		
            		Company company = searchForNit(nit);
    				company.cancelAllRooms();
            	}
            }
        }
		
		return answer;
	}

	/**
	 * Creates a map of the building, where rooms are on and rooms are off.
	 * @return Building map.
	 */
	public String showMap()
	{
		String answer = "";
		
		int count = 0;
		
		answer += "\r";
		
		for(int i = 0; i < miniRooms.length; i++)
		{
			for(int j = 0; j < miniRooms[i].length; j++)
			{
				if(miniRooms[i][j].getisOn() == true)
			 	{
					answer += (char)164;
					count++;
			 	}
				else
				{
					answer += (char)167;
					count++;
				}
				
				if(count == 50)
				{
					answer += "\r";
					answer += "\n";
					count = 0;
				}
			}
		}
		
		answer += "\r\n The mini rooms turned on have this icon: "+(char)164;
		answer += "\r\n The mini rooms turned off have this icon: "+(char)167+"\n";
		
		return answer;
	}
	
	/**
	 * Creates a map of the building, where all the rooms are on.
	 * @return Building map.
	 */
	public String simulatePowerOn()
	{
		String answer = "";
		
		int count = 0;
		
		for(int i = 0; i < miniRooms.length; i++)
		{
			for(int j = 0; j < miniRooms[i].length; j++)
			{
				answer += (char)164;
				count++;
			 					
				if(count == 50)
				{
					answer += "\r\n";
					count = 0;
				}
			}
		}
		
		answer += "\r\n The mini rooms turned on have this icon: "+(char)164;
		answer += "\r\n The mini rooms turned off have this icon: "+(char)167+"\n";
		
		return answer;	
	}
	
	/**
	 * Power on all the building rooms.
	 * @return A success message.
	 */
	public String powerOn()
	{
		String answer = "\n Success.\n";
		
		for(int i = 0; i < miniRooms.length; i++)
        {
            for(int j = 0; j < miniRooms[i].length; j++)
            {
            	miniRooms[i][j].setOn(true);
            }
        }
		
		return answer;	
	}
	
	/**
	 * Create a map of the building, where the rooms will be turned off based on the letter entered by the user.
	 * @param desiredLetter The letter entered by the user.
	 * @return Building map.
	 */
	public String simulatePowerOff(String desiredLetter)
	{
		String answer = "";
		
		String letter = desiredLetter.toLowerCase();
		
		int count = 0;
		
		switch(letter)
		{
			case"l":
				count = 0;
				
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(i == 0)
						{
							answer += (char)167;
							count++;
						}
						else
						{
							if(j == 0)
							{
								answer += (char)167;
								count++;
							}
							else
							{
								if(miniRooms[i][j].getisOn() == true)
							 	{
									answer += (char)164;
									count++;
							 	}
								else
								{
									answer += (char)167;
									count++;
								}
							}
						}
						if(count == 50)
						{
							answer += "\r\n";
							count = 0;
						}
					}
				}
				
				break;
			
			case"z":	
				count = 0;
				
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(i == 0 || i == 7)
						{
							answer += (char)167;
							count++;
						}
						else
						{
							if(i == 1)
							{
								if(j >= 6 && j <= 11)
								{
									answer += (char)167;
									count++;
								}
								else
								{
									if(miniRooms[i][j].getisOn() == true)
									{
										answer += (char)164;
										count++;
									}
									else
									{
										answer += (char)167;
										count++;
									}
								}
							}
							else if(i == 2)
							{
								if(j >= 12 && j <= 17)
								{
									answer += (char)167;
									count++;
								}
								else
								{
									if(miniRooms[i][j].getisOn() == true)
									{
										answer += (char)164;
										count++;
									}
									else
									{
										answer += (char)167;
										count++;
									}
								}
							}
							else if(i == 3)
							{
								if(j >= 18 && j <= 23)
								{
									answer += (char)167;
									count++;
								}
								else
								{
									if(miniRooms[i][j].getisOn() == true)
									{
										answer += (char)164;
										count++;
									}
									else
									{
										answer += (char)167;
										count++;
									}
								}
							}
							else if(i == 4)
							{
								if(j >= 24 && j <= 29)
								{
									answer += (char)167;
									count++;
								}
								else
								{
									if(miniRooms[i][j].getisOn() == true)
									{
										answer += (char)164;
										count++;
									}
									else
									{
										answer += (char)167;
										count++;
									}
								}
							}
							else if(i == 5)
							{
								if(j >= 30 && j <= 35)
								{
									answer += (char)167;
									count++;
								}
								else
								{
									if(miniRooms[i][j].getisOn() == true)
									{
										answer += (char)164;
										count++;
									}
									else
									{
										answer += (char)167;
										count++;
									}
								}
							}
							else if(i == 6)
							{
								if(j >= 36 && j <= 41)
								{
									answer += (char)167;
									count++;
								}
								else
								{
									if(miniRooms[i][j].getisOn() == true)
									{
										answer += (char)164;
										count++;
									}
									else
									{
										answer += (char)167;
										count++;
									}
								}
							}
						}
						if(count == 50)
						{
							answer += "\r\n";
							count = 0;
						}
					}
				}
				
				break;
				
			case"h":
				count = 0;
				
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if((i%2) == 0)
						{
							answer += (char)167;
							count++;
						}
						else
						{
							if(miniRooms[i][j].getisOn() == true)
						 	{
								answer += (char)164;
								count++;
						 	}
							else
							{
								answer += (char)167;
								count++;
							}
						}
						if(count == 50)
						{
							answer += "\r\n";
							count = 0;
						}
					}
				}				
				
				break;
				
			case"o":
				count = 0;
				
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(miniRooms[i][j].haveWindow())
						{
							answer += (char)167;
							count++;
						}
						else
						{
							if(miniRooms[i][j].getisOn() == true)
						 	{
								answer += (char)164;
								count++;
						 	}
							else
							{
								answer += (char)167;
								count++;
							}
						}
						if(count == 50)
						{
							answer += "\r\n";
							count = 0;
						}
					}
				}
				
				break;			
		}
		
		answer += "\r\n The mini rooms turned on have this icon: "+(char)164;
		answer += "\r\n The mini rooms turned off have this icon: "+(char)167+"\n";
		
		return answer;	
	}
	
	/**
	 * Create a map of the building, where a column will be turned off based on a number entered by the user.
	 * @param column The column that the user wants to turn off.
	 * @return Building map.
	 */
	public String simulatePowerOffColumn(int column)
	{
		String answer = "";
		
		int count = 0;
		
		for(int i = 0; i < miniRooms.length; i++)
		{
			for(int j = 0; j < miniRooms[i].length; j++)
			{						
				if(j == column)
				{
					answer += (char)167;
					count++;
				}
				else
				{
					if(miniRooms[i][j].getisOn() == true)
				 	{
						answer += (char)164;
						count++;
				 	}
					else
					{
						answer += (char)167;
						count++;
					}
				}
				if(count == 50)
				{
					answer += "\r\n";
					count = 0;
				}
			}
		}
		
		answer += "\r\n The mini rooms turned on have this icon: "+(char)164;
		answer += "\r\n The mini rooms turned off have this icon: "+(char)167+"\n";
		
		return answer;
	}
	
	/**
	 * Create a map of the building, where a row will be turned off based on a number entered by the user.
	 * @param row The row that the user wants to turn off.
	 * @return Building map.
	 */
	public String simulatePowerOffRow(int row)
	{
		String answer = "";
		
		int count = 0;
		
		for(int i = 0; i < miniRooms.length; i++)
		{
			for(int j = 0; j < miniRooms[i].length; j++)
			{						
				if(i == row)
				{
					answer += (char)167;
					count++;
				}
				else
				{
					if(miniRooms[i][j].getisOn() == true)
				 	{
						answer += (char)164;
						count++;
				 	}
					else
					{
						answer += (char)167;
						count++;
					}
				}
				if(count == 50)
				{
					answer += "\r\n";
					count = 0;
				}
			}
		}
		
		answer += "\r\n The mini rooms turned on have this icon: "+(char)164;
		answer += "\r\n The mini rooms turned off have this icon: "+(char)167+"\n";
		
		return answer;
	}	
	
	/**
	 * Power off the building rooms based on a letter entered by the user.
	 * @param desiredLetter The letter entered by the user.
	 * @param position If the user chooses the letter m or p, it will be the row or column that they want to turn off.
	 * @return A success message.
	 */
	public String powerOff(String desiredLetter, int position)
	{
		String answer = "\n Success.\n";
		
		String letter = desiredLetter.toLowerCase();
		
		switch(letter)
		{
			case"l":
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(i == 0)
						{
							miniRooms[i][j].setOn(false);
						}
						else if(j == 0)
						{
							miniRooms[i][j].setOn(false);
						}
					}
				}
				
				break;
			
			case"z":	
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(i == 0 || i == 7)
						{
							miniRooms[i][j].setOn(false);
						}
						else
						{
							if(i == 1)
							{
								if(j >= 6 && j <= 11)
								{
									miniRooms[i][j].setOn(false);
								}
							}
							else if(i == 2)
							{
								if(j >= 12 && j <= 17)
								{
									miniRooms[i][j].setOn(false);
								}
							}
							else if(i == 3)
							{
								if(j >= 18 && j <= 23)
								{
									miniRooms[i][j].setOn(false);
								}
							}
							else if(i == 4)
							{
								if(j >= 24 && j <= 29)
								{
									miniRooms[i][j].setOn(false);
								}
							}
							else if(i == 5)
							{
								if(j >= 30 && j <= 35)
								{
									miniRooms[i][j].setOn(false);
								}
							}
							else if(i == 6)
							{
								if(j >= 36 && j <= 41)
								{
									miniRooms[i][j].setOn(false);
								}
							}
						}
					}
				}
				
				break;
				
			case"h":
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if((i%2) == 0)
						{
							miniRooms[i][j].setOn(false);
						}
					}
				}				
				
				break;
				
			case"o":
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(miniRooms[i][j].haveWindow())
						{
							miniRooms[i][j].setOn(false);
						}
					}
				}
				
				break;
			
			case "m":
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(j == position)
						{
							miniRooms[i][j].setOn(false);
						}
					}
				}
				
				break;
				
			case "p":
				for(int i = 0; i < miniRooms.length; i++)
				{
					for(int j = 0; j < miniRooms[i].length; j++)
					{						
						if(i == position)
						{
							miniRooms[i][j].setOn(false);
						}
					}
				}
				
				break;
		}
		
		return answer;	
	}
	
	/**
	 * Find the room that belongs to a project based on its registration number.
	 * @param registryNumber The project registration number.
	 * @return The room number.
	 */
	public int findRoom(String registryNumber)
	{
		int answer = 0;
		
		MiniRoom room = null;
		
		Project project = searchForRegistryNumber(registryNumber);
		
		if(project != null)
		{
			room = project.getRoom();
			if(room != null)
			{
				answer = room.getRoomNumber();
			}
			else
			{
				answer = -1;
			}
		}
		else
		{
			answer = -1;
		}
		
		return answer;
	}
	
	/**
	 * Search for a room based on its room number.
	 * @param roomNumber The room number.
	 * @return If it finds the room it will return that object, otherwise it will return null.
	 */
	public MiniRoom searchForRoomNumber(int roomNumber)
	{
		MiniRoom searchMiniRoom = null;
		for (int i = 0; i < CORRIDORS ; i++)
		{
			for(int j = 0; j < ROOMS_PER_CORRIDOR; j++)
			{
				if(miniRooms[i][j] != null && roomNumber == (miniRooms[i][j].getRoomNumber()))
				{
					searchMiniRoom = miniRooms[i][j];
				}
			}
		}
		return searchMiniRoom;
	}

	/**
	 * Search for a project based on its registry number.
	 * @param registryNumber The project registry number.
	 * @return If it finds the project it will return that object, otherwise it will return null.
	 */
	public Project searchForRegistryNumber(String registryNumber)
	{
		Project searchUser = null;
		
		for(int i = 0; i < users.length; i++)
		{
			Project user = (Project)users[i];
			
			if(users[i] != null && registryNumber == (user.getRegistryNumber()))
			{
				searchUser = user;
			}
		}
		return searchUser;
	}
	
	/**
	 * Search for a company based on its nit.
	 * @param nit The company nit.
	 * @return If it finds the company it will return that object, otherwise it will return null.
	 */
	public Company searchForNit(String nit)
	{
		Company searchUser = null;
		
		for(int i = 0; i < users.length; i++)
		{
			Company user = (Company)users[i];
			
			if(users[i] != null && nit == (user.getNit()))
			{
				searchUser = user;
			}
		}
		return searchUser;
	}
}
	