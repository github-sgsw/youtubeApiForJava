import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SnippetModel {
    public SnippetModel() {}
/**
    private String type;
    private String liveChatId;
    private String authorChannelId;
    private String publishedAt;
    private Boolean hasDisplayContent;
 */
    private String displayMessage;
    private SuperChatDetailsModel superChatDetails;
/**
    public String getType() { return type; }
    public String getLiveChatId() { return liveChatId; }
    public String getAuthorChannelId() { return authorChannelId; }
    public String getPublishedAt() { return publishedAt; }
    public Boolean getHasDisplayContent() { return hasDisplayContent; }
 */
    public String getDisplayMessage() { return displayMessage; }
    public SuperChatDetailsModel getSuperChatDetails() { return superChatDetails; }
/**
    public void setType(String type) { this.type = type; };
    public void setLiveChatId(String liveChatId) { this.liveChatId = liveChatId; }
    public void setAuthorChannelId(String authorChannelId) { this.authorChannelId = authorChannelId; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }
    public void setHasDisplayContent(Boolean hasDisplayContent) { this.hasDisplayContent = hasDisplayContent; }
 */
    public void setDisplayMessage(String displayMessage) { this.displayMessage = displayMessage; }
    public void setSuperChatDetails(SuperChatDetailsModel superChatDetails) { this.superChatDetails = superChatDetails; }

}
