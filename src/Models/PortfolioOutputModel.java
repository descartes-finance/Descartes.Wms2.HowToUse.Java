package Models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PortfolioOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("externalId")
	public String ExternalId;
	
	@SerializedName("accountHoldingInstitutionId")
	public long AccountHoldingInstitutionId;
	
	@SerializedName("investmentCategoryId")
	public long InvestmentCategoryId;
	
	@SerializedName("userId")
	public long UserId;
	
	@SerializedName("proposalId")
	public long ProposalId;
	
	@SerializedName("depositNumber")
	public String DepositNumber;
	
	@SerializedName("name")
	public String Name;
	
	@SerializedName("systemName")
	public String SystemName;
	
	@SerializedName("isTrading")
	public Boolean IsTrading;
	
	@SerializedName("hasPendingOrder")
	public Boolean HasPendingOrder;
	
	@SerializedName("creationDate")
	public Date CreationDate;
	
	@SerializedName("acceptanceDate")
	public Date AcceptanceDate;
	
	@SerializedName("closedDate")
	public Date ClosedDate;

	@SerializedName("investedAmount")
	public BigDecimal InvestedAmount;
	
	@SerializedName("total")
	public BigDecimal Total;
	
	@SerializedName("marketValue")
	public BigDecimal MarketValue;
	
	@SerializedName("profitOrLost")
	public BigDecimal ProfitOrLost;
	
	@SerializedName("performanceSinceInception")
	public BigDecimal PerformanceSinceInception;
	
	@SerializedName("performanceYearToDate")
	public BigDecimal PerformanceYearToDate;
	
	@SerializedName("portfolioValueDate")
	public Date PortfolioValueDate;

	@SerializedName("positions")
	public List<PortfolioPositionOutputModel> Positions = new ArrayList<PortfolioPositionOutputModel>();
}
