public class PropertyLaw extends Law {

    public PropertyLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Property Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 1.5);
        }
        return penalty;
    }
}
