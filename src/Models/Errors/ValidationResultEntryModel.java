package Models.Errors;

import com.google.gson.annotations.SerializedName;

public class ValidationResultEntryModel {
	@SerializedName("validationErrorCode")
	public String ValidationErrorCode = "INVALID-FIELD";

	@SerializedName("validationErrorMessage")
	public String ValidationErrorMessage; 
}