package org.jasr.dfa.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jasr.dfa.DFA;
import org.jasr.dfa.State;
import org.jasr.dfa.memento.StringsMemento;
import org.jasr.dfa.transitions.ResetTransition;
import org.jasr.dfa.transitions.Transition;
import org.jasr.dfa.transitions.UpdateTransition;
import org.junit.Assert;
import org.junit.Test;

public class SyllaberTest {

	private Set<Character> alphabet = new HashSet<>();
	private Set<State<String>> states = new HashSet<State<String>>();
	private DFA<Character, List<String>, String> dfa;

	private void init() {

		Character[] vocalesFuertes = { 'a', 'á', 'e', 'é', 'í', 'o', 'ó', 'ú' };
		Character[] consCombLR = { 'b', 'p', 'c', 'f', 'g' };
		Character[] consCombR = { 'd', 'k', 't' };
		Character[] haches = { 'h' };
		Character[] vocalesDebiles = { 'i', 'ü', 'u' };
		Character[] eles = { 'l' };
		Character[] consGen = { 'm', 'n', 'ñ', 'j', 'q', 's', 'v', 'w', 'x', 'y', 'z' };
		Character[] erres = { 'r' };

		alphabet.addAll(Arrays.asList(vocalesFuertes));
		alphabet.addAll(Arrays.asList(consCombLR));
		alphabet.addAll(Arrays.asList(consCombR));
		alphabet.addAll(Arrays.asList(haches));
		alphabet.addAll(Arrays.asList(vocalesDebiles));
		alphabet.addAll(Arrays.asList(eles));
		alphabet.addAll(Arrays.asList(consGen));
		alphabet.addAll(Arrays.asList(erres));

		State<String> qi = new State<>("qi", "start state", true, true);
		State<String> q0 = new State<>("q0", "consonantes de la sílaba antes de las vocales", false, true);
		State<String> q1 = new State<>("q1", "encontrada vocal fuerte", false, true);
		State<String> q2 = new State<>("q2", "encontrada vocal débil", false, true);
		State<String> q3 = new State<>("q3", "consonantes generales de la sílaba después de las vocales", false, true);
		State<String> q4 = new State<>("q4", "encontrada l después de las vocales", false, true);
		State<String> q5 = new State<>("q5", "encontrada r después de las vocales", false, true);

		states.add(qi);
		states.add(q0);
		states.add(q1);
		states.add(q2);
		states.add(q3);
		states.add(q4);
		states.add(q5);

		Transition update = new UpdateTransition();
		Transition reset = new ResetTransition();
		this.dfa = new DFA<>(new StringsMemento(), alphabet, states);
		// qi
		dfa.add(qi, q1, vocalesFuertes, update).add(qi, q0, consCombLR, update).add(qi, q0, consCombR, update).add(qi, q0, haches, update).add(qi, q2, vocalesDebiles, update).add(qi, q0, eles, update).add(qi, q0, consGen, update).add(qi, q0, erres, update)
				// q0
				.add(q0, q1, vocalesFuertes, update).add(q0, q0, consCombLR, update).add(q0, q0, consCombR, update).add(q0, q0, haches, update).add(q0, q2, vocalesDebiles, update).add(q0, q0, eles, update).add(q0, q0, consGen, update).add(q0, q0, erres, update)

				// q1
				.add(q1, q1, vocalesFuertes, reset).add(q1, q3, consCombLR, update).add(q1, q3, consCombR, update).add(q1, q1, haches, update).add(q1, q2, vocalesDebiles, update).add(q1, q4, eles, update).add(q1, q3, consGen, update).add(q1, q5, erres, update)

				// q2
				.add(q2, q1, vocalesFuertes, update).add(q2, q3, consCombLR, update).add(q2, q3, consCombR, update).add(q2, q2, haches, update).add(q2, q2, vocalesDebiles, update).add(q2, q4, eles, update).add(q2, q3, consGen, update).add(q2, q5, erres, update)

				// q3
				.add(q3, q1, vocalesFuertes, reset).add(q3, q0, consCombLR, reset).add(q3, q0, consCombR, reset).add(q3, q0, haches, reset).add(q3, q2, vocalesDebiles, reset).add(q3, q0, eles, reset).add(q3, q0, consGen, reset).add(q3, q0, erres, reset)

				// q4
				.add(q4, q1, vocalesFuertes, reset).add(q4, q3, consCombLR, update).add(q4, q0, consCombR, reset).add(q4, q0, haches, reset).add(q4, q2, vocalesDebiles, reset).add(q4, q3, eles, update).add(q4, q0, consGen, reset).add(q4, q0, erres, reset)

				// q5
				.add(q5, q1, vocalesFuertes, reset).add(q5, q3, consCombLR, update).add(q5, q3, consCombR, update).add(q5, q0, haches, reset).add(q5, q2, vocalesDebiles, reset).add(q5, q0, eles, reset).add(q5, q0, consGen, reset).add(q5, q3, erres, update);

	}

	public List<String> divide(String word) {

		if (!StringUtils.isAlpha(word))
			return Collections.emptyList();

		StringBuilder sb = new StringBuilder(word);
		sb.reverse();

		for (Character c : sb.toString().toCharArray())
			dfa.transition(c);
		List<String> syllabes = dfa.getMemento();
		Collections.reverse(syllabes);
		List<String> result = new ArrayList<>();
		String resultingSyllabe = "";
		for (String syllabe : syllabes) {

			resultingSyllabe = syllabe + resultingSyllabe;
			if (StringUtils.containsAny(resultingSyllabe, "aeioüuáéíóú")) {
				result.add(new StringBuilder(resultingSyllabe).reverse().toString());
				resultingSyllabe = "";
			}
		}
		dfa.reset();
		return result;
	}

	@Test
	public void resetTest() {
		init();
		Assert.assertArrayEquals(new String[] {"co","sas"},divide("cosas").toArray());
	}
}
