package model;

public class Company extends User
{
	//Attributes
	
	private String nit;
	
	//Relations
	
	private MiniRoom[] rooms;
	
	//Methods
	
	public Company(String companyName, String nit, int maxRooms)
	{
		super(companyName);
		
		this.nit = nit;
		
		rooms = new MiniRoom[maxRooms];
	}

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