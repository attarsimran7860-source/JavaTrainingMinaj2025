package com.day2.lab2;

public class Document {

	private DocumentState currentState;
    private String name; 

    public Document(String name) {
        this.name = name;
        this.currentState = DocumentState.DRAFT; 
        System.out.println("Document '" + this.name + "' created in state: " + currentState);
    }

    public DocumentState getCurrentState() {
        return currentState;
    }

   
    public boolean transitionTo(DocumentState nextState) {
        System.out.print("Attempting to transition Document '" + name + "' from " + currentState + " to " + nextState + "... ");

        if (currentState.canTransitionTo(nextState)) {
            this.currentState = nextState;
            System.out.println("SUCCESS! New state: " + currentState);
            return true;
        } else {
            System.out.println("FAILED! Invalid transition from " + currentState + " to " + nextState + ".");
            System.out.println("Allowed transitions from " + currentState + ": " + currentState.getAllowedTransitions());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Document '" + name + "' [Current State: " + currentState + "]";
    }
}