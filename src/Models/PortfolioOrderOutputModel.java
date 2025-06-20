package Models;

import com.google.gson.annotations.SerializedName;

public class PortfolioOrderOutputModel {
	@SerializedName("portfolioId")
	public long PortfolioId;
	
	@SerializedName("id")
	public long Id;
	
	@SerializedName("externalId")
	public String ExternalId;
	
	@SerializedName("proposalId")
	public long ProposalId;
	
	@SerializedName("orderStatusId")
	public long OrderStatusId;

	@SerializedName("orderTypeId")
	public long OrderTypeId;
	
	@SerializedName("submissionErrorMessage")
	public String SubmissionErrorMessage;
	
	@SerializedName("reasonForRejectionCode")
	public String ReasonForRejectionCode;
	
	@SerializedName("reasonForRejection")
	public String ReasonForRejection;
	
	@SerializedName("correlationId")
	public String CorrelationId;
}