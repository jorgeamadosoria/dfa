package org.jasr.dfa.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jasr.dfa.DFA;
import org.jasr.dfa.State;
import org.jasr.dfa.memento.StringMemento;
import org.jasr.dfa.transitions.UpdateTransition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransitionTest {
    StringMemento                  memento  = new StringMemento();
    Set<Character>                 alphabet = new HashSet<>();
    Set<State<String>>             states   = new HashSet<State<String>>();

    DFA<Character, String, String> dfa      = null;

    @Before
    public void setUp() {
        alphabet.add('a');
        alphabet.add('b');
        alphabet.add('c');

        State<String> startState1 = new State<String>("start state 1", "No description", true, false);
        State<String> state1 = new State<String>("state 1", "No description", false, false);
        State<String> acceptState1 = new State<String>("accept state 1", "No description", false, true);

        states.addAll(Arrays.asList(startState1, state1, acceptState1));
        dfa = new DFA<>(memento, alphabet, states);
        dfa.add(startState1, state1, 'c', new UpdateTransition());
        dfa.add(startState1, acceptState1, 'a', new UpdateTransition());
        dfa.add(state1, acceptState1, 'a', new UpdateTransition());
        dfa.add(acceptState1, state1, 'c', new UpdateTransition());
        dfa.add(acceptState1, acceptState1, new Character[] { 'a', 'b' }, new UpdateTransition());
    }

    @Test
    public void validTransition() {
        dfa.transition('c');
        Assert.assertEquals(dfa.getCurrent().getNombre(), "state 1");
    }

    @Test
    public void invalidTransition() {
        try {
            dfa.transition('b');
            fail("DFATransitionException not thrown");
        }
        catch (Exception e) {
        }
    }

    @Test
    public void invalidSymbolTransition() {
            State<String> before = dfa.getCurrent();
            dfa.transition('d');
            State<String> after = dfa.getCurrent();
            Assert.assertEquals(before,after);
    }

    @Test
    public void acceptedTransitions() {
        dfa.transition('a');
        Assert.assertEquals(dfa.entryAccepted(), true);
        dfa.transition(new Character[]{'a','a'});
        Assert.assertEquals(dfa.entryAccepted(), true);
        dfa.transition('c');
        Assert.assertEquals(dfa.entryAccepted(), false);
    }

}
