package org.jasr.dfa.test;

import org.jasr.dfa.memento.StringsMemento;
import org.junit.Assert;
import org.junit.Test;

public class StringMementoTest {

    StringsMemento memento = new StringsMemento();

    @Test
    public void resetTest() {
        String[] csa = { "a" };
        String[] csb = { "b" };
        memento = new StringsMemento();
        memento.update('a');
        Assert.assertArrayEquals(csa, memento.current().toArray());
        memento.reset('b');
        Assert.assertArrayEquals(csb, memento.current().toArray());
    }
}
