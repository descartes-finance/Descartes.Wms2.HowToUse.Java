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
import Constants.*;

public class ClientAddressChange {
	public static void Execute() throws Exception {
		var httpClient = HttpClient.newHttpClient();
		
		HttpRequest countryRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "countries/code/FR"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.GUEST_TOKEN) // or CLIENT/ADMIN ACCESS TOKEN
			.header("Content-Type", "application/json")
			.build();
		
		var response = httpClient.send(countryRequest, BodyHandlers.ofString());
		CountryOutputModel countryObj = new Gson().fromJson(response.body().toString(), CountryOutputModel.class);
		var newCountryId = countryObj.Id;
		
		// Assuming this is our client ID
		var userId = 226;
		
		StringBuilder newAddressPayload = new StringBuilder("{");
		newAddressPayload.append("'Street': 'Nowhere',");
		newAddressPayload.append("'StreetNr': '1',");
		newAddressPayload.append("'City': 'Elsewhere',");
		newAddressPayload.append("'CountryId': " + newCountryId + ",");
		newAddressPayload.append("'Zip': '6000',"); 
		newAddressPayload.append("}");
		
		HttpRequest theRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "update-address/user-id/" + userId))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN) // or CLIENT ACCESS TOKEN
			.header("Content-Type", "application/json")
			.PUT(HttpRequest.BodyPublishers.ofString(newAddressPayload.toString()))
			.build();
				
		response = httpClient.send(theRequest, BodyHandlers.ofString());
	}
}
