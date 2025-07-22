package CodeSnippets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.google.gson.Gson;

import Models.*;
import Utils.MultiPartBodyPublisher;
import Constants.*;

public class ClientDataChange {
	public static void Execute(String name, String surname, String email) throws Exception {
		var httpClient = HttpClient.newHttpClient();
		
		// Assuming this is our client ID
		var userId = 1889;
		
		HttpRequest nationalityRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "nationalities/code/CH"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		var response = httpClient.send(nationalityRequest, BodyHandlers.ofString());
		NationalityOutputModel nationalityObj = new Gson().fromJson(response.body().toString(), NationalityOutputModel.class);
		var nationalityId = nationalityObj.Id;
		
		HttpRequest languageRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "languages/code/FR"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(languageRequest, BodyHandlers.ofString());
		LanguageOutputModel languageObj = new Gson().fromJson(response.body().toString(), LanguageOutputModel.class);
		var languageId = languageObj.Id;
		
		HttpRequest pensionRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "pension-situations/code/PENSION-FUND"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(pensionRequest, BodyHandlers.ofString());
		PensionSituationOutputModel pensionObj = new Gson().fromJson(response.body().toString(), PensionSituationOutputModel.class);
		var pensionSituationId = pensionObj.Id;
		
		StringBuilder clientChangeDataPayload = new StringBuilder("{ ");
		clientChangeDataPayload.append("'Name': '" + name + "',");
		clientChangeDataPayload.append("'Surname': '" + surname + "',");
		clientChangeDataPayload.append("'Email': '" + email + "',");
		clientChangeDataPayload.append("'PhoneNumberPrefix': '0041',");
		clientChangeDataPayload.append("'PhoneNumberNumber': '767965367',");
		clientChangeDataPayload.append("'BirthDate': '2001-12-25',");
		clientChangeDataPayload.append("'LanguageId': " + languageId + ",");
		clientChangeDataPayload.append("'Nationality1Id': " + nationalityId + ",");
		clientChangeDataPayload.append("'DeliverTaxStatement': true,");
		clientChangeDataPayload.append("'PensionSituationId': " + pensionSituationId); 
		clientChangeDataPayload.append(" }");
		
		MultiPartBodyPublisher multipartClientChangeOrderBody = new MultiPartBodyPublisher()
			    .addPart("jsonPayload", clientChangeDataPayload.toString());
			    //.addPart("userIdentities", () -> null, null, "application/pdf");  ==> In case no client ID document is needed, do not use this.
		
		HttpRequest clientDataChangeRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "users/user-id/" + userId))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "multipart/form-data; boundary=" + multipartClientChangeOrderBody.getBoundary())
				.PUT(multipartClientChangeOrderBody.build())
				.build();
					
		response = httpClient.send(clientDataChangeRequest, BodyHandlers.ofString());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("Update client data ==> response.statusCode() = " + response.statusCode());
		}
	}
}