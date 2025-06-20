package Models;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PortfolioPositionOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("allocation")
	public BigDecimal Allocation;
	
	@SerializedName("quantity")
	public BigDecimal Quantity;
	
	@SerializedName("price")
	public BigDecimal Price;
	
	@SerializedName("priceInChf")
	public BigDecimal PriceInChf;
	
	@SerializedName("performance")
	public BigDecimal Performance;
	
	@SerializedName("date")
	public Date Date;
	
	@SerializedName("currencyId")
	public long CurrencyId;
	
	@SerializedName("securityId")
	public long SecurityId;
}
