package CodeSnippets;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;

import Constants.*;
import Models.*; 

public class TwoFactorAuthentication {
	public static void Execute(String clientPassword) throws IOException, InterruptedException {
		var httpClient = HttpClient.newHttpClient();
		
		// First factor authentication
		StringBuilder firstFactorPayload = new StringBuilder("{");
		firstFactorPayload.append("'AccountNumber': 'christian.delbianco@gmail.com");
		firstFactorPayload.append("'Password': '" + clientPassword + "'"); 
		firstFactorPayload.append("}");
		
		HttpRequest firstFactorRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "users/authentication/first-factor"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(firstFactorPayload.toString()))
			.build();
		
		// ==> User get SMS or EMAIL token 
		
		// Second factor authentication
		StringBuilder secondFactorPayload = new StringBuilder("{");
		secondFactorPayload.append("'AccountNumber': 'christian.delbianco@gmail.com");
		secondFactorPayload.append("'SmsToken': '29894'"); // Assume this is the code received by SMS
		secondFactorPayload.append("}");
		
		HttpRequest secondFactorRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "users/authentication/second-factor"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(secondFactorPayload.toString()))
			.build();
		
		var response = httpClient.send(secondFactorRequest, BodyHandlers.ofString());
		AuthenticationSecondFactorOutputModel authenticationResult = new Gson().fromJson(response.body().toString(), AuthenticationSecondFactorOutputModel.class);
	
		// Now, set the CLIENT token for the next requests
		var clientAuthorizationToken = authenticationResult.AccessToken;
	}
}
