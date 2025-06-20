package CodeSnippets;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Models.*;
import Constants.*;

public class InvestmentCategory {
	public static long GetId() throws Exception {
		var httpClient = HttpClient.newHttpClient();
		
		HttpRequest investmentCategoryRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "investment-categories/code/3A"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		var response = httpClient.send(investmentCategoryRequest, BodyHandlers.ofString());
		InvestmentCategoryOutputModel investmentCategoryObj = new Gson().fromJson(response.body().toString(), InvestmentCategoryOutputModel.class);
		
		return investmentCategoryObj.Id;
	}
}