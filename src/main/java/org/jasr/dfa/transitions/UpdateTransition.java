package org.jasr.dfa.transitions;

import org.jasr.dfa.State;
import org.jasr.dfa.memento.Memento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTransition implements Transition {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTransition.class);
    @Override
    public <T, T2,T3> State<T3> apply(State<T3> from, State<T3> to, T c, Memento<T, T2> state) {
        LOGGER.debug("Applying transition between {} and {} with character {}",from,to,c);
        state.update(c);
        return to;
    }
    @Override
    public String toString() {
        return "UpdateTransition";
    }

}
