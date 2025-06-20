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

public class ClientSelfRegistration {
	public static void Execute() {
		var httpClient = HttpClient.newHttpClient();		
		
		try {				
			//System.out.println(response.statusCode());
			//System.out.println(response.body());
									
			var clientEmail = UUID.randomUUID().toString() + "@xmail.com";
			var clientName = UUID.randomUUID().toString().replace("-", "").substring(4, 6);
			var clientSurname = UUID.randomUUID().toString().replace("-", "").substring(1, 7);
			
			// var birthDataLocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse("2018-03-09"))
			
			// First-factor registration
			HttpRequest genderRequest = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "genders/code/MALE"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
					.header("Content-Type", "application/json")
					.build();
			
			HttpResponse<String> response = httpClient.send(genderRequest, BodyHandlers.ofString());
			GenderOutputModel genderObj = new Gson().fromJson(response.body().toString(), GenderOutputModel.class);
			var genderId = genderObj.Id;
			
			var firstFactorPayload = "{'GenderId':" + genderId + ",'Name':'" + clientName + "','Surname':'" + clientSurname + "', 'EmailAddress': '" + clientEmail + "'}";
			
			HttpRequest firstFactorRequest = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "users/registration/first-factor"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(firstFactorPayload))
					.build();
			
			response = httpClient.send(firstFactorRequest, BodyHandlers.ofString());
			RegistrationFirstFactorOutputModel fistFactorRegistrationResult = new Gson().fromJson(response.body().toString(), RegistrationFirstFactorOutputModel.class);
			
			// Second-factor registration	
			var secondFactorPayload = "{'PhoneNumberPrefix': '0041','PhoneNumberNumber':'767965454'}";
			
			HttpRequest secondFactorRequest = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "users/registration/second-factor"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(secondFactorPayload))
					.build();
			
			response = httpClient.send(secondFactorRequest, BodyHandlers.ofString());
			RegistrationSecondFactorOutputModel registrationSecondFactorOutputModel = new Gson().fromJson(response.body().toString(), RegistrationSecondFactorOutputModel.class);
			
			// Third-factor registration
			HttpRequest languageRequest = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "languages/code/DE"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
					.header("Content-Type", "application/json")
					.build();
			
			response = httpClient.send(languageRequest, BodyHandlers.ofString());
			LanguageOutputModel languageObj = new Gson().fromJson(response.body().toString(), LanguageOutputModel.class);
			var languageId = languageObj.Id;
			
			HttpRequest legalAcceptanceRequest = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "legal-acceptances/active"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
					.header("Content-Type", "application/json")
					.build();
			
			response = httpClient.send(legalAcceptanceRequest, BodyHandlers.ofString());
			LegalAcceptanceOutputModel legalAcceptanceObj = new Gson().fromJson(response.body().toString(), LegalAcceptanceOutputModel.class);
			var legalAcceptanceId = legalAcceptanceObj.Id;
			
			StringBuilder thirdFactorPayload = new StringBuilder("{");
			thirdFactorPayload.append("'ManagementPersonalDataConsent': '2025-01-01',");
			thirdFactorPayload.append("'SmsToken': '" + registrationSecondFactorOutputModel.Token + "',");
			thirdFactorPayload.append("'EmailToken': '" + fistFactorRegistrationResult.Token + "',"); 
			thirdFactorPayload.append("'GenderId': " + genderId + ","); 
			thirdFactorPayload.append("'Name': '" + clientName + "',"); 
			thirdFactorPayload.append("'Surname': '" + clientSurname + "',"); 
			thirdFactorPayload.append("'EmailAddress': '" + clientEmail + "',"); 
			thirdFactorPayload.append("'PhoneNumberPrefix': '0041',"); 
			thirdFactorPayload.append("'PhoneNumberNumber': '767965454',"); 
			thirdFactorPayload.append("'Password': 'Ast@LaVist@!',"); 
			thirdFactorPayload.append("'LanguageId': " + languageId + ","); 
			thirdFactorPayload.append("'LegalAcceptanceId': " + legalAcceptanceId); 
			thirdFactorPayload.append("}");
			
			HttpRequest thirdFactorRequest = HttpRequest.newBuilder()
					.timeout(Duration.ofMinutes(1))
					.uri(URI.create(Settings.BASE_WMS_URL + "users/registration/third-factor"))
					.header("Accept-Language", "de-DE")
					.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(thirdFactorPayload.toString()))
					.build();
			
			response = httpClient.send(thirdFactorRequest, BodyHandlers.ofString());
			RegistrationThirdFactorOutputModel registrationThirdFactorOutputModelObj = new Gson().fromJson(response.body().toString(), RegistrationThirdFactorOutputModel.class);
			
			System.out.println("User ID = " + registrationThirdFactorOutputModelObj.UserId);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}