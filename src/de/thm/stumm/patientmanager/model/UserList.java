package de.thm.stumm.patientmanager.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Singleton that contains all existing users in the system.
 *
 * @author Dennis Stumm
 */
public class UserList extends List<User> {
    /**
     * Instance of the UserList.
     */
    private static UserList instance;

    /**
     * Array with default users that should be created if no users exist (the users.csv does not exist).
     */
    private final User[] userSeed = {
            new User("max", "3e5c5f0ee799eb1965756f590546061b77167f43"),
            new User("bob", "556f9ae42d491e9bffc3ff34febcaa6fc28c6d3a"),
            new User("john", "d139f80a8bc30efec42efcdd6e5daa71d2941127"),
            new User("alice", "ddc2a2c436454080555bc4c53513c7af2b9e2559")
    };

    /**
     * Initializes the UserList object by seeding the user objects if the user.csv is missing.
     *
     * @throws MalformedCsvLineException If some of the lines in the CSV-File contains errors.
     * @throws IOException If an error gets thrown while reading the CSV-File.
     */
    private UserList() throws MalformedCsvLineException, IOException {
        super();
        this.seedItemsIfNecessary();
    }

    /**
     * Returns the instance of the singleton object and initializes this object if necessary.
     *
     * @return The instance of this singleton.
     * @throws MalformedCsvLineException If some of the lines in the CSV-File contains errors.
     * @throws IOException If an error gets thrown while reading the CSV-File.
     */
    public static UserList getInstance() throws MalformedCsvLineException, IOException {
        if (instance == null) {
            instance = new UserList();
        }

        return instance;
    }

    /**
     * Checks if the user.csv is missing, in that case the user objects from the userSeed gets added to the UserList.
     */
    private void seedItemsIfNecessary() {
        Iterator iterator = iterator();

        if (!iterator.hasNext()) {
            for (User user : userSeed) {
                this.add(user);
            }
        }
    }

    /**
     * Adds a new user object with the information parsed from the passed String in CSV-Format to this UserList.
     *
     * @param csvLine Line in the CSV-Format to parse and get the user information from.
     * @throws MalformedCsvLineException If the passed csvLine contains errors.
     */
    @Override
    protected void add(String csvLine) throws MalformedCsvLineException {
        String[] values = csvLine.split(";", -1);

        if (values.length != 2) {
            throw new MalformedCsvLineException("Die Zeile (" + csvLine + ") enthält zu wenig bzw. zu viel spalten!");
        }

        this.add(new User(values[0], values[1]));
    }

    /**
     * @return Path to the CSV-File where the users of the application should be persisted.
     */
    @Override
    protected Path getFilePath() {
        return Paths.get("./data/user.csv");
    }

    /**
     * Returns a String containing the line for a CSV-File for the user object at the passed index.
     *
     * @param index Index for which user the CSV-Line should be generated.
     * @return String in CSV-Format representing this user object.
     */
    @Override
    protected String getCsvLine(int index) {
        User user = this.get(index);
        return user.getUsername() + ";" + user.getPassword();
    }
}
