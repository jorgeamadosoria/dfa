package org.jasr.dfa;

public class State<T> {

    private String  nombre;
    private T       content;
    private boolean startState;
    private boolean acceptState;

    public State(String nombre, T content, boolean startState, boolean acceptState) {
        super();
        this.nombre = nombre;
        this.content = content;
        this.startState = startState;
        this.acceptState = acceptState;
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

}
