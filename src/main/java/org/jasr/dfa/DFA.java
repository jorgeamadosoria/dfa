package org.jasr.dfa;

import java.util.Set;

import org.jasr.dfa.memento.Memento;
import org.jasr.dfa.transitions.Transition;
import org.jasr.dfa.transitions.Transitions;

public class DFA<T, T2,T3> {

    private Memento<T, T2> memento;
    private Transitions<T,T3> transitions;
    private Set<T>         alphabet;
    private Set<State<T3>> states;
    private State<T3>      current;
    private State<T3>      startState;

    public boolean entryAccepted() {
        return current.isAcceptState();
    }

    public DFA<T, T2, T3> add(State<T3> from, State<T3> to, T[] cs, Transition transition) {
        for (T c : cs)
            transitions.put(from, to, c, transition);
        return this;
    }

    public DFA<T, T2, T3> add(State<T3> from, State<T3> to, T c, Transition transition) {
        transitions.put(from, to, c, transition);
        return this;
    }

    public void transition(T c) {
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
        this.states = states;
        this.states.add(startState);
        this.current = startState;
        this.startState = startState;
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
