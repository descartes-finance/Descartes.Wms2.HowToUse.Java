package Models;

import com.google.gson.annotations.SerializedName;

public class CivilStatusOutputModel {
	@SerializedName("id")
    public long Id;
	
	@SerializedName("name")
    public String Name;
    
	@SerializedName("code")
    public String Code;
}
