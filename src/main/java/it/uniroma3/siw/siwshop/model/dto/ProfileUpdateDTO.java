package it.uniroma3.siw.siwshop.model.dto;

public class ProfileUpdateDTO {
    private String newUsername;
    private String newPassword;

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
