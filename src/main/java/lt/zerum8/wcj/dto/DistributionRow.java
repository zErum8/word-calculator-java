package lt.zerum8.wcj.dto;

public class DistributionRow {

	private String word;
	private int frequencyCount;

	public DistributionRow(String word, int frequencyCount) {
		this.word = word;
		this.frequencyCount = frequencyCount;
	}

	public DistributionRow() {
		super();
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequencyCount() {
		return frequencyCount;
	}

	public void setFrequencyCount(int frequencyCount) {
		this.frequencyCount = frequencyCount;
	}

}
