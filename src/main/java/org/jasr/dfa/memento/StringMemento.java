package org.jasr.dfa.memento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringMemento implements Memento<Character,String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(StringMemento.class);
    private StringBuilder state = new StringBuilder();
    
    @Override
    public void reset(Character c) {
        LOGGER.debug("Resetting memento with value: " + c);
        state = new StringBuilder();
    }

    @Override
    public void update(Character c) {
        LOGGER.debug("Updating memento with value: " + c);
        state.append(c);
    }

    @Override
    public String current() {
        LOGGER.debug("Returning current memento value of: " + state.toString());
        return state.toString();
    }

    @Override
    public void init() {
        LOGGER.debug("Initializing memento with a null value");
        reset(null);
    }

}
