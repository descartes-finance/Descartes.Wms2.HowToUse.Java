package CodeSnippets;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.google.gson.Gson;

import Models.*;
import Constants.*;

public class ReasonToChangeProposal {
	public static long Execute(long businessLineId, long investmentCategoryId) throws Exception {
		var httpClient = HttpClient.newHttpClient();	
	
		HttpRequest reasonToChangeRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "reason-to-change-proposed-proposal/investment-category-id/" + investmentCategoryId + "/active"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		var response = httpClient.send(reasonToChangeRequest, BodyHandlers.ofString());
		ReasonToChangeProposalOutputModel reasonToChangeProposalObj = new Gson().fromJson(response.body().toString(), ReasonToChangeProposalOutputModel.class);
		
		//System.out.println("reasonToChangeProposalObj.Id = " + reasonToChangeProposalObj.Id);		
		
		return reasonToChangeProposalObj.Responses.getFirst().Id;
	}
}
