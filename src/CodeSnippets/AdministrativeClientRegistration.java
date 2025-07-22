package CodeSnippets;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.UUID;

import com.google.gson.Gson;

import Constants.*;
import Models.*; 

public class AdministrativeClientRegistration {
	public static long Execute() throws Exception {
		var httpClient = HttpClient.newHttpClient();		

		var clientEmail = UUID.randomUUID().toString() + "@mail.com";
		var clientName = UUID.randomUUID().toString().replace("-", "").substring(4, 6);
		var clientSurname = UUID.randomUUID().toString().replace("-", "").substring(1, 7);
		
		HttpRequest languageRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "languages/code/FR"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		var response = httpClient.send(languageRequest, BodyHandlers.ofString());
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
		
		HttpRequest nationalityRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "nationalities/code/CH"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(nationalityRequest, BodyHandlers.ofString());
		NationalityOutputModel nationalityObj = new Gson().fromJson(response.body().toString(), NationalityOutputModel.class);
		var nationalityId = nationalityObj.Id;
					
		HttpRequest countryRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "countries/code/CH"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(countryRequest, BodyHandlers.ofString());
		CountryOutputModel countryObj = new Gson().fromJson(response.body().toString(), CountryOutputModel.class);
		var countryId = countryObj.Id;
		
		HttpRequest pensionRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "pension-situations/code/PENSION-FUND"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(pensionRequest, BodyHandlers.ofString());
		PensionSituationOutputModel pensionObj = new Gson().fromJson(response.body().toString(), PensionSituationOutputModel.class);
		var pensionId = pensionObj.Id;
		
		HttpRequest taxLiabilityRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "tax-liabilities/code/CH"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(taxLiabilityRequest, BodyHandlers.ofString());
		TaxLiabilityOutputModel taxLiabilityObj = new Gson().fromJson(response.body().toString(), TaxLiabilityOutputModel.class);
		var taxLiabilityId = taxLiabilityObj.Id;	
		
		HttpRequest civilStatusRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "civil-statuses/code/SINGLE"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(civilStatusRequest, BodyHandlers.ofString());
		CivilStatusOutputModel civilStatusObj = new Gson().fromJson(response.body().toString(), CivilStatusOutputModel.class);
		var civilStatusId = civilStatusObj.Id;			
		
		HttpRequest genderRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "genders/code/MALE"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(genderRequest, BodyHandlers.ofString());
		GenderOutputModel genderObj = new Gson().fromJson(response.body().toString(), GenderOutputModel.class);
		var genderId = genderObj.Id;
		
		// Preparing pay-load
		StringBuilder registrationPayload = new StringBuilder("{");
		registrationPayload.append("'ManagementPersonalDataConsent': '2025-01-01',");
		registrationPayload.append("'CivilStatusId': " + civilStatusId + ",");
		registrationPayload.append("'Name': '" + clientName + "',"); 
		registrationPayload.append("'Surname': '" + clientSurname + "',"); 
		registrationPayload.append("'Email': '" + clientEmail + "',"); 			
		registrationPayload.append("'PhoneNumberPrefix': '0041',"); 
		registrationPayload.append("'PhoneNumberNumber': '767965454',"); 
		registrationPayload.append("'PensionSituationId': '" + pensionId + "',");
		registrationPayload.append("'LanguageId': " + languageId + ","); 
		registrationPayload.append("'LegalAcceptanceId':" + legalAcceptanceId + ","); 
		registrationPayload.append("'Address': {");
		registrationPayload.append("    'City': 'Baar',"); 
		registrationPayload.append("    'Zip': 6340,");
		registrationPayload.append("    'Street': 'Nowhere',"); 
		registrationPayload.append("    'StreetNr': '1',"); 
		registrationPayload.append("    'CountryId': " + countryId + ","); 
		registrationPayload.append("},");
		registrationPayload.append("'GenderId': " + genderId + ","); 			
		registrationPayload.append("'BirthDate': '2006-01-01',"); 
		registrationPayload.append("'Nationality1Id': " + nationalityId + ","); 
		registrationPayload.append("'TaxLiabilityId': " + taxLiabilityId);
		registrationPayload.append("}");
		
		HttpRequest createRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "users"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(registrationPayload.toString()))
				.build();
		
		response = httpClient.send(createRequest, BodyHandlers.ofString());
		ClientOutputModel clientObj = new Gson().fromJson(response.body().toString(), ClientOutputModel.class);
		
		System.out.println("Created user with ID = " + clientObj.Id);
		
		return clientObj.Id;
	}
}