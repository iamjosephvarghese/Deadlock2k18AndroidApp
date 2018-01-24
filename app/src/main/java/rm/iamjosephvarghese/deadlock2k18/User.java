package rm.iamjosephvarghese.deadlock2k18;

/**
 * Created by joseph on 23/01/18.
 */

public class User {
    String displayName;
    String college;
    String address;
    String email;
    String photoURL;
    String currentHash;
    String previousHash;
    String mobno;


    public User() {
    }


    public User(String displayName, String college, String address, String email, String photoURL, String currentHash, String previousHash, String mobno) {
        this.displayName = displayName;
        this.college = college;
        this.address = address;
        this.email = email;
        this.photoURL = photoURL;
        this.currentHash = currentHash;
        this.previousHash = previousHash;
        this.mobno = mobno;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }
}
