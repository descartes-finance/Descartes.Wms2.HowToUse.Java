package Models;

import com.google.gson.annotations.SerializedName;

public class ProposalOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("name")
	public String Name;
	
	@SerializedName("description")
	public String Description;
	
	@SerializedName("color")
	public String Color;
	
	@SerializedName("code")
	public String Code;
}
