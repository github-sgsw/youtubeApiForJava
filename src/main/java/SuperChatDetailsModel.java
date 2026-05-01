import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuperChatDetailsModel {
  private String amountMicros;
  private String currency;
  private String amountDisplayString;
  private String userComment;
  private String tier;

  public SuperChatDetailsModel() {
  }

  public String getAmountMicros() {
    return amountMicros;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAmountDisplayString() {
    return amountDisplayString;
  }

  public String getUserComment() {
    return userComment;
  }

  public String getTier() {
    return tier;
  }

  public void setAmountMicros(String amountMicros) {
    this.amountMicros = amountMicros;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAmountDisplayString(String amountDisplayString) {
    this.amountDisplayString = amountDisplayString;
  }

  public void setUserComment(String userComment) {
    this.userComment = userComment;
  }

  public void setTier(String tier) {
    this.tier = tier;
  }

}
