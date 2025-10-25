public class TaxLaw extends Law {

    public TaxLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Tax Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 2.0);
        }
        return penalty;
    }
}
