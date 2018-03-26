package me.javagyb.data.supplier.core.handlers.impl;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import me.javagyb.data.supplier.annotations.DS_Regx;
import me.javagyb.data.supplier.core.handlers.AnnotationHandler;

import java.util.List;
import java.util.Random;

/**
 * Created by javagyb on 2018/3/21.
 */
public class RegxHandler extends AnnotationHandler<DS_Regx,String> {


    private Automaton automaton;
    private Random random = new Random();

    @Override
    public String value(DS_Regx annotation) {
        this.automaton = new RegExp(annotation.value()).toAutomaton();
        return generate();
    }

    /**
     * Generates a random String that is guaranteed to match the regular expression passed to the constructor.
     */
    public String generate() {
        StringBuilder builder = new StringBuilder();
        generate(builder, automaton.getInitialState());
        return builder.toString();
    }

    private void generate(StringBuilder builder, State state) {
        List<Transition> transitions = state.getSortedTransitions(false);
        if (transitions.size() == 0) {
            assert state.isAccept();
            return;
        }
        int nroptions = state.isAccept() ? transitions.size() : transitions.size() - 1;
        int option = getRandomInt(0, nroptions, random);
        if (state.isAccept() && option == 0) {          // 0 is considered stop
            return;
        }
        // Moving on to next transition
        Transition transition = transitions.get(option - (state.isAccept() ? 1 : 0));
        appendChoice(builder, transition);
        generate(builder, transition.getDest());
    }

    private void appendChoice(StringBuilder builder, Transition transition) {
        char c = (char) getRandomInt(transition.getMin(), transition.getMax(), random);
        builder.append(c);
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Generates a random number within the given bounds.
     *
     * @param min The minimum number (inclusive).
     * @param max The maximum number (inclusive).
     * @param random The object used as the randomizer.
     * @return A random number in the given range.
     */
    static int getRandomInt(int min, int max, Random random) {
        // Use random.nextInt as it guarantees a uniform distribution
        int maxForRandom=max-min+1;
        return random.nextInt(maxForRandom) + min;
    }
}
