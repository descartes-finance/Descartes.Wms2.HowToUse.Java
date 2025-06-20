package Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuestionnaireQuestionOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("description")
	public String Description;
	
	@SerializedName("order")
	public int Order;
	
	@SerializedName("code")
	public String Code;

	@SerializedName("responses")
	public List<QuestionnaireResponseOutputModel> Responses;
}
