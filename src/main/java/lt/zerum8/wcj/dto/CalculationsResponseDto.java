package lt.zerum8.wcj.dto;

import java.util.List;

public class CalculationsResponseDto {

	private String groupName;
	private List<DistributionRow> rows;

	public CalculationsResponseDto(String groupName, List<DistributionRow> rows) {
		this.groupName = groupName;
		this.rows = rows;
	}

	public CalculationsResponseDto() {
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<DistributionRow> getRows() {
		return rows;
	}

	public void setRows(List<DistributionRow> rows) {
		this.rows = rows;
	}

}
