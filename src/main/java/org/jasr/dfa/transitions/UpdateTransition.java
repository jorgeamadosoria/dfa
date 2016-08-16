package org.jasr.dfa.transitions;

import org.jasr.dfa.State;
import org.jasr.dfa.memento.Memento;

public class UpdateTransition implements Transition {

    @Override
    public <T, T2,T3> State<T3> apply(State<T3> from, State<T3> to, T c, Memento<T, T2> state) {
        state.update(c);
        return to;
    }

}
