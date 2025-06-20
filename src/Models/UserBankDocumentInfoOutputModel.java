package Models;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class UserBankDocumentInfoOutputModel {
	@SerializedName("documentTypeId")
	public long DocumentTypeId;
	
	@SerializedName("dTypeCode")
	public String DocumentTypeCode;
	
	@SerializedName("description")
	public String Description;
	
	@SerializedName("reference")
	public String Reference;
	
	@SerializedName("created")
	public Date Created;
	
	@SerializedName("viewed")
	public Date Viewed;
}
