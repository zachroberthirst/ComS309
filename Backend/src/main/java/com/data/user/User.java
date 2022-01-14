package com.data.user;

import com.data.match.Match;
import com.data.setting.Setting;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users_Table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotFound(action = NotFoundAction.IGNORE)
    private String firstName;

    @NotFound(action = NotFoundAction.IGNORE)
    private String lastName;

    @NotFound(action = NotFoundAction.IGNORE)
    private String birthday;

    @Column(unique = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private String username;

    @NotFound(action = NotFoundAction.IGNORE)
    private String email;

    @NotFound(action = NotFoundAction.IGNORE)
    private String password;


    @NotFound(action = NotFoundAction.IGNORE)
    private String salt;

    private UserType type;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany
    @JsonIgnore
    private List<Match> matches;

    @OneToOne
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private Setting setting;

    /**
     * A method that serves as a constructor for the users profile and simply sets the given variables
     * to each of the users variables. It also creates a new arraylist to the hold the User's  matches.
     *
     * @param firstName User's first name
     * @param lastName  User's last name
     * @param birthday  User's birthday
     * @param email     User's email
     * @param userName  User's account username
     * @param password  User's password
     * @param salt      User's Salt for password retrieval
     */
    public User(String firstName, String lastName, String birthday, String email, String userName, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.username = userName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.matches = new ArrayList<>();
    }

    /**
     * A method that serves to create a user with solely a match arraylist for testing purposes.
     */
    public User() {
        this.matches = new ArrayList<>();
    }

    //Getters

    /**
     * A simple getter that returns the current User's Id
     * @return Returns the User's id in integer form
     */
    public int getId() { return id; }

    /**
     * A simple getter that returns the current User's first name
     * @return Returns the User's first name in string form
     */
    public String getFirstName() {return firstName;}

    /**
     * A simple getter that returns the current User's last name
     * @return Returns the User's last name in string form
     */
    public String getLastName() {return lastName;}

    /**
     * A simple getter that returns the current User's birthday
     * @return Returns the User's birthday in String form
     */
    public String getBirthday() {return birthday;}

    /**
     * A simple getter that returns the current User's username
     * @return Returns the User's username in String form
     */
    public String getUsername() {return username;}

    /**
     * A simple getter that returns the current User's Email
     * @return Returns the User's Email in String form
     */
    public String getEmail() {return email;}

    /**
     * A simple getter that returns the current User's password
     * @return Returns the User's password in String form
     */
    public String getPassword() {return password;}

    /**
     * A simple getter that returns the current User's Salt
     * @return Returns the User's Salt in String form
     */
    public String getSalt() {return salt;}

    /**
     * A simple getter that returns the current User's Type
     * @return Returns the User's type in the UserType object form
     */
    public UserType getType() { return type; }

    /**
     * A simple getter that returns the current User's Setting
     * @return Returns the User's Setting in Setting form
     */
    public Setting getSetting() {return setting;}

    /**
     * A simple getter that returns the current User's List of matches
     * @return Returns the User's list of matches in ArrayList form
     */
    public List<Match> getMatches() {return matches;}

    //Setters

    /**
     * An individual Setter for changing the current User's first name
     * @param firstName A String input that serves as the input for changing a User's first name
     */
    public void setFirstName(String firstName) {this.firstName = firstName;}

    /**
     * An individual Setter for changing the current User's last name
     * @param lastName A String input that serves as the input for changing a User's last name
     */
    public void setLastName(String lastName) {this.lastName = lastName;}

    /**
     * An individual Setter for changing the current User's birthday
     * @param birthday A String input that serves as the input for changing a User's birthday
     */
    public void setBirthday(String birthday) {this.birthday = birthday;}

    /**
     * An individual Setter for changing the current User's username
     * @param username A String input that serves as the input for changing a User's username
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * An individual Setter for changing the current User's Email
     * @param email A String input that serves as the input for changing a User's
     */
    public void setEmail(String email) {this.email = email;}

    /**
     * An individual Setter for changing the current User's password
     * @param password A String input that serves as the input for changing a User's password
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * An individual Setter for changing the User's password salt
     * @param salt A String input that serves as the input for changing a User's password salt
     */
    public void setSalt(String salt) {this.salt = salt;}

    /**
     * An individual Setter for changing the User's Tpe
     * @param type A UserType input that serves as the input for changing a User's UserType
     */
    public void setType(UserType type) {this.type = type;}

    /**
     * An individual Setter for changing the users Setting
     * @param setting A Setting input that serves as the input for changing a User's Setting
     */
    public void setSetting(Setting setting) {this.setting = setting;}

    /**
     * An individual Setter for adding a match to the User's match arraylist
     * @param match A Match input that serves as the input for adding a match to a User's Match's list
     */
    public void addMatch(Match match){this.matches.add(match);}
}
