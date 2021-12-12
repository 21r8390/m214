
// Imports bitte NICHT Löschen!
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hasher {

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
}
