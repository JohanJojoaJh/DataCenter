package model;

/**
 * This class is an object that represents a user, in this case, a company. 
 * @author johan jojoa
 */
public class Company extends User
{
	//Attributes
	
	private String nit;
	
	//Relations
	
	private MiniRoom[] rooms;
	
	//Methods
	
	/**
	 * Company constructor, initialize initialize its attributes.
	 * @param companyName The company name.
	 * @param nit The company nit.
	 * @param maxRooms Represents the rooms that the company may have.
	 */
	public Company(String companyName, String nit, int maxRooms)
	{
		super(companyName);
		
		this.nit = nit;
		
		rooms = new MiniRoom[maxRooms];
	}
	
	/**
	 * Rent a room.
	 * @param room The rented room.
	 */
	public void rentRoom(MiniRoom room)
	{
		for(int i = 0; i < rooms.length; i++)
		{
			if(rooms[i] == null)
			{
				rooms[i]= room;
			}
		}
	}
	
	/**
	 * Cancel the rental of a room.
	 * @param room The cancelled room.
	 */
	public void cancelRoom(int room)
	{
		for(int i = 0; i < rooms.length; i++)
		{
			if(rooms[i] != null)
			{
				if(rooms[i].getRoomNumber() == room)
				{
					rooms[i] = null;
				}
			}
		}
	}
	
	/**
	 * Cancel the rental for all rooms.
	 */
	public void cancelAllRooms()
	{
		for(int i = 0; i < rooms.length; i++)
		{
			if(rooms[i] != null)
			{
				rooms[i] = null;
			}
		}
	}
	
	
	
	//Getters and setters
	
	public String getNit()
	{
		return nit;
	}

	public void setNit(String nit)
	{
		this.nit = nit;
	}
}