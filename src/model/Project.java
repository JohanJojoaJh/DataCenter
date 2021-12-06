package model;

public class Project extends User
{
	//Attributes
	
	private String registryNumber;
	
	//Relations
	
	private MiniRoom room;
	
	//Methods
	
	public Project(String companyName, String registryNumber)
	{
		super(companyName);
		
		this.registryNumber = registryNumber;
	}

	public void rentRoom(MiniRoom room)
	{
		this.room = room;
	}
	
	
	
	
	
	//Getters and setters
	
	public String getRegistryNumber()
	{
		return registryNumber;
	}

	public void setRegistryNumber(String registryNumber)
	{
		this.registryNumber = registryNumber;
	}
	
	public MiniRoom getRoom()
	{
		return room;
	}

	public void setRoom(MiniRoom room)
	{
		this.room = room;
	}
}