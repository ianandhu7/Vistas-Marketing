package co.vistafoundation.vlearning.video.utils;

public enum VideoResolution {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int value;

    VideoResolution(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

