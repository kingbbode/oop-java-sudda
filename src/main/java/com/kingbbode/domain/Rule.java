package com.kingbbode.domain;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by YG on 2017-07-13.
 */
public enum Rule {
    DDANG_GWANG_38((card1, card2) -> containsKingNumber(card1, card2, 3) && containsKingNumber(card1, card2, 8) ? Optional.of(Pedigree.DDANG_GWANG_38) : Optional.empty()),
    DDANG_GWANG_13((card1, card2) -> containsKingNumber(card1, card2, 1) && containsKingNumber(card1, card2, 3) ? Optional.of(Pedigree.DDANG_GWANG_38) : Optional.empty()),
    DDANG_GWANG_18((card1, card2) -> containsKingNumber(card1, card2, 1) && containsKingNumber(card1, card2, 8) ? Optional.of(Pedigree.DDANG_GWANG_18) : Optional.empty()),
    DDANG((card1, card2) -> card1.getNumber() == card2.getNumber() ? Pedigree.findByScore(Pedigree.DDANG1.getScore() + card1.getNumber() - 1) : Optional.empty()),
    ALLI((card1, card2) -> containsNumbers(card1, card2, 1, 2) ? Optional.of(Pedigree.ALLI) : Optional.empty()),
    DOGSA((card1, card2) -> containsNumbers(card1, card2, 1, 4) ? Optional.of(Pedigree.DOGSA) : Optional.empty()),
    BBING_9((card1, card2) -> containsNumbers(card1, card2, 1, 9) ? Optional.of(Pedigree.BBING_9) : Optional.empty()),
    BBING_10((card1, card2) -> containsNumbers(card1, card2, 1, 10) ? Optional.of(Pedigree.BBING_10) : Optional.empty()),
    JANGSA((card1, card2) -> containsNumbers(card1, card2, 4, 10) ? Optional.of(Pedigree.JANGSA) : Optional.empty()),
    SERUEG((card1, card2) -> containsNumbers(card1, card2, 4, 6) ? Optional.of(Pedigree.SERUEG) : Optional.empty()),
    DDANG_KILLER((card1, card2) -> containsKingNumber(card1, card2, 3) && containsKkeutNumber(card1, card2, 7) ? Optional.of(Pedigree.DDANG_KILLER) : Optional.empty()),
    AMHENG((card1, card2) -> containsKkeutNumber(card1, card2, 4) && containsKkeutNumber(card1, card2, 7) ? Optional.of(Pedigree.AMHENG) : Optional.empty()),
    KKEUT((card1, card2) -> Pedigree.findByScore(Pedigree.KKEUT0.getScore() + (card1.getNumber() + card2.getNumber()) % 10));

    Rule(RuleFormal rule) {
        this.rule = rule;
    }

    private RuleFormal rule;

    public static List<Pedigree> findAvailableResult(List<Card> deck) {
        Map<Pair<Card, Card>, Pedigree> resultMap = getCandidatePair(deck);
        List<Pedigree> specialCasePedigree = new ArrayList<>();
        for (Rule rule : Rule.values()) {
            for (Pair<Card, Card> pair : resultMap.keySet()) {
                Optional<Pedigree> result = rule.getResult(pair.getKey(), pair.getValue());
                if (!result.isPresent() || (!(result.get() == Pedigree.AMHENG || result.get() == Pedigree.DDANG_KILLER) && resultMap.get(pair).verifyVictoryConditions(result.get()))) {
                    continue;
                }
                resultMap.put(pair, result.get());
            }
        }
        specialCasePedigree.addAll(resultMap.values());
        return specialCasePedigree.stream().filter(pedigree -> pedigree != Pedigree.ERROR).distinct().collect(Collectors.toList());
    }

    private static Map<Pair<Card, Card>, Pedigree> getCandidatePair(List<Card> deck) {
        return IntStream.range(0, deck.size())
                .mapToObj((int i) -> IntStream.range(i + 1, deck.size()).mapToObj(j -> new Pair<>(deck.get(i), deck.get(j)))).flatMap(pairStream -> pairStream).collect(Collectors.toMap(k -> k, v -> Pedigree.ERROR));
    }

    public Optional<Pedigree> getResult(Card card1, Card card2) {
        return this.rule.apply(card1, card2);
    }

    private static boolean containsNumbers(Card card1, Card card2, int number1, int number2) {
        return (card1.getNumber() == number1 && card2.getNumber() == number2) || (card1.getNumber() == number2 && card2.getNumber() == number1);
    }

    private static boolean containsKingNumber(Card card1, Card card2, int number) {
        return containsKingAndNumber(card1, card2, true, number);
    }

    private static boolean containsKkeutNumber(Card card1, Card card2, int number) {
        return containsKingAndNumber(card1, card2, false, number);
    }

    private static boolean containsKingAndNumber(Card card1, Card card2, boolean isKing, int number) {
        return (card1.isKing() == isKing && card1.getNumber() == number) || (card2.isKing() == isKing && card2.getNumber() == number);
    }

    interface RuleFormal {
        Optional<Pedigree> apply(Card card1, Card card2);
    }
}
