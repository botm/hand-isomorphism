package mbot.poker.handindex;

import java.util.Random;

public class Test {
  private static void testRandom(HandIndexer indexer) {
    Random rnd = new Random();

    int totalCards = 0;
    for (int i = 0; i < indexer.rounds; ++i)
      totalCards += indexer.cardsPerRound[i];

    int[] cards = new int[totalCards];
    int[] cards2 = new int[totalCards];

    for (long n = 0; n < 10000000L; ++n) {
      long deadCardMask = 0;
      for (int i = 0; i < totalCards; ++i) {
        do {
          cards[i] = (int) (rnd.nextDouble() * HandIndexer.CARDS);
        } while (((1L << cards[i]) & deadCardMask) != 0);
        deadCardMask |= (1L << cards[i]);
      }
      long index = indexer.indexLast(cards);

      indexer.unindex(indexer.rounds - 1, index, cards2);
      long index2 = indexer.indexLast(cards2);
      assert index2 == index;
    }
  }

  public static void main(String[] args) {
    System.out.println("testing hand-isomorphism...");

    HandIndexer preflopIndexer = new HandIndexer(2);
    HandIndexer flopIndexer = new HandIndexer(2, 3);
    HandIndexer turnIndexer = new HandIndexer(2, 3, 1);
    HandIndexer riverIndexer = new HandIndexer(2, 3, 1, 1);

    assert preflopIndexer.roundSize[0] == 169;
    assert flopIndexer.roundSize[0] == 169;
    assert turnIndexer.roundSize[0] == 169;
    assert riverIndexer.roundSize[0] == 169;
    assert flopIndexer.roundSize[1] == 1286792;
    assert turnIndexer.roundSize[1] == 1286792;
    assert riverIndexer.roundSize[1] == 1286792;
    assert turnIndexer.roundSize[2] == 55190538;
    assert riverIndexer.roundSize[2] == 55190538;
    assert riverIndexer.roundSize[3] == 2428287420L;

    testRandom(flopIndexer);
    testRandom(turnIndexer);
    testRandom(riverIndexer);

    System.out.println("done!");
  }
}
