package rm.iamjosephvarghese.deadlock2k18;

import java.util.Date;

/**
 * Created by joseph on 26/01/18.
 */

public class LogData {
    String UID;
    String answer;
    int currentLevel;
    String displayName;
    String email;
    String phno;
    Date timestamp;


    public LogData() {
    }

    public LogData(String UID, String answer, int currentLevel, String displayName, String email, String phno, Date timestamp) {
        this.UID = UID;
        this.answer = answer;
        this.currentLevel = currentLevel;
        this.displayName = displayName;
        this.email = email;
        this.phno = phno;
        this.timestamp = timestamp;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
