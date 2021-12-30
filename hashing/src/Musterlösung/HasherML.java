package Musterlösung;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * Dies hier ist die Musterlösung
 * Bitte nicht Spoilern lassen!
 */

public class HasherML {

    /**
     * Wandelt Binär (byte[]) in Hex um, für einfacheres Speichern und Darstellung
     * 
     * @param binaryBytes
     * @return Hex-String
     */
    private static String binToHex(byte[] binaryBytes) {
        // StringBuilder wird verwendet um mehrere Zeichen effizient
        // aneinander zu ketten
        StringBuilder sb = new StringBuilder();

        // Jedes Byte als Hex (%02x) formatieren
        for (byte binByte : binaryBytes) {
            sb.append(String.format("%02x", binByte));
        }

        // Hash als HexString zurück geben
        return sb.toString();
    }

    /**
     * Hasht ein Passwort
     * 
     * Passwort wird als byte[] übergeben. String kann mithilfe von '.getBytes()'
     * umgewandelt werden.
     * 
     * @param passwort
     */
    public static void hashPasswortOhneSalt(byte[] passwort) {
        try {
            // MessageDigests ist eine sichere unidirektionale Hashfunktion, die Daten
            // beliebiger Größe annimmt und einen Hashwert fester Länge ausgiebt.
            // Dem Konstruktor übergibt man den Algorihtmus (SHA-256) und gibt die
            // dementsprechende Instanz zurück.
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Nun kann mit md ein Hash generiert werden
            byte[] hashedPassword = md.digest(passwort);

            // Aus dem Binär wird nun ein Hex generiert
            String hashedPasswd = binToHex(hashedPassword);

            // Hash in Konsole ausgeben
            System.out.println("Passwort Hash:" + hashedPasswd);

        } catch (NoSuchAlgorithmException e) {
            // Ausgewählter Algorithmus existiert nicht
            System.out.println(e.getMessage());
        }
    }

    /**
     * Hasht ein Passwort mit einem zufälligen Salt
     * 
     * Passwort wird als byte[] übergeben. String kann mithilfe von '.getBytes()'
     * umgewandelt werden.
     * 
     * @param passwort
     */
    public static void hashPasswortMitSalt(byte[] passwort) {
        try {
            // MessageDigests ist eine sichere unidirektionale Hashfunktion, die Daten
            // beliebiger Größe annimmt und einen Hashwert fester Länge ausgiebt.
            // Dem Konstruktor übergibt man den Algorihtmus (SHA-256) und gibt die
            // dementsprechende Instanz.
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Randome generiert eine zufähllige Zahl
            // - Randome 48bit
            // - SecureRandome 128bit
            // Daher sind die Chancen, sich in SecureRandom zu wiederholen, geringer.
            // Allgemein schwieriger zu knacken
            SecureRandom random = new SecureRandom();

            // Erstellung eines Arrays von bytes für das Salting
            // 16 byte weil 16*8 = 128bit | SecureRandome = 128bit
            // Wahlweise Zahl auch erweiterbar für noch mehr Sicherheit
            byte[] salt = new byte[16];

            // Geht beim Array von byte zu byte und setzt eine zufällige Zahl hinein.
            random.nextBytes(salt);

            // Übergeben des salt an den Digest für die Berechnung
            md.update(salt);

            // Nun kann mit md ein salted Hash generiert werden
            byte[] hashedPasswordBytes = md.digest(passwort);

            // Aus dem Binär wird nun ein Hex generiert
            String hashedPasswd = binToHex(hashedPasswordBytes);
            String hashedSalt = binToHex(salt);

            // Hash und Salt in Konsole ausgeben
            // Beides Speichern, damit gleicher Hash generiert werden kann
            System.out.println("Passwort Hash:" + hashedPasswd);
            System.out.println("Salt als Hex: " + hashedSalt);

        } catch (NoSuchAlgorithmException e) {
            // Ausgewählter Algorithmus existiert nicht
            System.out.println(e.getMessage());
        }
    }

    /**
     * Methode, welche Passwörter für schnelles Testen ausgiebt
     */
    public static void HashPasswort() {
        // Erklärungen & Kommentare in den jeweiligen anderen Methoden...

        try {
            Scanner sc = new Scanner(System.in);

            String[] algs = new String[] { "SHA-1", "SHA-256", "SHA-384", "SHA-512", "MD2", "MD5" };

            System.out.println("\n\nWelchen Algorithmus möchtest du verwenden?");
            for (int i = 0; i < algs.length; i++) {
                System.out.println(i + 1 + ". " + algs[i]);
            }
            System.out.print("Auswahl: ");

            MessageDigest md = MessageDigest.getInstance(algs[Integer.valueOf(sc.nextLine()) - 1]);

            System.out.print("Sollte das Passwort gesaltet werden? (Y/N): ");
            byte[] salt = new byte[16];
            boolean useSalting = sc.nextLine().equalsIgnoreCase("Y");
            if (useSalting) {
                SecureRandom random = new SecureRandom();
                random.nextBytes(salt);
                md.update(salt);
            }

            System.out.print("Gebe dein Passwort ein: ");
            byte[] hashedPasswordBytes = md.digest(sc.nextLine().getBytes());

            System.out.println("\n\nResultat:");
            System.out.println("Passwort Hash: " + binToHex(hashedPasswordBytes));
            if (useSalting) {
                System.out.println("Salt als Hex: " + binToHex(salt));
            }

            sc.close();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Fehler: Ausgewählter Algorithmus existiert nicht" + e.getMessage());
        }
    }
}

/**
 * Was ist MessageDigest => Hashing nichts neues, darum hat Java schon Methoden
 * dafür => Kann MD5, SHA-256 & SHA-526... hashes bilden
 * 
 * Was ist byte[] => byte (8 Bits) 10101010 => Binär Array
 * 
 * Wieso byte[] => Standardmässig sind Passwörter nicht als String sondern
 * byte[] => Mehr Sicherheit und Kontrolle
 * 
 * StringBuilder => Wird für das zusammensetzen von mehreren Strings verwendet,
 * da effizienter
 * 
 * Was ist SecureRandom => Random einfach Sicherer (grösser, zufälliger, ...)
 */
