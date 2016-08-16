package org.jasr.dfa.memento;

import java.util.ArrayList;
import java.util.List;

public class StringsMemento implements Memento<Character,List<String>>{

    private List<StringBuilder> strings = new ArrayList<StringBuilder>();
    
    @Override
    public void reset(Character c) {
        strings.add(new StringBuilder());
        update(c);
    }

    @Override
    public void update(Character c) {
        if (strings.size() == 0)
            strings.add(new StringBuilder());
        strings.get(strings.size()-1).append(c);
    }

    @Override
    public List<String> current() {
        
        List<String> result = new ArrayList<>();
        for(StringBuilder sb:strings)
            result.add(sb.toString());
        
        return result;
    }

    @Override
    public void init() {
        strings.add(new StringBuilder());
    }

}
