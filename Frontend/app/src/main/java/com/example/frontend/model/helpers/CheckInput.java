package com.example.frontend.model.helpers;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

import android.os.Build;
import android.text.Layout;
import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;

import java.time.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Check input class to check input fields based on set criteria
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class CheckInput {
    /**
     * Check if full name is a valid input.
     * @param fullName full name to be checked
     * @return error message
     */
    public static String fullName(String fullName){
        String[] names = fullName.split(" ");
        Pattern p = Pattern.compile("[^A-Za-z ]");
        Matcher m = p.matcher(fullName);

        if(fullName.isEmpty()){
            return "Name cannot be empty";
        }else if(m.find()){
            return "Name can only have a-z and A-Z";
        }else if(names.length != 2){
            return "Please enter your first and last name";
        }
        return null;
    }

    /**
     * Check if shelter name is a valid input.
     * @param shelterName shelter name to be checked
     * @return error message
     */
    public static String shelterName(String shelterName){
        Pattern p = Pattern.compile("[^A-Za-z ]");
        Matcher m = p.matcher(shelterName);
        if(shelterName.isEmpty()){
            return "Name cannot be empty";
        }else if(m.find()){
            return "Name can only have a-z and A-Z";
        }
        return null;
    }

    /**
     * Check if birthday is a valid input.
     * @param date date to be checked
     * @return error message
     */
    public static String dOB(String date){
        String[] dates = date.split("/");
        //check if date is null
        if(date.isEmpty()){
            return "Date Of Birth cannot be empty";
        }else if(dates.length != 3){
            return "Date is formatted MM/DD/YYYY";
        }

        int year = Integer.parseInt(dates[2]);
        int day = Integer.parseInt(dates[1]);
        int month = Integer.parseInt(dates[0]);

        LocalDate birthday;
        try {
            birthday = LocalDate.of(year, month, day);
        }catch (java.time.DateTimeException e){
            e.printStackTrace();
            return "Invalid date";
        }

        LocalDate currentDay = LocalDate.now();
        int age = Period.between(birthday, currentDay).getYears();

        if(!birthday.isSupported(DAY_OF_MONTH)){
            return "Invalid date";
        }
        else if (age < 18){
            return "Must be 18 or older!";
        }
        else if (age >= 130){
            return "Invalid birthday";
        }
        return null;
    }

    /**
     * Check if email is a valid input.
     * @param email email to be checked
     * @return error message
     */
    public static String email(String email){
        if(email.isEmpty()){
            return "Email cannot be empty";
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Please enter a valid email";
        }
        return null;
    }

    /**
     * Check if username is a valid input.
     * @param username username to be checked
     * @return error message
     */
    public static String userName(String username){
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(username);
        if(username.isEmpty()){
            return "Username cannot be empty";
        }
        else if(m.find()){
            return "Usernames can only hold a-z, A-Z, 0-9";
        }
        else if(username.length() > 12){
            return "Username is too long";
        }
        return null;
    }

    /**
     * Chek if password is a valid input.
     * @param password password to be checked
     * @return error message
     */
    public static String password(String password){
        if(password.isEmpty()){
            return "Password cannot be empty";
        }
        return null;
    }

    /**
     * Set error message in TextInputLayout
     * @param error error message to be set
     * @param textField text field to hold error message
     * @return if there was an error message or not
     */
    public static boolean setError(String error, TextInputLayout textField){
        if(error != null) {
            textField.setError(error);
            return false;
        }
        textField.setError(null);
        return true;
    }

    /**
     * Capitalize string
     * @param in string to be capitalized
     * @return capitalized string
     */
    public static String capitalize(String in){
        return in.substring(0,1).toUpperCase() + in.substring(1).toLowerCase();
    }

    public static String message(String msg){
        //Pattern p = Pattern.compile("[:]");
        //Matcher m = p.matcher(msg);
        if(msg.isEmpty()){
            return "Message cannot be empty";
        }
        return null;
    }
}
