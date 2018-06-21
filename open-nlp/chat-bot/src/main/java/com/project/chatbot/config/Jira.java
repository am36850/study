package com.project.chatbot.config;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import java.util.ArrayList;
import java.util.List;

public class Jira {

    public static void main(String args[]) {

        net.rcarz.jiraclient.BasicCredentials creds = new net.rcarz.jiraclient.BasicCredentials("anmane", "May@2018");
        JiraClient jira = new JiraClient("https://jira.mediaocean.com", creds);

        try {
            /* Retrieve issue TEST-123 from JIRA. We'll get an exception if this fails. */
            Issue issue = jira.getIssue("IAPP-190737");

            /* Print the issue key. */
            issue.getStatus().getName();

            // Create a new issue.
            Issue newIssue = jira.createIssue("IAPP", "Support Ticket").field(Field.SUMMARY, "Test").field(Field.DESCRIPTION, "Test Summary").field(Field.REPORTER, "anmane").field(
                    Field.ASSIGNEE, getIterable("anmane")).field("components", getIterable("Prisma")).field("customfield_12922", getIterable("Support Item")).field("fixVersions",
                    getIterable("Engineering Support")).field("versions", getIterable("2018.3")).field("customfield_16932", getIterable("Prisma Adoption"))
                    //.field("Account Name" , "Test")
                    .execute();
            System.out.println(newIssue);

        }
        catch (JiraException ex) {
            System.err.println(ex.getMessage());

            if (ex.getCause() != null)
                System.err.println(ex.getCause().getMessage());
        }
        // return null;
    }

    private static List<String> getIterable(String value) {
        List<String> iterable = new ArrayList<>();
        iterable.add(value);
        return iterable;
    }

    public String checkJiraStatus(String createdJiras) {
        net.rcarz.jiraclient.BasicCredentials creds = new net.rcarz.jiraclient.BasicCredentials("anmane", "May@2018");
        JiraClient jira = new JiraClient("https://jira.mediaocean.com", creds);
        String response = null;
        try {
            /* Retrieve issue TEST-123 from JIRA. We'll get an exception if this fails. */
            Issue issue = jira.getIssue("IAPP-190737");

            /* Print the issue key. */
            response = issue.getStatus().getName();
            System.out.println(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}