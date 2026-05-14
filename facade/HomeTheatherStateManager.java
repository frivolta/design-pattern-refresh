public class HomeTheatherStateManager {
    private HomeTheatherState currentState;
    private HomeTheatherState previousState;

    HomeTheatherStateManager() {
        this.currentState = HomeTheatherState.IDLE;
        this.previousState = null;
    }

    public void setIdle() {
        if (HomeTheatherState.IDLE.equals(currentState)) {
            throw new RuntimeException("Already in IDLE State");
        }
        this.previousState = this.currentState;
        this.currentState = HomeTheatherState.IDLE;
    }

    public void resume() {
        if (!HomeTheatherState.IDLE.equals(currentState) || this.previousState == null) {
            throw new RuntimeException("Cannot resume not in IDLE state");
        }
        this.currentState = this.previousState;
        this.previousState = HomeTheatherState.IDLE;
    }

    public void setMovie() {
        if (!HomeTheatherState.IDLE.equals(currentState)) {
            throw new RuntimeException("Cannot start movie before going idle");
        }
        this.previousState = this.currentState;
        this.currentState = HomeTheatherState.MOVIE;
    }

    public void setMusic() {
        if (!HomeTheatherState.IDLE.equals(currentState)) {
            throw new RuntimeException("Cannot start movie before going idle");
        }
        this.previousState = this.currentState;
        this.currentState = HomeTheatherState.MUSIC;
    }

    public void clearState() {
        this.currentState = HomeTheatherState.IDLE;
        this.previousState = null;
    }
}