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

import java.net.http.HttpClient;

public class RefreshAuthenticationToken {
	public static void Execute(long userId, String refreshToken, String accessToken) throws IOException, InterruptedException {
		var httpClient = HttpClient.newHttpClient();
		
		StringBuilder thePayload = new StringBuilder("{");
		thePayload.append("'UserId': " + userId + ",");
		thePayload.append("'RefreshToken': '" + refreshToken + "',");
		thePayload.append("'AccessToken': '" + accessToken); 
		thePayload.append("}");
		
		HttpRequest theRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "users/authentication/access-token/refresh"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(thePayload.toString()))
			.build();
		
		var response = httpClient.send(theRequest, BodyHandlers.ofString());
		AccessTokenOutputModel accessTokenOutputModel = new Gson().fromJson(response.body().toString(), AccessTokenOutputModel.class);
		
	}
}
