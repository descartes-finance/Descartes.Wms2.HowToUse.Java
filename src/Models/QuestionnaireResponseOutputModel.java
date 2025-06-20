package Models;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class QuestionnaireResponseOutputModel {
	@SerializedName("id")
    public int Id;
	
	@SerializedName("description")
    public String Description;
	
	@SerializedName("score")
    public BigDecimal Score;
	
	@SerializedName("order")
    public int Order;
	
	@SerializedName("code")
    public String Code;
	
	@SerializedName("minValue")
    public BigDecimal MinValue;
	
	@SerializedName("maxValue")
    public BigDecimal MaxValue;
}
