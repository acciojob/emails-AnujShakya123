package com.driver;

import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Gmail extends Email {

    private int inboxCapacity;
    private ArrayList<Triple<Date, String, String>> Inbox;
    private ArrayList<Triple<Date, String, String>> Trash;

    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
        this.Inbox = new ArrayList<>();
        this.Trash = new ArrayList<>();
    }

    public void receiveMail(Date date, String sender, String message) {
        if (Inbox.size() == inboxCapacity) {
            Triple<Date, String, String> oldestMail = Inbox.get(0);
            Trash.add(oldestMail);
            Inbox.remove(0);
        }
        Triple<Date, String, String> mail = Triple.of(date, sender, message);
        Inbox.add(mail);
    }

    public void deleteMail(String message) {
        Triple<Date, String, String> mailToDelete = null;
        for (Triple<Date, String, String> mail : Inbox) {
            if (mail.getRight().equals(message)) {
                mailToDelete = mail;
                break;
            }
        }

        if (Objects.nonNull(mailToDelete)) {
            Trash.add(0, mailToDelete);
            Inbox.remove(mailToDelete);
        }
    }

    public String findLatestMessage() {
        if (Inbox.isEmpty())
            return null;
        return Inbox.get(Inbox.size() - 1).getRight();
    }

    public String findOldestMessage() {
        if (Inbox.isEmpty())
            return null;
        return Inbox.get(0).getRight();
    }

    public int findMailsBetweenDates(Date start, Date end) {
        int cnt = 0;
        for (Triple<Date, String, String> mail : Inbox) {
            Date myDate = mail.getLeft();
            if (myDate.compareTo(start) >= 0 && myDate.compareTo(end) <= 0) {
                cnt++;
            }
        }
        return cnt;
    }

    public int getInboxSize() {
        return Inbox.size();
    }

    public int getTrashSize() {
        return Trash.size();
    }

    public void emptyTrash() {
        Trash.clear();
    }

    public int getInboxCapacity() {
        return inboxCapacity;
    }
}