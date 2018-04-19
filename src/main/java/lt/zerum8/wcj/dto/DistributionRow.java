package lt.zerum8.wcj.dto;

public class DistributionRow {

	private final String word;
	private final int frequencyCount;

	public DistributionRow(String word, int frequencyCount) {
		this.word = word;
		this.frequencyCount = frequencyCount;
	}

	public String getWord() {
		return word;
	}

	public int getFrequencyCount() {
		return frequencyCount;
	}

}
