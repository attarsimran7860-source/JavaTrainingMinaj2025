package com.day2.lab2;

import java.util.*;

public enum DocumentState {

    DRAFT {
        @Override
        public Set<DocumentState> getAllowedTransitions() {
            return Collections.singleton(REVIEW); 
        }
    },
    REVIEW {
        @Override
        public Set<DocumentState> getAllowedTransitions() {
            Set<DocumentState> allowed = new HashSet<>();
            allowed.add(APPROVED);
            allowed.add(DRAFT); 
            return Collections.unmodifiableSet(allowed); 
        }
    },
    APPROVED {
        @Override
        public Set<DocumentState> getAllowedTransitions() {
            return Collections.singleton(PUBLISHED); 
        }
    },
    PUBLISHED {
        @Override
        public Set<DocumentState> getAllowedTransitions() {
            return Collections.singleton(ARCHIVED); 
        }
    },
    ARCHIVED {
        @Override
        public Set<DocumentState> getAllowedTransitions() {
            return Collections.emptySet(); 
        }
    };

   
    public abstract Set<DocumentState> getAllowedTransitions();

  
    public boolean canTransitionTo(DocumentState nextState) {
        return getAllowedTransitions().contains(nextState);
    }
}