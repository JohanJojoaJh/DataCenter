package model;

public class Server
{
	//Attributes
	
	private double cacheMemory;
	private int numberOfProcessors;
	private double ramMemory;
	private int numberOfDiscs;
	
	//Relations
	
	private Processor[] processors;
	private Disk[] discs;
	
	//Methods
	
	public Server(double cacheMemory, int numberOfProcessors, double ramMemory, int numberOfDiscs)
	{
		this.cacheMemory = cacheMemory;
		this.numberOfProcessors = numberOfProcessors;
		this.ramMemory = ramMemory;
		this.numberOfDiscs = numberOfDiscs;
		
		processors = new Processor[numberOfProcessors];
		discs = new Disk[numberOfDiscs];
	}

	public void initProcessor(int processorNumber, int brand)
	{
		processors[processorNumber] = new Processor(brand);
	}
	
	public void initDisk(int diskNumber, double diskCapacity)
	{
		discs[diskNumber] = new Disk(diskCapacity);
	}
	
	public int calculateDisk()
	{
		int result = 0;
		
		for(int i = 0; i < discs.length; i++)
		{
			result += discs[i].getDiskCapacity();
		}
		
		return result;
	}
	
	//Getters and setters
	
	public double getCacheMemory()
	{
		return cacheMemory;
	}

	public void setCacheMemory(double cacheMemory)
	{
		this.cacheMemory = cacheMemory;
	}

	public int getNumberOfProcessors()
	{
		return numberOfProcessors;
	}

	public void setNumberOfProcessors(int numberOfProcessors)
	{
		this.numberOfProcessors = numberOfProcessors;
	}

	public double getRamMemory()
	{
		return ramMemory;
	}

	public void setRamMemory(double ramMemory)
	{
		this.ramMemory = ramMemory;
	}

	public int getNumberOfDiscs()
	{
		return numberOfDiscs;
	}

	public void setNumberOfDiscs(int numberOfDiscs)
	{
		this.numberOfDiscs = numberOfDiscs;
	}
}