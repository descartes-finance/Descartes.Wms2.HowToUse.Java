package Models;

import com.google.gson.annotations.SerializedName;

public class PensionSituationOutputModel {
	@SerializedName("id")
    public long Id;
	
	@SerializedName("description")
    public String Description;
    
	@SerializedName("code")
    public String Code;
}
