package com.carehires.actions.agency;

import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;

import java.io.IOException;
import java.util.Properties;

public class GmailIMAPReader {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        try {
            //Connect to the GMail IMap server
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore();
            store.connect("imap.gmail.com", "niroshancarehires@gmail.com", "Lev3!n123");

            //Access the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            //Search for unread emails
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Content: " + message.getContent());
            }

            //close connection
            inbox.close(false);
            store.close();
        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
