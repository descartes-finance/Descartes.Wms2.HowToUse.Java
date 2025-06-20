package Models;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class ReasonToChangeProposalOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("code")
	public String Code;
	
	@SerializedName("name")
	public String Name;

	@SerializedName("responses")
	public List<ReasonToChangeProposalResponseOutputModel> Responses;
}
