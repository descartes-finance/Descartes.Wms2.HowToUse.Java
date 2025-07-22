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
			
			// NOTE:
			// 1. The system can work manage different type of Business Line (INVEST (3B product) or VORSORGE (3A/FZ product). Neon will have only VORSORGE business line
			// 2. A business line has different products. Neon will have only 3A business line. In the future is also possible to have FZ.
			// 3. The system can manage multiple 3A foundations, but NEON will work only with LIENHARDT. So, you can hard-code it.
			// 
			// Please remember that the CODE (string) do not change between TEST and PROD. Id instead can change.
			
			// Getting the business line (in your case is 'VORSORGE')
			//var businessLineId = BusinessLine.GetId();
			
			// Getting the investment category based on the business line (in your case is hard-coded to 3A as you, at the moment, do not sell FZ product) 
			//var investmentCategoryId = InvestmentCategory.GetId();
			
			// Create all mandatory client data
			//var userId = AdministrativeClientRegistration.Execute();
			
			// Select a investment proposal: get questionnaire response, simulate some replies from the client, save the response to the system
			// Saving the response for a client, automatically create a user risk profile.
			//var proposalSelectionResult = ProposalSelection.Execute(businessLineId, investmentCategoryId);
			
			// Getting the reasons to change proposal. This list of IDs must be selected by the client, if he wants to change the proposed investment.
			// In case the proposed investment matched his risk profile, this ID's list in not needed. 
			//var reasonToChangeProposalId = ReasonToChangeProposal.Execute(businessLineId, investmentCategoryId);			
			
			// Prepare the contract PDF, submit request to open a 3A plan(LIENHARDT Foundation) 
			//long portfolioId = PlanCreation.Execute(businessLineId, investmentCategoryId, userId, proposalSelectionResult);
			
			//ClientPortfolioPosition.Execute();
			//ModifyPlan.Execute();
			
			//ClientPortfolioPerformance.Execute();
			
			// ClientAddressChange.Execute();
			ClientDataChange.Execute("Piri", "Picchio", "piri-picchio@gmail.com");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Done!");
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