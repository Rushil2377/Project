// Labor Laws (Industrial Relations Code, Labor Codes)
public class LaborLaw extends Law {

    public LaborLaw(String section, String description, int basicFine, String punishment) {
        super(section, description, basicFine, "Labor Law", punishment);
    }

    @Override
    public int calculatePenalty(int repeatOffenseCount) {
        // Labor law violations - moderate penalties with escalation
        int penalty = basicFine;
        if (repeatOffenseCount > 0) {
            penalty += (basicFine * repeatOffenseCount * 0.75); // 75% increase per repeat
        }
        return penalty;
    }
}