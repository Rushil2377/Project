// Environmental Laws (Environment Protection Act, Wildlife Protection Act)
public class EnvironmentalLaw extends Law {

    public EnvironmentalLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Environmental Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        // Environmental violations - significant penalties
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 1.2); // 120% increase per repeat
        }
        return penalty;
    }
}