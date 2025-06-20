package Models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QuestionnaireOutputModel {
	@SerializedName("id")
    public long Id;
	   
    @SerializedName("code")
    public String Code;
    
    @SerializedName("description")
    public String Description;   

    @SerializedName("sections")
    public List<QuestionnaireSectionOutputModel> Sections;
}
