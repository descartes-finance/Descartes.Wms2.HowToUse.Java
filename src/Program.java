import java.io.IOException;

import CodeSnippets.*;

public class Program {
			
	public static void main(String[] args) {
		
		var version = getVersion();
		
		System.out.println("*********************** How to Use *********************** ");
		System.out.println("WEB APIs: https://wms2-app.descartes.swiss/");
		System.out.println("Client Java (version = " + version + ")");
		System.out.println("********************************************************** ");
		
		//ErrorManagement.Execute();
		
		try {
			//ClientSelfRegistration.Execute();
			
			//var businessLineId = BusinessLine.GetId();
			//var investmentCategoryId = InvestmentCategory.GetId();
			//var userId = AdministrativeClientRegistration.Execute();		
			//var proposalSelectionResult = ProposalSelection.Execute(businessLineId, investmentCategoryId);
			//var reasonToChangeProposalId = ReasonToChangeProposal.Execute(businessLineId, investmentCategoryId);			
			//long portfolioId = PlanCreation.Execute(businessLineId, investmentCategoryId, userId, proposalSelectionResult);
			
			//ClientPortfolioPosition.Execute();
			//ModifyPlan.Execute();
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getVersion() {
	    String version = System.getProperty("java.version");
	    if(version.startsWith("1.")) {
	        version = version.substring(2, 3);
	    } else {
	        int dot = version.indexOf(".");
	        if(dot != -1) { version = version.substring(0, dot); }
	    } return Integer.parseInt(version);
	}
}