package de.thm.stumm.patientmanager.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dennis Stumm
 */
public class UserList extends List<User> {
    /**
     *
     */
    private static UserList instance;

    /**
     *
     */
    private User[] userSeed = {
            new User("max", "3e5c5f0ee799eb1965756f590546061b77167f43"),
            new User("bob", "556f9ae42d491e9bffc3ff34febcaa6fc28c6d3a"),
            new User("john", "d139f80a8bc30efec42efcdd6e5daa71d2941127"),
            new User("alice", "ddc2a2c436454080555bc4c53513c7af2b9e2559")
    };

    /**
     *
     */
    private UserList() {
        super();
        this.seedItemsIfNecessary();
    }

    /**
     * @return
     */
    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }

        return instance;
    }

    /**
     *
     */
    private void seedItemsIfNecessary() {
        if (Files.notExists(getFilePath())) {
            for (User user : userSeed) {
                this.add(user);
            }
        }
    }

    /**
     * @param csvLine
     */
    @Override
    protected void add(String csvLine) {
        String[] values = csvLine.split(";");
        this.add(new User(values[0], values[1]));
    }

    /**
     * @return
     */
    @Override
    protected Path getFilePath() {
        return Paths.get("./data/user.csv");
    }

    /**
     * @param index
     * @return
     */
    @Override
    protected String getCsvLine(int index) {
        User user = this.get(index);
        return user.getUsername() + ";" + user.getPassword();
    }
}
