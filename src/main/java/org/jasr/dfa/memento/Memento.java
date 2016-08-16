package org.jasr.dfa.memento;

public interface Memento<T,T2> {
    public void reset(T c);
    public void update(T c);
    public T2 current();
    public void init();
}
