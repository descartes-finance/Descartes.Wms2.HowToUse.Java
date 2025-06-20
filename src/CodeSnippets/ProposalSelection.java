package CodeSnippets;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Models.*;
import Constants.*;
import Results.ProposalSelectionResult;

public class ProposalSelection {
	public static ProposalSelectionResult Execute(long businessLineId, long investmentCategoryId) throws Exception {
		var httpClient = HttpClient.newHttpClient();		
	
		HttpRequest questionnaireRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "questionnaires/business-line-id/" + businessLineId + "/active"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		var response = httpClient.send(questionnaireRequest, BodyHandlers.ofString());		
		//System.out.println(response.body());		
		QuestionnaireOutputModel activeQuestionnaire = new Gson().fromJson(response.body().toString(), QuestionnaireOutputModel.class);

		var response1 = activeQuestionnaire.Sections.getFirst().Questions.get(0).Responses.stream().filter(r -> "VORSORGE0004-QUESTION0001-RESPONSE0001".equals(r.Code)).findFirst().orElse(null);
		var response2 = activeQuestionnaire.Sections.getFirst().Questions.get(1).Responses.stream().filter(r -> "VORSORGE0004-QUESTION0002-RESPONSE0002".equals(r.Code)).findFirst().orElse(null);
		var response3 = activeQuestionnaire.Sections.getFirst().Questions.get(2).Responses.stream().filter(r -> "VORSORGE0004-QUESTION0003-RESPONSE0003".equals(r.Code)).findFirst().orElse(null);
		var response4 = activeQuestionnaire.Sections.getFirst().Questions.get(3).Responses.stream().filter(r -> "VORSORGE0004-QUESTION0004-RESPONSE0003".equals(r.Code)).findFirst().orElse(null);
		var responsesCsv = response1.Id + "," + response2.Id + "," + response3.Id + "," + response4.Id;
		
		HttpRequest riskRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "risk-categorizations/business-line-id/" + businessLineId + "/calculate-risk?csv-response-ids=" + responsesCsv))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(riskRequest, BodyHandlers.ofString());
		RiskCategorizationOutputModel riskCategorizationObj = new Gson().fromJson(response.body().toString(), RiskCategorizationOutputModel.class);				
					
		HttpRequest proposalRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "proposals/investment-category-id/" + investmentCategoryId + "/risk-id/" + riskCategorizationObj.Id))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(proposalRequest, BodyHandlers.ofString());
		
		Type listType = new TypeToken<ArrayList<ProposalOutputModel>>(){}.getType();
		List<ProposalOutputModel> proposalsListObj = new Gson().fromJson(response.body().toString(), listType);
		
		var proposalId = proposalsListObj.getFirst().Id;
		System.out.println("Suggested proposal Id = " + proposalId);
		
		var responseIds = new ArrayList<Integer>();
		responseIds.add(response1.Id);
		responseIds.add(response2.Id);
		responseIds.add(response3.Id);
		responseIds.add(response4.Id);
		
		return new ProposalSelectionResult(proposalId, responseIds);
	}
}
