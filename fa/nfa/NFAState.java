package fa.nfa;

import fa.State;

public class NFAState extends State {
    /**
     * The state label.
     * It should be a unique name set by
     * the concrete class constructor.
     * @author elenasherman
     */
    private String name;

    public NFAState(String name) {
        super(name);

    }


    /**
     * getter for the string label
     * @return returns the state label.
     */
    public String getName(){
        return super.getName();
    }

    @Override
    public String toString(){
        return super.toString();
    }

}
