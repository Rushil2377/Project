// Cyber Laws (Information Technology Act)
public class CyberLaw extends Law {

    public CyberLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Cyber Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        // Cyber crimes are more serious - higher penalties
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 1.0); // 100% increase per repeat
        }
        return penalty;
    }
}