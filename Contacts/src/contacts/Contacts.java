package contacts;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Contacts.
 */
class Contacts {
    /**
     * The Records.
     */
    private final ArrayList<Record> RECORDS = new ArrayList<>();

    /**
     * The Scanner.
     */
    private Scanner scanner;

    /**
     * Start.
     */
    public static void start() {
        Contacts contacts = new Contacts();
        contacts.scanner = new Scanner(System.in);
        contacts.mainMenu();
    }

    /**
     * Main menu.
     */
    private void mainMenu() {
        System.out.print("[menu] Enter action (add, list, search, count, exit): ");
        switch (scanner.nextLine()) {
            case "add":
                addRecord();
                break;
            case "list":
                listMenu();
                break;
            case "search":
                searchMenu();
                break;
            case "count":
                System.out.println("The Phone Book has " + this.RECORDS.size() + " records.");
                break;
            case "exit":
                exitProgram();
                break;
            default:
                System.out.println("Invalid input argument.");
                break;
        }
        System.out.println();
        mainMenu();
    }

    /**
     * Add record.
     */
    private void addRecord() {
        System.out.print("Enter the type (person, organization): ");
        Record newRecord = null;
        switch (scanner.nextLine()) {
            case "person":
                newRecord = new IndividualRecord();
                break;
            case "organization":
                newRecord = new OrganisationalRecord();
                break;
            default:
                System.out.println("Invalid input.");
                mainMenu();
                break;

        }
        assert newRecord != null;
        addRecordDetails(newRecord);
    }

    /**
     * Add record details.
     *
     * @param newRecord the new record
     */
    private void addRecordDetails(Record newRecord) {
        for (String field : newRecord.getFields()) {
            System.out.print("Enter the " + field + ": ");
            newRecord.setField(field, scanner.nextLine());
        }
        RECORDS.add(newRecord);
        System.out.println("Record successfully added!\n");
        mainMenu();
    }

    /**
     * List menu.
     */
    private void listMenu() {
        System.out.println(recordsList(RECORDS) + "\n");
        if (RECORDS.isEmpty()) {
            mainMenu();
        }
        System.out.print("[list] Enter action ([number], back): ");

        String option = scanner.nextLine();
        if (isNumeric(option)) {
            int index = Integer.parseInt(option) - 1;
            System.out.println(RECORDS.get(index));
            recordMenu(RECORDS.get(index));
        } else if (option.equals("back")) {
            mainMenu();
        } else {
            System.out.println("Invalid option!");
            mainMenu();
        }
    }

    /**
     * Records list string.
     *
     * @param records the records
     * @return the string
     */
    private String recordsList(ArrayList<Record> records) {
        if (RECORDS.isEmpty()) {
            return "This Phone Book contains no records.";
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0, j = 1; i < records.size(); i++, j++) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("\n");
            }
            sb.append(j).append(". ").append(records.get(i).getName());
        }
        return sb.toString();
    }

    /**
     * Record menu.
     *
     * @param curRecord the cur record
     */
    private void recordMenu(Record curRecord) {
        System.out.print("[record] Enter action (edit, delete, menu): ");
        switch (scanner.nextLine()) {
            case "edit":
                editMenu(curRecord);
                break;
            case "delete":
                RECORDS.remove(curRecord);
                System.out.println("Record successfully deleted!");
                break;
            case "menu":
                System.out.println();
                mainMenu();
                break;
            default:
                System.out.println("Invalid field!");
                mainMenu();
                break;
        }
    }

    /**
     * Edit menu.
     *
     * @param curRecord the cur record
     */
    private void editMenu(Record curRecord) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Select a field (");
        String[] fields = curRecord.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (i == fields.length - 1) {
                stringBuilder.append(fields[i]).append("): ");
            } else {
                stringBuilder.append(fields[i]).append(", ");
            }
        }
        System.out.print(stringBuilder.toString());
        String fieldVal = this.scanner.nextLine();

        System.out.print("Enter the " + fieldVal + ": ");
        curRecord.setField(fieldVal, scanner.nextLine());
        System.out.println(curRecord);

        int index = RECORDS.indexOf(curRecord);
        RECORDS.set(index, curRecord);
        System.out.println("Record successfully updated!\n");
    }

    /**
     * Search menu.
     */
    private void searchMenu() {
        System.out.print("Enter search query: ");
        String searchQuery = scanner.nextLine().toLowerCase();
        ArrayList<Record> matches = new ArrayList<>();

        for (Record record : RECORDS) {
            for (String field : record.getFields()) {
                if ((!matches.contains(record))
                        && record.getFieldValue(field).toLowerCase().contains(searchQuery)) {
                    matches.add(record);
                }
            }
        }

        if (!matches.isEmpty()) {
            System.out.println(recordsList(matches));
            System.out.print("[search] Enter action ([number], back, again): ");

            String selectedField = scanner.nextLine();
            if (isNumeric(selectedField)) {
                int index = Integer.parseInt(selectedField) - 1;
                if (index < matches.size() && index >= 0) {
                    Record curRecord = matches.get(index);
                    recordMenu(curRecord);
                } else {
                    System.out.println("Invalid selection!");
                    searchMenu();
                }

            } else if (selectedField.equals("back")) {
                System.out.println();
                mainMenu();
            } else if (selectedField.equals("again")) {
                searchMenu();
            } else {
                System.out.println("Invalid option!");
                mainMenu();
            }
        } else {
            System.out.println("No matches found!");
        }
    }

    /**
     * Is numeric boolean.
     *
     * @param strNum the str num
     * @return the boolean
     */
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Exit program.
     */
    private void exitProgram() {
        System.exit(100);
    }

}
