package org.jasr.dfa.transitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jasr.dfa.State;
import org.jasr.dfa.memento.Memento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transitions<T, T3> {
    private static final Logger                          LOGGER      = LoggerFactory.getLogger(Transitions.class);
    private Map<State<T3>, Map<T, TransitionHolder<T3>>> transitions = new HashMap<>();

    public Transitions(Set<State<T3>> states) {
        LOGGER.debug("creation of Transitions object");
        for (State<T3> st : states) {
            transitions.put(st, new HashMap<>());
        }

    }

    public void put(State<T3> from, State<T3> to, T c, Transition transition) {
        LOGGER.debug("adding transition: " + from.toString() + " " + to.toString() + " " + transition.toString());
        transitions.get(from).put(c, new TransitionHolder<>(to, transition));
    }

    public boolean containsTransition(State<T3> from, State<T3> to, T c) {
        boolean result = transitions.get(from).get(c).getTo().equals(to);
        return result;
    }

    public <T2> State<T3> transition(State<T3> from, T c, Memento<T, T2> state) {
        if (transitions.containsKey(from))
            if (transitions.get(from).containsKey(c)) {
                State<T3> to = transitions.get(from).get(c).apply(from, c, state);
                LOGGER.debug("transit: " + from.toString() + " to " + to.toString() + " with " + c.toString());
                return to;
            }
        return null;
    }

}
