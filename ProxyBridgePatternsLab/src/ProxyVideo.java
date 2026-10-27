public class ProxyVideo implements Video {
    private final String fileName;
    private RealVideo realVideo;
    private int viewCount = 0;
    public ProxyVideo(String fileName) { this.fileName = fileName; }
    public void play() {
        viewCount++;
        System.out.println("--> [AUDIT] Video has been played " + viewCount + " time(s).");
        if (realVideo == null) realVideo = new RealVideo(fileName);
        realVideo.play();
    }
}
