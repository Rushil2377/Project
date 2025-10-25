import java.util.*;

// Custom Exception for Law not found
class LawNotFoundException extends Exception {
    public LawNotFoundException(String message) {
        super(message);
    }
}

// Database of all Indian laws
public class LawDatabase {
    private static Map<String, Law> lawsMap = new HashMap<>();
    private static List<Law> allLaws = new ArrayList<>();

    // Initialize the database with Indian laws
    static {
        initializeLaws();
    }

    private static void initializeLaws() {
        // TRAFFIC LAWS (Motor Vehicles Act, 1988)
        TrafficLaw tl1 = new TrafficLaw("Section 177", "Causing hurt by driving or riding a motor vehicle rashly", 500,
                "Fine up to ₹1000 or imprisonment up to 6 months");
        TrafficLaw tl2 = new TrafficLaw("Section 180", "Causing death by negligent driving", 5000,
                "Imprisonment up to 2 years");
        TrafficLaw tl3 = new TrafficLaw("Section 183", "Overspeeding or dangerous driving", 1000,
                "Fine up to ₹5000 or imprisonment up to 3 months");
        TrafficLaw tl4 = new TrafficLaw("Section 184", "Driving without license", 500, "Fine up to ₹5000");
        TrafficLaw tl5 = new TrafficLaw("Section 191", "Failure to notify accident", 1000, "Fine up to ₹1000");

        addLaw("overspeeding", tl3);
        addLaw("rash_driving", tl1);
        addLaw("negligent_driving", tl2);
        addLaw("driving_without_license", tl4);
        addLaw("accident_not_reported", tl5);

        allLaws.addAll(Arrays.asList(tl1, tl2, tl3, tl4, tl5));

        // CYBER LAWS (Information Technology Act, 2000)
        CyberLaw cl1 = new CyberLaw("Section 66", "Hacking or unauthorized access to computer", 50000,
                "Imprisonment up to 3 years");
        CyberLaw cl2 = new CyberLaw("Section 67", "Obscene material in electronic form", 5000,
                "Imprisonment up to 5 years");
        CyberLaw cl3 = new CyberLaw("Section 72", "Breach of confidentiality", 10000, "Imprisonment up to 3 years");
        CyberLaw cl4 = new CyberLaw("Section 43", "Unauthorized access to computer", 1000,
                "Civil liability up to 1 lakh");

        addLaw("hacking", cl1);
        addLaw("obscene_content", cl2);
        addLaw("breach_confidentiality", cl3);
        addLaw("unauthorized_access", cl4);

        allLaws.addAll(Arrays.asList(cl1, cl2, cl3, cl4));

        // CRIMINAL LAWS (Indian Penal Code)
        CriminalLaw crl1 = new CriminalLaw("Section 302", "Murder", 10000, "Death penalty or life imprisonment");
        CriminalLaw crl2 = new CriminalLaw("Section 304", "Causing death by negligence", 5000,
                "Imprisonment up to 2 years or fine up to ₹1000");
        CriminalLaw crl3 = new CriminalLaw("Section 323", "Causing hurt", 500,
                "Imprisonment up to 6 months or fine up to ₹250");
        CriminalLaw crl4 = new CriminalLaw("Section 379", "Theft", 1000,
                "Imprisonment up to 3 years or fine up to ₹250");
        CriminalLaw crl5 = new CriminalLaw("Section 420", "Cheating or fraud", 2000,
                "Imprisonment up to 7 years or fine");
        CriminalLaw crl6 = new CriminalLaw("Section 506", "Criminal intimidation", 500,
                "Imprisonment up to 2 years or fine up to ₹1000");

        addLaw("murder", crl1);
        addLaw("negligent_death", crl2);
        addLaw("causing_hurt", crl3);
        addLaw("theft", crl4);
        addLaw("cheating_fraud", crl5);
        addLaw("criminal_intimidation", crl6);

        allLaws.addAll(Arrays.asList(crl1, crl2, crl3, crl4, crl5, crl6));

        // LABOR LAWS (Industrial Relations Code, 2020)
        LaborLaw ll1 = new LaborLaw("Section 223", "Unauthorized strikes", 5000, "Imprisonment or fine");
        LaborLaw ll2 = new LaborLaw("Section 224", "Lockouts by employers", 10000, "Penalty on employer");
        LaborLaw ll3 = new LaborLaw("Section 103", "Child labor violation", 20000,
                "Fine and imprisonment up to 2 years");
        LaborLaw ll4 = new LaborLaw("Section 108", "Unequal pay for equal work", 1000, "Fine or adjustment of wages");

        addLaw("unauthorized_strike", ll1);
        addLaw("lockout_violation", ll2);
        addLaw("child_labor", ll3);
        addLaw("unequal_pay", ll4);

        allLaws.addAll(Arrays.asList(ll1, ll2, ll3, ll4));

        // ENVIRONMENTAL LAWS
        EnvironmentalLaw el1 = new EnvironmentalLaw("Section 15", "Air pollution violation", 5000,
                "Fine up to ₹1 lakh");
        EnvironmentalLaw el2 = new EnvironmentalLaw("Section 25", "Water pollution violation", 10000,
                "Fine up to ₹5 lakhs");
        EnvironmentalLaw el3 = new EnvironmentalLaw("Section 49", "Wildlife protection violation", 15000,
                "Imprisonment up to 7 years");
        EnvironmentalLaw el4 = new EnvironmentalLaw("Section 51", "Illegal tree cutting", 2000,
                "Fine or tree plantation compensation");

        addLaw("air_pollution", el1);
        addLaw("water_pollution", el2);
        addLaw("wildlife_violation", el3);
        addLaw("illegal_tree_cutting", el4);

        allLaws.addAll(Arrays.asList(el1, el2, el3, el4));
    }

    private static void addLaw(String keyword, Law law) {
        lawsMap.put(keyword.toLowerCase(), law);
    }

    // Find law by keyword
    public static Law findLawByKeyword(String keyword) throws LawNotFoundException {
        Law law = lawsMap.get(keyword.toLowerCase());
        if (law == null) {
            throw new LawNotFoundException("Law not found for keyword: " + keyword);
        }
        return law;
    }

    // Search laws by keyword (partial match)
    public static List<Law> searchLaws(String searchTerm) {
        List<Law> results = new ArrayList<>();
        String searchLower = searchTerm.toLowerCase();

        for (Map.Entry<String, Law> entry : lawsMap.entrySet()) {
            if (entry.getKey().contains(searchLower) ||
                    entry.getValue().getDescription().toLowerCase().contains(searchLower) ||
                    entry.getValue().getSection().toLowerCase().contains(searchLower)) {
                results.add(entry.getValue());
            }
        }
        return results;
    }

    // Get all laws
    public static List<Law> getAllLaws() {
        return new ArrayList<>(allLaws);
    }

    // Get laws by category
    public static List<Law> getLawsByCategory(String category) {
        List<Law> results = new ArrayList<>();
        for (Law law : allLaws) {
            if (law.getCategory().equalsIgnoreCase(category)) {
                results.add(law);
            }
        }
        return results;
    }

    // Get all categories
    public static List<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        for (Law law : allLaws) {
            categories.add(law.getCategory());
        }
        return new ArrayList<>(categories);
    }

    // Get keywords for autocompletion
    public static List<String> getAllKeywords() {
        return new ArrayList<>(lawsMap.keySet());
    }
}