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
import Models.Errors.ValidationErrorModel; 

public class ErrorManagement {
	public static void Execute() {
		var httpClient = HttpClient.newHttpClient();		
		
		try {
			var payload = "{ 'CustomerID': null, 'CustomerName': 'A', 'CustomerSurname': 'Dea#§' }";
			
			HttpRequest request = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "users"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(payload))
					.build();
			
			var response = httpClient.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 400) {
				System.out.println(response.body());
				
				ValidationErrorModel errorsObj = new Gson().fromJson(response.body().toString(), ValidationErrorModel.class);
				var howManyErrors = errorsObj.validationErrors.size();
				System.out.println(howManyErrors);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}