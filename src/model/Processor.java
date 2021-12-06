package model;

public class Processor
{
	//Relations
	
	private Brand brand;
	
	//Methods
		
	public Processor(int brand)
	{
		switch(brand)
		{
			case 1:
				this.brand = Brand.INTEL;
				break;
			
			case 2:
				this.brand = Brand.AMD;
				break;
		}
	}

	
		
	
	
	//Getters and setters
	
	public Brand getBrand()
	{
		return brand;
	}

	public void setBrand(Brand brand)
	{
		this.brand = brand;
	}
}