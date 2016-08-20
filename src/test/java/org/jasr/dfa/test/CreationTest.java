package org.jasr.dfa.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jasr.dfa.DFA;
import org.jasr.dfa.State;
import org.jasr.dfa.exceptions.DFACreationException;
import org.jasr.dfa.memento.StringsMemento;
import org.junit.Before;
import org.junit.Test;

public class CreationTest {

    StringsMemento     memento              = new StringsMemento();
    Set<Character>     alphabet             = new HashSet<>();
    Set<Character>     emptyAlphabet        = new HashSet<>();
    Set<State<String>> states               = new HashSet<State<String>>();
    Set<State<String>> noStartStates        = new HashSet<State<String>>();
    Set<State<String>> multipleStartStates  = new HashSet<State<String>>();
    Set<State<String>> oneStartAcceptState  = new HashSet<State<String>>();
    Set<State<String>> noAcceptStates       = new HashSet<State<String>>();
    Set<State<String>> multipleAcceptStates = new HashSet<State<String>>();
    Set<State<String>> emptyStates          = new HashSet<State<String>>();

    @Before
    public void setUp() {
        alphabet.add('a');
        alphabet.add('b');
        alphabet.add('c');

        State<String> startAcceptState = new State<String>("start and accept state", "No description", true, true);
        State<String> startState1 = new State<String>("start state 1", "No description", true, false);
        State<String> startState2 = new State<String>("start state 2", "No description", true, false);
        State<String> state1 = new State<String>("state 1", "No description", false, false);
        State<String> state2 = new State<String>("state 2", "No description", false, false);
        State<String> state3 = new State<String>("state 3", "No description", false, false);
        State<String> acceptState1 = new State<String>("accept state 1", "No description", false, true);
        State<String> acceptState2 = new State<String>("accept state 2", "No description", false, true);

        states.addAll(Arrays.asList(startState1, state1, state2, state3, acceptState1));
        noStartStates.addAll(Arrays.asList(state1, state2, state3, acceptState1));
        multipleStartStates.addAll(Arrays.asList(startState1, startState2, state1, acceptState1));
        oneStartAcceptState.addAll(Arrays.asList(startAcceptState));
        noAcceptStates.addAll(Arrays.asList(startState1, state1, state2, state3));
        multipleAcceptStates.addAll(Arrays.asList(startState1, state1, state2, state3, acceptState1, acceptState2));
    }

    @Test
    public void AcceptStateExistTest() {

        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, noAcceptStates);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
    }
    
    @Test
    public void MultipleAcceptStatesExistTest() {

        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, multipleAcceptStates);
        }
        catch(Exception e){
            fail(e.getMessage());
        }
    }
    
    @Test
    public void MultipleStartStatesExistTest() {

        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, multipleStartStates);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
    }

    @Test
    public void StatesExistTest() {
        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, null);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
        
        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, emptyStates);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
    }
    
    @Test
    public void AlphabetExistTest() {
        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, null, states);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
    }

    @Test
    public void MementoExistTest() {
        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(null, alphabet, states);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
    }

    @Test
    public void StartStateExistTest() {
        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, noStartStates);
            fail("No DFACreationException thrown");
        }
        catch(DFACreationException e){
        }
    }
    
    @Test
    public void OneStateTest() {
        try {
            DFA<Character, List<String>,String> dfa = new DFA<>(memento, alphabet, oneStartAcceptState);
        }
        catch(Exception e){
            fail("DFACreationException thrown");
        }
    }
}
