package de.thm.stumm.patientmanager.model;

/**
 * Class holding the information of a user.
 *
 * @author Dennis Stumm
 */
public class User {
    /**
     * The name, whereby the user gets authenticated.
     */
    private String username;

    /**
     * The SHA1 hashed password of the user.
     */
    private String password;

    /**
     * Initializes the user object by setting the passed values as the values of the object properties.
     *
     * @param username The name of the user.
     * @param password The SHA1 hashed password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return The SHA1 hashed password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return The name of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns a boolean value that indicates whether the passed object is equal to this user object.
     *
     * If the passed object is a instance of the class User the two user objects gets compared. Otherwise
     * the object is guessed as not equal.
     *
     * @param obj The object to compare the user object with.
     * @return Boolean value that indicates, whether the passed object is equal to this object or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return this.equals((User) obj);
        }

        return false;
    }

    /**
     * Returns a boolean value that indicates whether the passed user object is equal to this user object.
     *
     * To determine whether the both objects are equal or not, the password and username gets compared.
     *
     * @param user The second user object to compare this object with.
     * @return Boolean value that indicates, whether the passed object is equal to this object or not.
     */
    private boolean equals(User user) {
        return this.password.equals(user.getPassword()) && this.username.equals(user.getUsername());
    }
}
