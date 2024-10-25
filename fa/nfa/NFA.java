package fa.nfa;

import fa.State;

import java.util.*;

public class NFA implements NFAInterface {


    LinkedHashSet<Character> sigma;
    LinkedHashSet<NFAState> states;
    String startState;
    LinkedHashSet<NFAState> finalStates;
    HashMap<Character, NFAState> transitions;
    LinkedHashMap<NFAState, ArrayList<Map<Character,NFAState>>> transitionTable;

    public NFA() {

        sigma = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        startState = "";
        finalStates = new LinkedHashSet<>();
        transitions = new HashMap<>();
        transitionTable = new LinkedHashMap<>();

    }
    @Override
    public boolean addState(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                return false;
            }
        }

        return states.add(new NFAState(name));
    }

    @Override
    public boolean setFinal(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                return finalStates.add(state);
            }
        }

        return false;
    }

    @Override
    public boolean setStart(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                startState = name;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        for (Character alnum : sigma) {
            if (alnum.equals(symbol)) {
                return;
            }
        }
        sigma.add(symbol);

    }

    @Override
    public boolean accepts(String s) {
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        for (State state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        for (State state : finalStates) {
            if (state.getName().equals(name)) {
                return state;
            }
        }

        return null;
    }

    @Override
    public boolean isFinal(String name) {
        for (NFAState state : finalStates) {
            if (state.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isStart(String name) {
        if (name.equals(startState)) {
            return true;
        }
        return false;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return null;
    }

    @Override
    public int maxCopies(String s) {
        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        return false;
    }

    @Override
    public boolean isDFA() {
        return false;
    }
}
