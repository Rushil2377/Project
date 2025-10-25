// Base class for all laws in India
public abstract class Law {
    protected String section;
    protected String description;
    protected int basicFine;
    protected String category;
    protected String punishment;

    public Law(String section, String description, int basicFine, String category, String punishment) {
        this.section = section;
        this.description = description;
        this.basicFine = basicFine;
        this.category = category;
        this.punishment = punishment;
    }

    // Abstract method - each law type calculates penalty differently
    public abstract int calculatePenalty(int repeatOffenseCount);

    public String getSection() {
        return section;
    }

    public String getDescription() {
        return description;
    }

    public int getBasicFine() {
        return basicFine;
    }

    public String getCategory() {
        return category;
    }

    public String getPunishment() {
        return punishment;
    }

    @Override
    public String toString() {
        return "Section: " + section + "\n" +
                "Description: " + description + "\n" +
                "Basic Fine: ₹" + basicFine + "\n" +
                "Category: " + category + "\n" +
                "Punishment: " + punishment;
    }
}