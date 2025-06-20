package Models;

import com.google.gson.annotations.SerializedName;

public class RiskCategorizationOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("code")
	public String Code;
	
	@SerializedName("name")
	public String Name;
	
	@SerializedName("description")
	public String Description;
	
	@SerializedName("riskyAssets")
	public int RiskyAssets;
	
	@SerializedName("lessRiskyAssets")
	public int LessRiskyAssets;
}
