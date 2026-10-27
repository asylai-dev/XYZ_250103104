public class SchoolProxyInternet implements Internet {
    private RealInternet realInternet;
    public void connectTo(String site) {
        if ("gaming.com".equals(site) || "socialmedia.com".equals(site)) { System.out.println("[BLOCKED BY FIREWALL] Cannot access: " + site); return; }
        if (realInternet == null) realInternet = new RealInternet();
        realInternet.connectTo(site);
    }
}
