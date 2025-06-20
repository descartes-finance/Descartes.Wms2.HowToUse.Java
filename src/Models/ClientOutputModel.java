package Models;

import com.google.gson.annotations.SerializedName;

public class ClientOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("accountNumber")
	public String AccountNumber;
	
	@SerializedName("name")
	public String Name;
	
	@SerializedName("surname")
	public String Surname;
	
	@SerializedName("email")	
	public String Email;
	
	@SerializedName("phoneNumber")
	public String PhoneNumber;
}
