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

public class ClientPortfolioPosition {
	public static void Execute() throws Exception {
		var httpClient = HttpClient.newHttpClient();	
		
		// Portfolios list
		
		// Assuming this is our client ID
		var clientId = 226;
		  
		HttpRequest portfoliosRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "user-portfolios/user-id/" + clientId))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
			.header("Content-Type", "application/json")
			.build();
			
		var response = httpClient.send(portfoliosRequest, BodyHandlers.ofString());		
		Type listType = new TypeToken<ArrayList<PortfolioOutputModel>>(){}.getType();
		List<PortfolioOutputModel> portfolioObjs = new Gson().fromJson(response.body().toString(), listType);			
		System.out.println("portfolioObj.Id = " + portfolioObjs.getFirst().Id);
			
		// Portfolio by Id
		
		var portfolioId = 151;
		
		HttpRequest portfolioRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "user-portfolios/portfolio-id/" + portfolioId + "?portfolio-view-as=Snapshot"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
			.header("Content-Type", "application/json")
			.build();
				
		response = httpClient.send(portfolioRequest, BodyHandlers.ofString());
		PortfolioOutputModel portfolioObj = new Gson().fromJson(response.body().toString(), PortfolioOutputModel.class);
		
		// Portfolio values
		
		HttpRequest portfolioPerformanceRequest = HttpRequest.newBuilder()
			.timeout(Duration.ofMinutes(1))
			.uri(URI.create(Settings.BASE_WMS_URL + "user-portfolios/portfolio-id/" + portfolioObj.Id + "/performances"))
			.header("Accept-Language", "de-DE")
			.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
			.header("Content-Type", "application/json")
			.build();
		
		response = httpClient.send(portfolioPerformanceRequest, BodyHandlers.ofString());
		PortfolioValuesOutputModel portfolioPerformanceObj = new Gson().fromJson(response.body().toString(), PortfolioValuesOutputModel.class);
		System.out.println("performances count = " + portfolioPerformanceObj.Values.size());
		
		// Portfolio cash amount
		
		HttpRequest bankAccountTypesRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "cash-account-types"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.GUEST_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		// Get bank account types: Cash account/Custody account
		response = httpClient.send(bankAccountTypesRequest, BodyHandlers.ofString());
		Type listBankAccountTypes = new TypeToken<ArrayList<BankAccountTypeOutputModel>>(){}.getType();
		List<BankAccountTypeOutputModel> bankAccountTypes = new Gson().fromJson(response.body().toString(), listBankAccountTypes);
		var bankAccountType = bankAccountTypes.stream().filter(x -> "CASH-ACCOUNT".equals( x.Code)).findFirst().orElse(null);
		
		// Get portfolio's bank accounts
		HttpRequest portfolioBankAccountRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-cash-accounts/portfolio-id/" + portfolioId))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(portfolioBankAccountRequest, BodyHandlers.ofString());
		Type cashAccountType = new TypeToken<ArrayList<CashAccountOutputModel>>(){}.getType();
		List<CashAccountOutputModel> cashAccountObjs = new Gson().fromJson(response.body().toString(), cashAccountType);		
		CashAccountOutputModel cashAccountObj = cashAccountObjs.stream().filter(x -> x.AccountTypeId == bankAccountType.Id).findFirst().get();
		
		// CashAccountMovementOutputModel
		HttpRequest cashAccountMovementsRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-cash-accounts/cash-account-id/" + cashAccountObj.Id + "/movements"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(cashAccountMovementsRequest, BodyHandlers.ofString());
		Type cashAccountMovementType = new TypeToken<ArrayList<CashAccountMovementOutputModel>>(){}.getType();
		List<CashAccountMovementOutputModel> cashAccountMovementObjs = new Gson().fromJson(response.body().toString(), cashAccountMovementType);	
		System.out.println("cashAccountMovementObjs.size() = " + cashAccountMovementObjs.size());
		
		HttpRequest bookingCodeRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "booking-codes"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(bookingCodeRequest, BodyHandlers.ofString());
		Type bookingCodeTypes = new TypeToken<ArrayList<BookingCodeOutputModel>>(){}.getType();
		List<BookingCodeOutputModel> bookingCodeTypeObjs = new Gson().fromJson(response.body().toString(), bookingCodeTypes);
		System.out.println("bookingCodeTypeObjs.size() = " + bookingCodeTypeObjs.size());
		
		cashAccountMovementObjs.forEach(bankAccountMovement -> {
			BookingCodeOutputModel bookingCodeTypeObj = bookingCodeTypeObjs.stream().filter(x -> x.Id == bankAccountMovement.BookingCodeId).findFirst().orElse(null);
			
			var bookingCode = bookingCodeTypeObj.Code;
			System.out.println("bookingCode = " + bookingCode);
			
			var bookingCodeDescription = bookingCodeTypeObj.Description;
			System.out.println("bookingCodeDescription = " + bookingCodeDescription);
		});
		
		// User bank documents
		HttpRequest userDocumentsMetadataRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-bank-documents/user-id/" + clientId + "/list?starting-from=20000101"))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		response = httpClient.send(userDocumentsMetadataRequest, BodyHandlers.ofString());
		Type userBankDocumentInfoType = new TypeToken<ArrayList<UserBankDocumentInfoOutputModel>>(){}.getType();
		List<UserBankDocumentInfoOutputModel> userDocumentsMetadataObjs = new Gson().fromJson(response.body().toString(), userBankDocumentInfoType);	
		System.out.println("userDocumentsMetadataObjs.size() = " + userDocumentsMetadataObjs.size());
				
		// Assuming this is the document reference selected by the user to download it
		var reference = "2020061713284100000051110100000009";
		
		HttpRequest downloadfRequest = HttpRequest.newBuilder()
				.timeout(Duration.ofMinutes(1))
				.uri(URI.create(Settings.BASE_WMS_URL + "user-bank-documents/user-id/" + clientId + "/portfolio-id/" + portfolioId + "/download-bank-document/reference/" + reference))
				.header("Accept-Language", "de-DE")
				.header("Authorization", "Bearer " + Settings.ADMIN_TOKEN)
				.header("Content-Type", "application/json")
				.build();
		
		HttpResponse<byte[]> byteArrayResponse = httpClient.send(downloadfRequest, BodyHandlers.ofByteArray());
		if (byteArrayResponse.statusCode() != 200 && byteArrayResponse.statusCode() != 201) {
			throw new Exception("user-bank-documents ==> response.statusCode() = " + byteArrayResponse.statusCode());
		}
		
		Files.write(Paths.get("C:\\Temp\\BankDocument.pdf"), byteArrayResponse.body());
	}
}
