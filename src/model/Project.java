package model;

/**
 * is an object that represents a user, in this case, a project. 
 * @author johan jojoa
 */
public class Project extends User
{
	//Attributes
	
	private String registryNumber;
	
	//Relations
	
	private MiniRoom room;
	
	//Methods
	
	/**
	 * Project constructor, initialize initialize its attributes.
	 * @param companyName "ICESI".
	 * @param registryNumber The project registry number.
	 */
	public Project(String companyName, String registryNumber)
	{
		super(companyName);
		
		this.registryNumber = registryNumber;
	}

	/**
	 * A room is assigned to the project.
	 * @param room The assigned room.
	 */
	public void rentRoom(MiniRoom room)
	{
		this.room = room;
	}
	
	/**
	 * The rental of the room linked to the project is canceled.
	 */
	public void cancelRoom()
	{
		this.room = null;
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