// Traffic Laws (Motor Vehicles Act)
public class TrafficLaw extends Law {

    public TrafficLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Traffic Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        // Base fine + increment for repeat offenses
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 0.5); // 50% increase per repeat
        }
        return penalty;
    }
}