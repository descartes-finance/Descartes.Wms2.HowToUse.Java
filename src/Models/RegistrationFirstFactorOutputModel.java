package Models;
import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class RegistrationFirstFactorOutputModel {
	@SerializedName("accountNumber")
	public String AccountNumber;
	
	@SerializedName("validUntil")
	public Date ValidUntil;
	
	@SerializedName("token")
	public String Token;
}