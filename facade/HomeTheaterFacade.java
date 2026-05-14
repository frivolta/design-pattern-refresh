public class HomeTheaterFacade {
    // Constructor takes all subsystem instances
    private Amplifier amplifier;
    private Projector projector;
    private SmartLighting smartLighting;
    private StreamingPlayer streamingPlayer;
    private HomeTheatherStateManager state;

    HomeTheaterFacade(
            Amplifier amplifier,
            Projector projector,
            SmartLighting smartLighting,
            StreamingPlayer streamingPlayer) {
        this.amplifier = amplifier;
        this.projector = projector;
        this.smartLighting = smartLighting;
        this.streamingPlayer = smartLighting;
        this.state = new HomeTheatherStateManager();
    }

    public void movieNight(String contentId) {
        this.shutdown();
        this.state.setMovie();
    }
    // Powers everything on, sets cinema modes, dims lights, plays content

    public void musicMode(String playlistId) {
        this.shutdown();
        this.state.setMusic();
    }
    // Projector off, amplifier to stereo, lights to "dim", streams music

    public void shutdown() {
        this.state.setIdle();
    }
    // Graceful shutdown of all subsystems in correct order

    public void pause() {
        this.state.setIdle();
    }

    public void resume() {
        this.state.resume();
    }
}