package Models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class AccessTokenOutputModel {
	@SerializedName("accountNumber")
    public String AccountNumber;
	
	@SerializedName("accessToken")
    public String AccessToken;
	
	@SerializedName("refreshToken")
    public String RefreshToken;
	
	@SerializedName("validUntil")
    public Date ValidUntil;
}
