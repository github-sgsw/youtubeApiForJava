import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDetailsModel {
  CommentDetailsModel() {
  }

  private SnippetModel snippet;
  private AuthorDetailsModel authorDetails;

  public SnippetModel getSnippet() {
    return snippet;
  }

  public AuthorDetailsModel getAuthorDetailsModel() {
    return authorDetails;
  }

  public void setSnippet(SnippetModel snippet) {
    this.snippet = snippet;
  }

  public void setAuthorDetails(AuthorDetailsModel authorDetails) {
    this.authorDetails = authorDetails;
  }

}
