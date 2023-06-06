import java.util.List;

public class Post {
    private int postID;
    private String title;
    private List<String> category;
    private String content;
    private String postingTime;
    private String postingCity;
    private String Author;
    private String authorRegistrationTime;
    private String authorID;
    private String authorPhone;
    private List<String> authorFollowedBy;
    private List<String> authorWhoFavoritedThePost;
    private List<String> authorWhoSharedThePost;
    private List<String> authorWhoLikedThePost;

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", content='" + content + '\'' +
                ", postingTime='" + postingTime + '\'' +
                ", postingCity='" + postingCity + '\'' +
                ", Author='" + Author + '\'' +
                ", authorRegistrationTime='" + authorRegistrationTime + '\'' +
                ", authorID='" + authorID + '\'' +
                ", authorPhone='" + authorPhone + '\'' +
                ", authorFollowedBy=" + authorFollowedBy +
                ", authorFavorite=" + authorWhoFavoritedThePost +
                ", authorShared=" + authorWhoSharedThePost +
                ", authorLiked=" + authorWhoLikedThePost +
                '}';
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public String getPostingCity() {
        return postingCity;
    }

    public void setPostingCity(String postingCity) {
        this.postingCity = postingCity;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getAuthorRegistrationTime() {
        return authorRegistrationTime;
    }

    public void setAuthorRegistrationTime(String authorRegistrationTime) {
        this.authorRegistrationTime = authorRegistrationTime;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public List<String> getAuthorFollowedBy() {
        return authorFollowedBy;
    }

    public void setAuthorFollowedBy(List<String> authorFollowedBy) {
        this.authorFollowedBy = authorFollowedBy;
    }

    public List<String> getAuthorWhoFavoritedThePost() {
        return authorWhoFavoritedThePost;
    }

    public void setAuthorWhoFavoritedThePost(List<String> authorWhoFavoritedThePost) {
        this.authorWhoFavoritedThePost = authorWhoFavoritedThePost;
    }

    public List<String> getAuthorWhoSharedThePost() {
        return authorWhoSharedThePost;
    }

    public void setAuthorWhoSharedThePost(List<String> authorWhoSharedThePost) {
        this.authorWhoSharedThePost = authorWhoSharedThePost;
    }

    public List<String> getAuthorWhoLikedThePost() {
        return authorWhoLikedThePost;
    }

    public void setAuthorWhoLikedThePost(List<String> authorWhoLikedThePost) {
        this.authorWhoLikedThePost = authorWhoLikedThePost;
    }
}
