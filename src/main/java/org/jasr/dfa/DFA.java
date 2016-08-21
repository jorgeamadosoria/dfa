package org.jasr.dfa;

import java.util.Set;

import org.jasr.dfa.exceptions.DFACreationException;
import org.jasr.dfa.exceptions.DFATransitionException;
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
        if (cs != null)
            for (T c : cs)
                add(from, to, c, transition);
        return this;
    }

    public DFA<T, T2, T3> add(State<T3> from, State<T3> to, T c, Transition transition) {
        if (alphabet.contains(c)) {
            LOGGER.debug("add transition of type {} from {} to {} with {}", transition.toString(), from.toString(), to.toString(),
                    c);
            transitions.put(from, to, c, transition);
        }
        else
            LOGGER.warn("attempted transition from {} to {} with {}. This symbol does not belong in the current alphabet.",
                    from.toString(), to.toString(), c);
        return this;
    }

    public boolean transition(T c) {
        if (alphabet.contains(c)) {
            LOGGER.debug("transit from {} with {}", current.toString(), c);
            State<T3> state = transitions.transition(current, c, memento);
            if (state != null)
                current = state;
            else {//the symbol exists, but there is no transition
                LOGGER.error("invalid transition with {} from {}.", c, current);
                throw new DFATransitionException("Invalid transition with character " + c);
            }
        }
        else
            LOGGER.warn("attempted to transition with {}. This symbol does not belong in the current alphabet.", c);
        return entryAccepted();
    }

    public void transition(T[] cs) {
        for (T c : cs)
            current = transitions.transition(current, c, memento);
    }

    private DFACreationException createDFAException(String msg) {
        DFACreationException e = new DFACreationException(msg);
        LOGGER.error("DFA Creation Exception", e);
        return e;
    }

    private State<T3> init(Memento<T, T2> memento, Set<T> alphabet, Set<State<T3>> states) {

        if (alphabet == null || alphabet.size() == 0) {
            throw createDFAException("The alphabet set must not be empty.");
        }

        if (memento == null) {
            throw createDFAException("The memento implementation is empty.");
        }

        if (states == null)
            throw createDFAException("The set of states is empty. At least one state is required.");

        State<T3> startState = null;
        int startStateCount = 0;
        boolean acceptState = false;
        for (State<T3> state : states) {
            if (state.isStartState()) {
                startState = state;
                startStateCount++;
            }
            acceptState = acceptState || state.isAcceptState();
        }

        if (startState == null || startStateCount != 1)
            throw createDFAException("There must be exactly one state marked as start state in the set (" + startStateCount
                    + " start states found).");
        if (!acceptState)
            throw createDFAException("There must be at least one accept state in the set.");
        return startState;
    }

    public DFA(Memento<T, T2> memento, Set<T> alphabet, Set<State<T3>> states) {
        super();
        this.current = this.startState = init(memento, alphabet, states);
        LOGGER.debug("DFA parameters validated");
        this.memento = memento;
        this.alphabet = alphabet;
        this.transitions = new Transitions<>(states);
        this.states = states;
        LOGGER.debug("DFA created with {} states and {} as start state", states.size(), startState.toString());
    }

    public void reset(){
        this.current = startState;
        this.memento.init();
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
