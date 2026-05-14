# Exercise 14 — Facade

## Scenario
You're building a home media system. Behind the scenes there are multiple complex subsystems: an amplifier, a streaming player, a projector, lighting, and a speaker array. Each has its own verbose API with dozens of methods. Users just want to say "movie night" or "music mode."

## What to build

### Subsystem classes (complex internal APIs)
```java
public class Amplifier {
    public void on() { }
    public void off() { }
    public void setVolume(int level) { }  // 0-100
    public void setInput(String source) { } // "hdmi1", "hdmi2", "bluetooth"
    public void setSurroundMode(String mode) { } // "stereo", "5.1", "dolby-atmos"
}

public class StreamingPlayer {
    public void power(boolean on) { }
    public void login(String service, String token) { }
    public void play(String contentId) { }
    public void pause() { }
    public void stop() { }
    public void setResolution(String res) { } // "1080p", "4k", "8k"
}

public class Projector {
    public void powerOn() { }
    public void powerOff() { }
    public void setAspectRatio(String ratio) { } // "16:9", "21:9", "4:3"
    public void setLampMode(String mode) { } // "eco", "bright", "cinema"
}

public class SmartLighting {
    public void setScene(String scene) { } // "bright", "dim", "movie", "off"
    public void setBrightness(int percent) { }
    public void setColor(String hex) { }
}
```

### Facade
```java
public class HomeTheaterFacade {
    // Constructor takes all subsystem instances

    public void movieNight(String contentId) { ... }
    // Powers everything on, sets cinema modes, dims lights, plays content

    public void musicMode(String playlistId) { ... }
    // Projector off, amplifier to stereo, lights to "dim", streams music

    public void shutdown() { ... }
    // Graceful shutdown of all subsystems in correct order

    public void pause() { ... }
    public void resume() { ... }
}
```

## Twist
### Part A — Stateful Facade
The facade must track the current mode (`IDLE`, `MOVIE`, `MUSIC`) and reject invalid transitions:
- Cannot `resume()` if already playing
- Cannot `movieNight()` if already in movie mode without calling `shutdown()` first
- `pause()` only works when in an active mode

### Part B — Extensible Facade
New subsystems may be added later. Design the facade so it accepts an optional list of `SubsystemPlugin` objects:
```java
public interface SubsystemPlugin {
    String name();
    void activate(String mode);  // mode = "movie", "music", "shutdown"
    void deactivate();
}
```
When `movieNight()` is called, all registered plugins receive `activate("movie")`. This allows future devices (e.g., a popcorn machine, motorized curtains) without modifying the facade.

## Verify
```
facade.movieNight("tt1375666");
// → Amplifier on, volume 75, Dolby Atmos, HDMI1
// → Projector on, 21:9, cinema lamp
// → Lights: movie scene, 10% brightness
// → StreamingPlayer: 4k, playing tt1375666
// → All plugins activated with "movie"

facade.movieNight("tt0468569");
// → IllegalStateException: already in MOVIE mode, call shutdown() first

facade.shutdown();
// → Everything off in reverse order, plugins deactivated
```
