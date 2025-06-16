package bg.softuni.mobilelele.model.dto;

public class StatsView {

    private final int anonymousRequests;
    private final int authRequests;

    public StatsView(int anonymousRequests, int authRequests) {
        this.anonymousRequests = anonymousRequests;
        this.authRequests = authRequests;
    }

    public int getAnonymousRequests() {
        return anonymousRequests;
    }

    public int getAuthRequests() {
        return authRequests;
    }

    public int getTotalRequests() {
        return authRequests + anonymousRequests;
    }
}
