package model;

public class DataCenterSystem
{
	public final int CORRIDORS = 8;
	public final int ROOMS_PER_CORRIDOR = 50;
	public final int MAXROOMS = (CORRIDORS*ROOMS_PER_CORRIDOR);
	
	//Relations
	
	private MiniRoom[][] miniRooms;
	private User[] users;
	
	//Methods
	
	public DataCenterSystem()
	{
		miniRooms = new MiniRoom[CORRIDORS][ROOMS_PER_CORRIDOR];
		users = new User[MAXROOMS];
		
		fillAndAsignate();
	}

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
		else
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
	
	public void initProcessor(int roomNumber, int serverNumber, int processorNumber, int brand)
	{
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		searchedRoom.initProcessor(serverNumber, processorNumber, brand);
	}
	
	public void initDisk(int roomNumber, int serverNumber, int diskNumber, double diskCapacity)
	{
		MiniRoom searchedRoom = searchForRoomNumber(roomNumber);
		
		searchedRoom.initDisk(serverNumber, diskNumber, diskCapacity);
	}
	
	public String validateCancelation(int roomToCancel)
	{
		String answer = "Do you really want to cancel the rental?";
		
		MiniRoom searchedRoom = searchForRoomNumber(roomToCancel);
		
		int totalDisk = searchedRoom.calculateDisk();
		
		int totalRam = searchedRoom.calculateRam();
		
		answer += "\n\nYou have a processing capacity of:\r\n" + 
				"total disk capacity: "+totalDisk+"\r\n" + 
				"total RAM memory: "+totalRam;
		
		return answer;
	}
	
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
		}
		
		return answer;
	}
	
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
            	}
            }
        }
		
		return answer;
	}

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
		
	public int findRoom(String registryNumber)
	{
		int answer = 0;
		
		Project project = searchForRegistryNumber(registryNumber);
		
		MiniRoom room = project.getRoom();
		
		answer = room.getRoomNumber();
		
		return answer;
	}
	
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
	