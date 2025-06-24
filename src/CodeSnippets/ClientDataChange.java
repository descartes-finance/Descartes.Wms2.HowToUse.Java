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

public class ClientDataChange {
	public static void Execute(String name, String surname, String email, long genderId, long languageId, long nationalityId, long pensionSituationId) throws Exception {
		var httpClient = HttpClient.newHttpClient();
		
		// Assuming this is our client ID
		var userId = 226;
		
		StringBuilder clientChangeDataPayload = new StringBuilder("{");
		clientChangeDataPayload.append("'Name': '" + name + "',");
		clientChangeDataPayload.append("'Surname': '" + surname + "',");
		clientChangeDataPayload.append("'PhoneNumberPrefix': '0041',");
		clientChangeDataPayload.append("'PhoneNumberNumber': '767965367',");
		clientChangeDataPayload.append("'BirthDate': '2001-12-25',");
		clientChangeDataPayload.append("'GenderId': " + genderId + ",");
		clientChangeDataPayload.append("'LanguageId': " + languageId + ",");
		clientChangeDataPayload.append("'Nationality1Id': " + nationalityId + ",");
		clientChangeDataPayload.append("'DeliverTaxStatement': true,");
		clientChangeDataPayload.append("'PensionSituationId': " + pensionSituationId); 
		clientChangeDataPayload.append("}");
		
		HttpRequest clientDataChangeRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "users/user-id/" + userId))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN) // or CLIENT ACCESS TOKEN
			.header("Content-Type", "application/json")
			.PUT(HttpRequest.BodyPublishers.ofString(clientChangeDataPayload.toString()))
			.build();
			
		var response = httpClient.send(clientDataChangeRequest, BodyHandlers.ofString());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("Update client data ==> response.statusCode() = " + response.statusCode());
		}
	}
}