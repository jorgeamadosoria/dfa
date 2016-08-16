package org.jasr.dfa.transitions;

import org.jasr.dfa.State;
import org.jasr.dfa.memento.Memento;

public class TransitionHolder<T3> {
    
    private State<T3>      to;
    private Transition transition;

    public TransitionHolder(State<T3> to, Transition transition) {
        this.to = to;
        this.transition = transition;
    }

    public <T, T2> State<T3> apply(State<T3> from, T c, Memento<T, T2> state) {
        return transition.apply(from, to, c, state);
    }

    public State<T3> getTo() {
        return to;
    }

    public Transition getTransition() {
        return transition;
    }
    
    
}
