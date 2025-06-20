package Models;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class CashAccountOutputModel {
	@SerializedName("id")
	public long Id;
	
	@SerializedName("accountTypeId")
	public long AccountTypeId; 
	
	@SerializedName("iban")
	public String Iban;

	@SerializedName("balance")
	public BigDecimal Balance;

	@SerializedName("balanceDate")
	public Date BalanceDate;

	@SerializedName("currencyId")
	public long CurrencyId;
}
