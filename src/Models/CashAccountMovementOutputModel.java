package Models;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class CashAccountMovementOutputModel {
	@SerializedName("cashAccountId")
	public long CashAccountId;

	@SerializedName("bookingDate")
	public Date BookingDate;

	@SerializedName("amount")
	public BigDecimal Amount;

	@SerializedName("saldo")
	public BigDecimal Saldo;

	@SerializedName("transactionId")
	public String TransactionId;

	@SerializedName("bookingCodeId")
	public long BookingCodeId;

	@SerializedName("bookingCode")
	public String BookingCode;

	@SerializedName("bookingCodeType")
	public String BookingCodeType;

	@SerializedName("bookingDescription")
	public String BookingDescription;

	@SerializedName("isin")
	public String Isin;

	@SerializedName("isFirstPayment")
	public Boolean IsFirstPayment;

	@SerializedName("usetText")
	public String UsetText;

	@SerializedName("unitPrice")
	public BigDecimal UnitPrice;

	@SerializedName("quantity")
	public BigDecimal Quantity;
}