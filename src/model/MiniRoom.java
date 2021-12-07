package model;

/**
 * This class is an object that represents a mini room. 
 * @author johan jojoa
 */
public class MiniRoom
{
	public final double FIXED_COST = 100;
	
	//Attributes
	
	private boolean window;
	private double rentalCost;
	private int roomNumber;
	private int hostedServers;
	private boolean available;
	private String rentalDate;
	private String ownerId;
	private boolean isOn;
	
	//Relations
	
	private Server[] servers;
	
	//Methods
	
	/**
	 * MiniRoom constructor.
	 * @param window Represents if the room have a window.
	 * @param roomNumber The number of the room.
	 */
	public MiniRoom(boolean window, int roomNumber)
	{
		this.window = window;
		this.roomNumber = roomNumber;
		
		available = true;
		calculateRentalCost();	
		
		hostedServers = 0;
		rentalDate = "";
		ownerId = "";
		isOn = false;
	}
	
	/**
	 * Calculates the rental cost of the room.
	 */
	public void calculateRentalCost()
	{
		double totalCost;
		double variableCost = 0;
		
		if(window == true)
		{
			variableCost = 0.1;
		}
		
		totalCost = FIXED_COST-(FIXED_COST*variableCost);
		
		if(roomNumber > 700 && roomNumber < 751)
		{
			variableCost = 0.15;
			totalCost -= (totalCost*variableCost);
		}
		else if(roomNumber > 200 && roomNumber < 651)
		{
			variableCost = 0.25;
			totalCost += (totalCost*variableCost);
		}
		
		this.rentalCost = totalCost;
	}
	
	/**
	 * Initialize the servers array.
	 * @param hostedServers 
	 */
	public void defineServers(int hostedServers)
	{
		if(servers == null)
		{
			servers = new Server[hostedServers];
		}
	}
	
	/**
	 * Initialize the servers.
	 * @param serverNumber
	 * @param hostedServers
	 * @param cacheMemory
	 * @param numberOfProcessors
	 * @param ramMemory
	 * @param numberOfDiscs
	 * @return
	 */
	public String initServer(int serverNumber, int hostedServers, double cacheMemory, int numberOfProcessors, double ramMemory, int numberOfDiscs)
	{
		String answer = "The room has been rented successfully";
		
		defineServers(hostedServers);
		
		servers[serverNumber] = new Server(cacheMemory, numberOfProcessors, ramMemory, numberOfDiscs);
		
		return answer;
	}
	
	/**
	 * Initialize the processors.
	 * @param serverNumber
	 * @param processorNumber
	 * @param brand
	 */
	public void initProcessor(int serverNumber, int processorNumber, int brand)
	{
		servers[serverNumber].initProcessor(processorNumber, brand);
	}
	
	/**
	 * Initialize the discs.
	 * @param serverNumber
	 * @param diskNumber
	 * @param diskCapacity
	 */
	public void initDisk(int serverNumber, int diskNumber, double diskCapacity)
	{
		servers[serverNumber].initDisk(diskNumber, diskCapacity);
	}
	
	/**
	 * Calculates the disk capacity.
	 * @return
	 */
	public int calculateDisk()
	{
		int result = 0;
		
		for(int i = 0; i < servers.length; i++)
		{
			result += servers[i].calculateDisk();
		}
		
		return result;
	}
	
	/**
	 * Calculates the RAM capacity.
	 * @return
	 */
	public int calculateRam()
	{
		int result = 0;
		
		for(int i = 0; i < servers.length; i++)
		{
			result += servers[i].getRamMemory();
		}
		
		return result;
	}
	
	/**
	 * Erase one server.
	 */
	public void deleteServers()
	{
		for(int i = 0; i < servers.length; i++)
		{
			servers = null;
		}
	}
	
	/**
	 * Print the mini room info.
	 */
	public String toString()
	{
		String windowString;
		int corridor;
		int column;
		String columnString;
		
		String info = "";
		
		if(window == true)
		{
			windowString = "      Yes      ";
		}
		else
		{
			windowString = "      No       ";
		}
		
		corridor = roomNumber/100;
		column = roomNumber-(corridor*100);
		
		if(column >= 10)
		{
			columnString = "   "+column+"   ";
		}
		else
		{
			columnString = "    "+column+"   ";
		}
		
		info += "\n|     "+roomNumber+"     |"+windowString+"|     "+corridor+"    |"+columnString+"| $ "+rentalCost;
		
		return info;
	}
	
	//Getters and setters
	
	public boolean haveWindow()
	{
		return window;
	}

	public void setWindow(boolean window)
	{
		this.window = window;
	}

	public double getRentalCost()
	{
		return rentalCost;
	}

	public void setRentalCost(double rentalCost)
	{
		this.rentalCost = rentalCost;
	}

	public int getRoomNumber()
	{
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber)
	{
		this.roomNumber = roomNumber;
	}

	public boolean isAvailable()
	{
		return available;
	}
	
	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	public int getHostedServers()
	{
		return hostedServers;
	}

	public void setHostedServers(int hostedServers)
	{
		this.hostedServers = hostedServers;
	}

	public String getRentalDate()
	{
		return rentalDate;
	}

	public void setRentalDate(String rentalDate)
	{
		this.rentalDate = rentalDate;
	}

	public String getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	public boolean isWindow()
	{
		return window;
	}
	
	public boolean getisOn()
	{
		return this.isOn;
	}

	public void setOn(boolean isOn)
	{
		this.isOn = isOn;
	}
}