package Models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PortfolioValuesOutputModel {
	@SerializedName("portfolioId")
	public long PortfolioId;
	
	@SerializedName("nome")
	public String Name;
	
	@SerializedName("color")
	public String Color;

	@SerializedName("values")
	public List<PortfolioValueOutputModel> Values = new ArrayList<PortfolioValueOutputModel>();
}
