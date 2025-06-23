package Models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class AuthenticationSecondFactorOutputModel {
	@SerializedName("cashAccountId")
	public long Id;
	
	@SerializedName("accountNumber")
	public String AccountNumber;
		
	@SerializedName("accessToken")
	public String AccessToken;
	
	@SerializedName("validUntil")
	public Date ValidUntil;
	
	@SerializedName("refreshToken")
	public String RefreshToken;

	@SerializedName("remainingAttempts")
	public int RemainingAttempts;
}