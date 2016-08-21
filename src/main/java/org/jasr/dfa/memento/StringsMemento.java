package org.jasr.dfa.memento;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringsMemento implements Memento<Character,List<String>>{
    private static final Logger LOGGER = LoggerFactory.getLogger(StringsMemento.class);
    private List<StringBuilder> state = new ArrayList<StringBuilder>();
    
    @Override
    public void reset(Character c) {
        LOGGER.debug("Resetting memento with value: " + c);
        state = new ArrayList<StringBuilder>();
        update(c);
    }

    @Override
    public void update(Character c) {
        LOGGER.debug("Updating memento with value: " + c);
        if (state.size() == 0)
            state.add(new StringBuilder());
        state.get(state.size()-1).append(c);
    }

    @Override
    public List<String> current() {
        LOGGER.debug("Returning current memento value");
        List<String> result = new ArrayList<>();
        for(StringBuilder sb:state)
            result.add(sb.toString());
        
        return result;
    }

    @Override
    public void init() {
        state = new ArrayList<StringBuilder>();
    }

}
