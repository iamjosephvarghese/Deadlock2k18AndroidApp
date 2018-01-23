package rm.iamjosephvarghese.deadlock2k18;

/**
 * Created by joseph on 23/01/18.
 */

public class User {
    String displayName;
    String college;
    String address;
    String email;
    String photoUrl;
    String currentHash;
    String previousHash;
    String mobno;


    public User() {
    }


    public User(String displayName, String college, String address, String email, String photoUrl, String currentHash, String previousHash, String mobno) {
        this.displayName = displayName;
        this.college = college;
        this.address = address;
        this.email = email;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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
