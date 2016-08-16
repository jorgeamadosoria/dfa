package org.jasr.dfa.memento;

public class StringMemento implements Memento<Character,String>{

    private StringBuilder state = new StringBuilder();
    
    @Override
    public void reset(Character c) {
        state = new StringBuilder();
    }

    @Override
    public void update(Character c) {
        state.append(c);
    }

    @Override
    public String current() {
        return state.toString();
    }

    @Override
    public void init() {
        reset(null);
    }

}
