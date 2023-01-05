import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthorDetailsModel {
    private String channelId;
    private String channelUrl;
    private String displayName;
    /**
    private String profileImageUrl;
    private Boolean isVerified;
    private Boolean isChatOwner;
    private Boolean isChatSponsor;
    private Boolean isChatModerator;*/

    public String getChannelId() { return channelId; }
    public String getChannelUrl() { return channelUrl; }
    public String getDisplayName() { return displayName; }
    /**
    public Boolean getChatOwner() { return isChatOwner; }
    public Boolean getVerified() { return isVerified; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public Boolean getChatSponsor() { return isChatSponsor; }
    public Boolean getChatModerator() { return isChatModerator; }
     */

    public void setChannelIId(String channelId) { this.channelId = channelId; }
    public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    /**
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    public void setVerified(Boolean verified) { this.isVerified = verified; }
    public void setChatOwner(Boolean chatOwner) { this.isChatOwner = chatOwner; }
    public void setChatSponsor(Boolean chatSponsor) { this.isChatSponsor = chatSponsor; }
    public void setChatModerator(Boolean chatModerator) {this.isChatModerator = chatModerator; }
     */


}
