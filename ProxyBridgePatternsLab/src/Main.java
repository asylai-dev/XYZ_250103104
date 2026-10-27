public class Main {
    public static void main(String[] args) {
        System.out.println("=== Task 1: Protection Proxy ===");
        new ProxyImage("salary_sheet.png", "GUEST").show();
        new ProxyImage("salary_sheet.png", "ADMIN").show();
        System.out.println("\n=== Task 2: Logging Proxy ===");
        Video video = new ProxyVideo("Cat_Memes.mp4");
        video.play(); video.play(); video.play();
        System.out.println("\n=== Task 3: School Firewall ===");
        Internet internet = new SchoolProxyInternet();
        internet.connectTo("wikipedia.org"); internet.connectTo("gaming.com");
        System.out.println("\n=== Task 4: Smart Light Bridge ===");
        Device light = new SmartLight();
        AdvancedRemote remote = new AdvancedRemote(light);
        remote.powerOn(); remote.mute();
        System.out.println("\n=== Task 5: Party Remote Bridge ===");
        PartyRemote partyRemote = new PartyRemote(new Radio());
        partyRemote.partyMode();
        System.out.println("\n=== Task 6: RPG Weapon Bridge ===");
        new Knight(new Sword()).attack();
        new Knight(new MagicWand()).attack();
    }
}
