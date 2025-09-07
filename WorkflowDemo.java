package com.day2.lab2;

public class WorkflowDemo {

	public static void main(String[] args) {
        System.out.println("--- Document Workflow Demonstration ---");

        Document doc1 = new Document("Project Proposal");

        doc1.transitionTo(DocumentState.REVIEW);
        doc1.transitionTo(DocumentState.APPROVED);
        doc1.transitionTo(DocumentState.PUBLISHED);
        doc1.transitionTo(DocumentState.ARCHIVED);

        System.out.println("\n--- Testing Invalid Transitions ---");
        Document doc2 = new Document("Budget Report");

        doc2.transitionTo(DocumentState.PUBLISHED);

        doc2.transitionTo(DocumentState.REVIEW);

        doc2.transitionTo(DocumentState.PUBLISHED);

        doc2.transitionTo(DocumentState.DRAFT);
        System.out.println("Current state of doc2 after rejection: " + doc2.getCurrentState());

        doc2.transitionTo(DocumentState.REVIEW);
        doc2.transitionTo(DocumentState.APPROVED);

       
        doc2.transitionTo(DocumentState.ARCHIVED);

        System.out.println("\n--- Final States ---");
        System.out.println(doc1);
        System.out.println(doc2);


        System.out.println("\n--- Extensibility Example: Adding ON_HOLD State ---");
        
        System.out.println("Adding 'ON_HOLD' state would involve modifying the DocumentState enum.");
        System.out.println("No changes would be required in the Document class or WorkflowDemo class to support the new state's transitions, only to the enum itself.");
    }
}
