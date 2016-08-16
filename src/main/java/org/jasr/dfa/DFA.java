package org.jasr.dfa;

import java.util.HashSet;
import java.util.Set;

import org.jasr.dfa.memento.Memento;
import org.jasr.dfa.transitions.Transition;
import org.jasr.dfa.transitions.Transitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DFA<T, T2, T3> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DFA.class);
    private Memento<T, T2>      memento;
    private Transitions<T, T3>  transitions;
    private Set<T>              alphabet;
    private Set<State<T3>>      states;
    private State<T3>           current;
    private State<T3>           startState;

    public boolean entryAccepted() {
        LOGGER.debug("Entry is accepted by state: " + current.toString());
        return current.isAcceptState();
    }

    public DFA<T, T2, T3> add(State<T3> from, State<T3> to, T[] cs, Transition transition) {
        for (T c : cs)
            add(from, to, c, transition);
        return this;
    }

    public DFA<T, T2, T3> add(State<T3> from, State<T3> to, T c, Transition transition) {
        LOGGER.debug("add transition of type {} from {} to {} with {}",transition.toString(),from.toString(),to.toString(),c);
        transitions.put(from, to, c, transition);
        return this;
    }

    public void transition(T c) {
        LOGGER.debug("transit from {} with {}",current.toString(),c);
        current = transitions.transition(current, c, memento);
    }

    public void transition(T[] cs) {
        for (T c : cs)
            current = transitions.transition(current, c, memento);
    }

    public DFA(Memento<T, T2> memento, Set<T> alphabet, State<T3> startState, Set<State<T3>> states) {
        super();
        this.memento = memento;
        this.alphabet = alphabet;
        this.transitions = new Transitions<>(states);
        if (states == null)
            this.states = new HashSet<>();
        this.states = states;
        this.states.add(startState);
        this.current = startState;
        this.startState = startState;
        LOGGER.debug("DFA created with {} states and {} as start state", states.size(), startState.toString());
    }

    public T2 getMemento() {
        return memento.current();
    }

    public Set<T> getAlphabet() {
        return alphabet;
    }

    public Set<State<T3>> getStates() {
        return states;
    }

    public State<T3> getCurrent() {
        return current;
    }

    public State<T3> getStartState() {
        return startState;
    }

}
