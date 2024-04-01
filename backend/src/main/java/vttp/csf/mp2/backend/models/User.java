package vttp.csf.mp2.backend.models;

import java.time.LocalDate;

public record User(String userID, String name, String email, LocalDate birthDate, String username, String password) {

}
