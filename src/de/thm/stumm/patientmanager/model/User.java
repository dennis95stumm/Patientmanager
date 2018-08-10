package de.thm.stumm.patientmanager.model;

/**
 * @author Dennis Stumm
 */
public class User {
    /**
     *
     */
    private String username;

    /**
     *
     */
    private String password;

    /**
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.equals((User) obj);
        }

        return false;
    }

    /**
     * @param user
     * @return
     */
    private boolean equals(User user) {
        return this.password.equals(user.getPassword()) && this.username.equals(user.getUsername());
    }
}
