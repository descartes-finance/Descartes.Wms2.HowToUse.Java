package Models;

import com.google.gson.annotations.SerializedName;

public class BankAccountTypeOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("code")
	public String Code;
	
	@SerializedName("description")
	public String Description;
}
