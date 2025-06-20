package Models;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PortfolioValueOutputModel {
	@SerializedName("date")
	public Date Date;
	
	@SerializedName("performanceSinceInception")
	public BigDecimal PerformanceSinceInception;
	
	@SerializedName("performanceYearToDate")
	public BigDecimal PerformanceYearToDate;
	
	@SerializedName("profitOrLost")
	public BigDecimal ProfitOrLost;
	
	@SerializedName("portfolioMarketValue")
	public BigDecimal PortfolioMarketValue;
	
	@SerializedName("cashAmount")
	public BigDecimal CashAmount;
	
	@SerializedName("portfolioTotalAssets")
	public BigDecimal PortfolioTotalAssets;
}