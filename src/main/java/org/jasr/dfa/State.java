package org.jasr.dfa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class State<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(State.class);
    private String              nombre;
    private T                   content;
    private boolean             startState;
    private boolean             acceptState;

    public State(String nombre, T content, boolean startState, boolean acceptState) {
        super();
        this.nombre = nombre;
        this.content = content;
        this.startState = startState;
        this.acceptState = acceptState;
        LOGGER.debug("Creating State: name {} start {} accept {}",nombre,startState,acceptState);
    }

    public String getNombre() {
        return nombre;
    }

    public T getContent() {
        return content;
    }

    public boolean isStartState() {
        return startState;
    }

    public boolean isAcceptState() {
        return acceptState;
    }

    @Override
    public String toString() {
        return "{name:" + nombre + ", start:" + startState + ", acceptState:" + acceptState + "}";
    }

}
