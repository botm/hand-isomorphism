package mbot.poker.handindex;

public final class HandIndexerState {
	
	public final int[] suitIndex = new int[HandIndexer.SUITS];
	public final int[] suitMultiplier = new int[HandIndexer.SUITS];
	public int round, permutationIndex, permutationMultiplier;
	public final int[] usedRanks = new int[HandIndexer.SUITS];
	
	public HandIndexerState() {
		permutationMultiplier = 1;
		for (int i=0; i<HandIndexer.SUITS; ++i)
			suitMultiplier[i] = 1;
	}
}
