package CodeSnippets;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Models.*;
import Utils.MultiPartBodyPublisher;
import Constants.*;

public class ModifyPlan {
	public static void Execute() throws Exception {
		var httpClient = HttpClient.newHttpClient();
		
		var clientId = 226;
		var portfolioId = 151;
		
		HttpRequest portfolioRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "user-portfolios/portfolio-id/" + portfolioId))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
			.header("Content-Type", "application/json")
			.build();
					
		var response = httpClient.send(portfolioRequest, BodyHandlers.ofString());
		PortfolioOutputModel clientPortfolio = new Gson().fromJson(response.body().toString(), PortfolioOutputModel.class);
		
		// Alternative proposals list
		HttpRequest proposalsRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "proposals/investment-category-id/" + clientPortfolio.InvestmentCategoryId))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
			.header("Content-Type", "application/json")
			.build();
				
		response = httpClient.send(proposalsRequest, BodyHandlers.ofString());		
		Type listType = new TypeToken<ArrayList<ProposalOutputModel>>(){}.getType();
		List<ProposalOutputModel> alternativeProposalObjs = new Gson().fromJson(response.body().toString(), listType);		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Please note:
		//   1.a new PDF document is needed for plan change. For this example we simply use an empty PDF.
		//   2.reason to change proposal is hard-coded while should be chosen by client
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		HttpRequest reasonToChangeRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "reason-to-change-proposed-proposal/investment-category-id/" + clientPortfolio.InvestmentCategoryId + "/active"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(reasonToChangeRequest, BodyHandlers.ofString());
		ReasonToChangeProposalOutputModel reasonToChangeProposalObj = new Gson().fromJson(response.body().toString(), ReasonToChangeProposalOutputModel.class);
		var reasonToChangeProposalId = reasonToChangeProposalObj.Responses.get(1).Id;
				
		StringBuilder orderPortfolioModificationInputModel = new StringBuilder("{");
		orderPortfolioModificationInputModel.append("'PortfolioId': " + portfolioId + ",");
		// Assume this is the new portfolio model selected by client
		orderPortfolioModificationInputModel.append("'ProposalId': " + alternativeProposalObjs.getFirst().Id + ",");
		// Assume this is reason to change proposal selected by client
		orderPortfolioModificationInputModel.append("'ReasonToChangeProposalResponsesIds': [ " + reasonToChangeProposalId + " ]");				
		orderPortfolioModificationInputModel.append("}");
				
		// For simplicity, use an empty PDF contract template
		var inputStream = Files.newInputStream(Paths.get("c:\\Temp\\Contract-DIR-3A.pdf"));
		
		MultiPartBodyPublisher multipartBody = new MultiPartBodyPublisher()
			    .addPart("jsonPayload", orderPortfolioModificationInputModel.toString())
			    .addPart("userContracts", () -> inputStream, "Contract.pdf", "application/pdf");
		
		HttpRequest planChangeRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-portfolio-orders/modification"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "multipart/form-data; boundary=" + multipartBody.getBoundary())
				.POST(multipartBody.build())
				.build();
		
		response = httpClient.send(planChangeRequest, BodyHandlers.ofString());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("pdf-service/fill-pdf-with-data ==> response.statusCode() = " + response.statusCode());
		}			
	}
}
