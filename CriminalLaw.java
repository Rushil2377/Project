// Criminal Laws (Indian Penal Code)
public class CriminalLaw extends Law {

    public CriminalLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Criminal Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        // Criminal offenses - strict penalties
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 1.5); // 150% increase per repeat
        }
        return penalty;
    }
}