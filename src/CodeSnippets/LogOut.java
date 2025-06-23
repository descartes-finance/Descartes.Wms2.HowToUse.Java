package CodeSnippets;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import Constants.Settings;

public class LogOut {
	public static void Execute() throws IOException, InterruptedException {
		var httpClient = HttpClient.newHttpClient();
		
		HttpRequest theRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "users/authentication/logout"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer XXXXXXXXXXXXXXX-client-access-token-XXXXXXXXXXXXXXX")
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.noBody())
			.build();
			
		var response = httpClient.send(theRequest, BodyHandlers.ofString());
	}
}