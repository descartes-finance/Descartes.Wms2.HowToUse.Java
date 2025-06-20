package Models;

import com.google.gson.annotations.SerializedName;

public class BookingCodeOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("accountHoldingInstitutionId")
	public long AccountHoldingInstitutionId;
	
	@SerializedName("code")
	public String Code;
	
	@SerializedName("name")
	public String Name;
	
	@SerializedName("description")	
	public String Description;
	
	@SerializedName("bookingCategoryId")
	public long BookingCategoryId;
}
