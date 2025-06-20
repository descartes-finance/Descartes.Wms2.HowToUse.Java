package Models.Errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class ValidationErrorModel {
	@SerializedName("errorCode")
	public String ErrorCode;
	
	@SerializedName("message")
	public String Message;
	
	@SerializedName("validationErrors")
	public Map<String, List<ValidationResultEntryModel>> validationErrors = new HashMap<>();
}