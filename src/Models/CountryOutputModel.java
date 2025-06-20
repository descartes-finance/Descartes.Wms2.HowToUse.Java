package Models;

import com.google.gson.annotations.SerializedName;

public class CountryOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("code")
	public String Code;
	 
	@SerializedName("name")
	public String Name;
}