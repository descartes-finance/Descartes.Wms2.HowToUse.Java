package CodeSnippets;
import java.awt.PageAttributes.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Map;

import com.google.gson.Gson;

import Models.*;
import Constants.*;
import Results.ProposalSelectionResult;
import Utils.MultiPartBodyPublisher;

public class PlanCreation {
	public static long Execute(long businessLineId, long investmentCategoryId, long userId, ProposalSelectionResult proposalSelectionResult) throws Exception {
		var httpClient = HttpClient.newHttpClient();		
		
		// Saving the client's risk profile passing the questionnaire response IDs
		StringBuilder registrationPayload = new StringBuilder("{");
		registrationPayload.append("'BusinessLineId': " + businessLineId + ",");
		registrationPayload.append("'UserId': " + userId + ",");
		registrationPayload.append("'Responses': [ { ResponseId:" + proposalSelectionResult.ResponseIds.get(0) + "}, { ResponseId:" + proposalSelectionResult.ResponseIds.get(1) + "}, { ResponseId:" + proposalSelectionResult.ResponseIds.get(2) + "}, { ResponseId:" + proposalSelectionResult.ResponseIds.get(3) + " } ]");				
		registrationPayload.append("}");
		
		HttpRequest createRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-risk-categorizations"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(registrationPayload.toString()))
				.build();
		
		var response = httpClient.send(createRequest, BodyHandlers.ofString());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("user-risk-categorizations ==> response.statusCode() = " + response.statusCode());
		}
		
		// Saving missing client data
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
		
		StringBuilder updatePayload = new StringBuilder("{");
		updatePayload.append("'CivilStatusId': " + civilStatusId + ",");
		updatePayload.append("'CivilStatusDate': '1989-01-01',");
		updatePayload.append("'GenderId': " + genderId + ",");
		updatePayload.append("'LanguageId': " + languageId + ",");
		updatePayload.append("'Nationality1Id': " + nationalityId + ",");
		updatePayload.append("'DeliverTaxStatement': true,");
		updatePayload.append("'TaxLiabilityId': " + taxLiabilityId + ",");
		updatePayload.append("'PensionSituationId': " + pensionId);
		updatePayload.append("}");
		
		MultiPartBodyPublisher multipartBody = new MultiPartBodyPublisher()
			    .addPart("jsonPayload", updatePayload.toString());
		
		HttpRequest updateRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "users/user-id/" + userId))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "multipart/form-data; boundary=" + multipartBody.getBoundary())
				.PUT(multipartBody.build())
				.build();
		
		response = httpClient.send(updateRequest, BodyHandlers.ofString());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());			
			throw new Exception("users/user-id" + userId + " ==> response.statusCode() = " + response.statusCode());
		}
		
		StringBuilder updateAddressPayload = new StringBuilder("{");
		updateAddressPayload.append("'Street': 'Nostrasse',");
		updateAddressPayload.append("'StreetNr': '12',");
		updateAddressPayload.append("'City': 'Zug',");
		updateAddressPayload.append("'CountryId':" + countryId + ",");
		updateAddressPayload.append("'Zip': '6300',");
		updateAddressPayload.append("}");
		
		HttpRequest updateAddressRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "users/update-address/user-id/" + userId))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(updateAddressPayload.toString()))
				.build();
		
		response = httpClient.send(updateAddressRequest, BodyHandlers.ofString());			
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("users/update-address/user-id/" + userId + " ==> response.statusCode() = " + response.statusCode());
		}
		
		// Get client data
		HttpRequest updateclientInfoRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "users/" + userId))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(updateclientInfoRequest, BodyHandlers.ofString());
		ClientOutputModel clientObj = new Gson().fromJson(response.body().toString(), ClientOutputModel.class);
		
		// Collect some (hard coded) clien's fields...
		Map<String, String> placeHolderValues = new HashMap<String, String>();
		placeHolderValues.put("Name", clientObj.Name);
		placeHolderValues.put("Surname", clientObj.Surname);
		placeHolderValues.put("Language", "DE");
		placeHolderValues.put("Gender", "MALE");
		placeHolderValues.put("Street", "Nowhere 0");
		placeHolderValues.put("ZipAndCity", "6000 Elsewhere");
		placeHolderValues.put("Country", "No man's land");
		placeHolderValues.put("Nationality",  "Mars");
		placeHolderValues.put("Birthdate", "01-01-1990");
		placeHolderValues.put("Email", clientObj.Email);
		placeHolderValues.put("Phone", clientObj.PhoneNumber);
		
		// Fill contract with client's data
		var pdfAsByteArray = GetPdf(httpClient, placeHolderValues);	
		//Files.write(Paths.get("C:\\Temp\\ClientContract.pdf"), pdfAsByteArray);
		
		// Submitting the order
		HttpRequest accountHoldingInstitutionRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "account-holding-institutions/code/LIENHARDT"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(accountHoldingInstitutionRequest, BodyHandlers.ofString());
		AccountHoldingInstitutionOutputModel accountHoldingInstitutionObj = new Gson().fromJson(response.body().toString(), AccountHoldingInstitutionOutputModel.class);
		
		StringBuilder orderPayload = new StringBuilder("{");
		orderPayload.append("'AccountHoldingInstitutionCode': '" + accountHoldingInstitutionObj.Code + "',");
		orderPayload.append("'UserId': " + userId + ",");
		orderPayload.append("'InvestmentCategoryId': " + investmentCategoryId + ",");
		orderPayload.append("'ProposalId': " + proposalSelectionResult.ProposalId + ",");
		orderPayload.append("'ReasonToChangeProposalResponsesIds': []");
		orderPayload.append("}");
		
		InputStream contractStream = new ByteArrayInputStream(pdfAsByteArray);
		
		MultiPartBodyPublisher multipartOrderBody = new MultiPartBodyPublisher()
			    .addPart("jsonPayload", orderPayload.toString())
			    .addPart("userContracts", () -> contractStream, "Contract.pdf", "application/pdf");
		
		HttpRequest createPortfolioRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-portfolio-orders/creation"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "multipart/form-data; boundary=" + multipartOrderBody.getBoundary())
				.POST(multipartOrderBody.build())
				.build();
		
		response = httpClient.send(createPortfolioRequest, BodyHandlers.ofString());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("users/update-address/user-id/" + userId + " ==> response.statusCode() = " + response.statusCode());
		}
		
		PortfolioOrderOutputModel orderObj = new Gson().fromJson(response.body().toString(), PortfolioOrderOutputModel.class);
				
		System.out.println("New Portfolio ID = " + orderObj.PortfolioId);
		
		return orderObj.PortfolioId;
	}
	
	private static byte[] GetPdf(HttpClient httpClient, Map<String, String> placeHolderValues) throws Exception {		
		var payload = new PdfDataInputModel();
		payload.PlaceHolderValues = new ArrayList<FormField>();

		for (Map.Entry<String, String> entry : placeHolderValues.entrySet()) {
		    payload.PlaceHolderValues.add(new FormField(entry.getKey(), entry.getValue()));
		}	
		
		var planceHolderJson = new Gson().toJson(payload);
		var inputStream = Files.newInputStream(Paths.get("c:\\Temp\\Contract-DIR-3A.pdf"));
		
		MultiPartBodyPublisher multipartBody = new MultiPartBodyPublisher()
			    .addPart("jsonPayload", planceHolderJson)
			    .addPart("attachments", () -> inputStream, "Contract.pdf", "application/pdf");
		
		// ***************************************************************************************
		// Please note: we are calling a different WEB API service, so it is a different URL!
		// ***************************************************************************************
		HttpRequest fillPdfRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_PDF_URL + "pdf-service/fill-pdf-with-data"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "multipart/form-data; boundary=" + multipartBody.getBoundary())
				.POST(multipartBody.build())
				.build();
		
		HttpResponse<byte[]> response = httpClient.send(fillPdfRequest, BodyHandlers.ofByteArray());
		if (response.statusCode() != 200 && response.statusCode() != 201) {
			System.out.println(response.body());
			throw new Exception("pdf-service/fill-pdf-with-data ==> response.statusCode() = " + response.statusCode());
		}		
		
		return response.body();
	}
}