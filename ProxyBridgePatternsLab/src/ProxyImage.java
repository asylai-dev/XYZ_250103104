public class ProxyImage implements Image {
    private final String fileName, userRole;
    private RealImage realImage;
    public ProxyImage(String fileName, String userRole) { this.fileName = fileName; this.userRole = userRole; }
    public void show() {
        if (!"ADMIN".equals(userRole)) { System.out.println("[ACCESS DENIED] You lack permission to view: " + fileName); return; }
        if (realImage == null) realImage = new RealImage(fileName);
        realImage.show();
    }
}
