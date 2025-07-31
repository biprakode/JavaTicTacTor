public abstract class Player {
    protected game game;
    protected String marker;

    Player(game game , String marker) {
        this.game = game;
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public abstract int selectPosition();
    public String toString() {
        return "Player";
    };

}
