package org.jasr.dfa.test;

import org.jasr.dfa.memento.StringsMemento;
import org.junit.Assert;
import org.junit.Test;

public class StringsMementoTest {

    StringsMemento memento = new StringsMemento();

    @Test
    public void resetTest() {
        String[] csa = { "a" };
        String[] csb = { "b" };
        String[] csab = {"a", "b" };
        String[] empty = {};
        memento = new StringsMemento();
        memento.update('a');
        Assert.assertArrayEquals(csa, memento.current().toArray());
        memento.reset('b');
        Assert.assertArrayEquals(csab, memento.current().toArray());
        memento.init();
        Assert.assertArrayEquals(empty, memento.current().toArray());
    }
}
