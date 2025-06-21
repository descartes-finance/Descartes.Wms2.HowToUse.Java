package CodeSnippets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.google.gson.Gson;

import Constants.*;
import Models.*;

public class BusinessLine {
	public static long GetId() throws Exception {
		var httpClient = HttpClient.newHttpClient();
		
		HttpRequest businessLineRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "business-lines/code/VORSORGE"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
			.header("Content-Type", "application/json")
			.build();
		
		var response = httpClient.send(businessLineRequest, BodyHandlers.ofString());
		BusinessLineOutputModel businessLineOutputModelObj = new Gson().fromJson(response.body().toString(), BusinessLineOutputModel.class);
		
		System.out.println("Business Line Id = " + businessLineOutputModelObj.Id);
		
		return businessLineOutputModelObj.Id;
	}
}