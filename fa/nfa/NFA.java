package fa.nfa;

import fa.State;

import java.util.*;

public class NFA implements NFAInterface {


    LinkedHashSet<Character> sigma;
    LinkedHashSet<NFAState> states;
    String startState;
    LinkedHashSet<NFAState> finalStates;
    Map<NFAState, Map<Character, Set<NFAState>>> transitionTable;

    public NFA() {

        sigma = new LinkedHashSet<>();
        sigma.add('e');
        states = new LinkedHashSet<>();
        startState = "";
        finalStates = new LinkedHashSet<>();
        transitionTable = new HashMap<>();

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
        for (int i = 0; i < s.length(); i++) {
            if (!sigma.contains(s.charAt(i))) {
                return false;
            }
        }

        // get all e closures first
        Set<NFAState> currentStates = new HashSet<>();
        currentStates.add((NFAState) getState(startState));
        currentStates.addAll(eClosure((NFAState) getState(startState)));

        if (startState.equals("")) {
            return false;
        }


        for (int i = 0; i < s.length(); i++) {
            Set<NFAState> nextStates = new HashSet<>();

            for (NFAState current : currentStates) {
                Map<Character, Set<NFAState>> transitions = transitionTable.get(current);
                if (transitions != null && transitions.containsKey(s.charAt(i))) {
                    Set<NFAState> destStates = transitions.get(s.charAt(i));
                    for (NFAState dest : destStates) {
                        nextStates.add(dest);
                        nextStates.addAll(eClosure(dest));
                    }
                }
            }

            if (nextStates.isEmpty()) {
                return false;
            }
            currentStates = nextStates;
        }

        for (NFAState state : currentStates) {
            if (finalStates.contains(state)) {
                return true;
            }
        }

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
        Set<NFAState> closure = new HashSet<>();
        Stack<NFAState> stack = new Stack<>();
        closure.add(s);
        stack.push(s);

        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            Map<Character, Set<NFAState>> transitions = transitionTable.get(current);
            System.out.println("e closure trans " + transitions);
            System.out.println("current " + current);

            if (transitions != null && transitions.containsKey('e')) {
                Set<NFAState> epsilonStates = transitions.get('e');
                for (NFAState state : epsilonStates) {
                    if (closure.add(state)) {  // returns true if state was added
                        stack.push(state);
                    }
                }
            }
        }

        System.out.println("the closure for " + s  + closure);
        return closure;
    }

    @Override
    public int maxCopies(String s) {
        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {

        if (!sigma.contains(onSymb)){
            return false;
        }

        NFAState from = null;
        for (NFAState state : states) {
            if (state.getName().equals(fromState)) {
                from = state;
                break;
            }
        }

        if (from == null){
            return false;
        }

        // convert toStates strings to NFAState objects
        Set<NFAState> toStateSet = new HashSet<>();
        for (String stateName : toStates) {
            for (NFAState state : states) {
                if (state.getName().equals(stateName)) {
                    toStateSet.add(state);
                    break;
                }
            }
        }
        // if none of the to states exist then just return false
        if(toStateSet.isEmpty()){
            return false;
        }


        if (!transitionTable.containsKey(from)) {
            transitionTable.put(from, new HashMap<>());
        }

        if (!transitionTable.get(from).containsKey(onSymb)) {
            transitionTable.get(from).put(onSymb, new HashSet<>());
        }

        // add all destination states
        transitionTable.get(from).get(onSymb).addAll(toStateSet);
        return true;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        return false;
    }


    @Override
    public boolean isDFA() {
        return false;
    }
}
