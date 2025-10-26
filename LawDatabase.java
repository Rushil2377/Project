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
        TrafficLaw tl6 = new TrafficLaw("Section 129", "Riding without helmet", 1000, "Fine up to ₹1500");
        TrafficLaw tl7 = new TrafficLaw("Section 130", "Riding without PUC (Pollution Under Control)", 2000, 
                "Fine up to ₹10,000 or vehicle seized");
        TrafficLaw tl8 = new TrafficLaw("Section 186", "Driving without RC Book (Registration Certificate)", 1500, 
                "Fine up to ₹5000 or imprisonment up to 3 months");
        TrafficLaw tl9 = new TrafficLaw("Section 192", "Using vehicle without third-party insurance", 2500, 
                "Fine up to ₹2000 and 3 months imprisonment");
        TrafficLaw tl10 = new TrafficLaw("Section 199", "Driving under influence of alcohol", 10000, 
                "Fine up to ₹10,000 or imprisonment up to 6 months");

        addLaw("overspeeding", tl3);
        addLaw("rash_driving", tl1);
        addLaw("negligent_driving", tl2);
        addLaw("driving_without_license", tl4);
        addLaw("accident_not_reported", tl5);
        addLaw("riding_without_helmet", tl6);
        addLaw("without_puc", tl7);
        addLaw("without_rc_book", tl8);
        addLaw("without_insurance", tl9);
        addLaw("drunk_driving", tl10);

        allLaws.addAll(Arrays.asList(tl1, tl2, tl3, tl4, tl5, tl6, tl7, tl8, tl9, tl10));

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

        // TAX LAWS (Income Tax Act, GST Act, Custom Laws)
        TaxLaw txl1 = new TaxLaw("Section 271", "Tax evasion or incorrect return filing", 25000,
                "Fine up to 3 times of tax involved or imprisonment up to 7 years");
        TaxLaw txl2 = new TaxLaw("Section 276CC", "Gross amount of income concealed", 50000,
                "Fine up to 50% of tax and imprisonment up to 2 years");
        TaxLaw txl3 = new TaxLaw("Section 132", "GST tax invasion/evasion", 30000,
                "Fine up to ₹2.5 lakhs and imprisonment up to 5 years");
        TaxLaw txl4 = new TaxLaw("Section 114", "Customs duty evasion", 40000,
                "Fine equal to customs duty and confiscation of goods");
        TaxLaw txl5 = new TaxLaw("Section 112", "Non-filing of income tax return", 5000,
                "Fine up to ₹10,000");

        addLaw("tax_evasion", txl1);
        addLaw("income_concealment", txl2);
        addLaw("gst_evasion", txl3);
        addLaw("customs_evasion", txl4);
        addLaw("non_filing_return", txl5);

        allLaws.addAll(Arrays.asList(txl1, txl2, txl3, txl4, txl5));

        // SEXUAL CRIME LAWS (Indian Penal Code)
        SexualCrimeLaw scl1 = new SexualCrimeLaw("Section 375", "Rape", 100000,
                "Rigorous imprisonment up to 7 years or life imprisonment");
        SexualCrimeLaw scl2 = new SexualCrimeLaw("Section 376", "Sexual assault", 50000,
                "Imprisonment up to 5 years or fine up to ₹3000");
        SexualCrimeLaw scl3 = new SexualCrimeLaw("Section 354", "Outraging modesty of a woman", 15000,
                "Imprisonment up to 3 years or fine up to ₹2000");
        SexualCrimeLaw scl4 = new SexualCrimeLaw("Section 509", "Displaying lewd conduct in public", 8000,
                "Imprisonment up to 3 months or fine up to ₹500");

        addLaw("rape", scl1);
        addLaw("sexual_assault", scl2);
        addLaw("outraging_modesty", scl3);
        addLaw("lewd_conduct", scl4);

        allLaws.addAll(Arrays.asList(scl1, scl2, scl3, scl4));

        // PROPERTY LAWS (Indian Penal Code)
        PropertyLaw pl1 = new PropertyLaw("Section 379", "Theft", 1000,
                "Imprisonment up to 3 years or fine up to ₹250");
        PropertyLaw pl2 = new PropertyLaw("Section 380", "Burglary or housebreaking", 5000,
                "Imprisonment up to 10 years or fine");
        PropertyLaw pl3 = new PropertyLaw("Section 405", "Criminal breach of trust", 10000,
                "Imprisonment up to 7 years or fine");
        PropertyLaw pl4 = new PropertyLaw("Section 425", "Mischief or causing damage to property", 3000,
                "Imprisonment up to 3 months or fine up to ₹250");
        PropertyLaw pl5 = new PropertyLaw("Section 427", "Mischief with explosive or fire", 15000,
                "Imprisonment up to 6 months or fine up to ₹1000");

        addLaw("theft", pl1);
        addLaw("burglary", pl2);
        addLaw("breach_of_trust", pl3);
        addLaw("property_damage", pl4);
        addLaw("damage_with_fire", pl5);

        allLaws.addAll(Arrays.asList(pl1, pl2, pl3, pl4, pl5));
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