package lt.zerum8.wcj.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import lt.zerum8.wcj.dto.DistributionRow;

public class WordCalculationServiceTest {

	private FilesService filesService;
	private UtilityService utilityService;

	@Before
	public void setupMock() {
		filesService = Mockito.mock(FilesService.class);
		utilityService = new UtilityService("\\W+");
	}

	@Test
	public void whenDistributionListContainsRecords_andAvailableBagsAvailable_andRecordsAreInDifferentBags_thenAllBagsFilledCorrect()
			throws IOException {
		// prepare
		when(filesService.processMultipartFileToLines(any())).thenReturn(Arrays.asList("hello world", "bye bye"));

		// act
		WordCalculationService wordBagService = new WordCalculationService(filesService, utilityService);
		List<DistributionRow> distributionRows = wordBagService.processMultipartsToDistributions(Arrays.asList());

		// assert
		Collections.sort(distributionRows, Comparator.comparing(DistributionRow::getWord));
		assertEquals(3, distributionRows.size());
		assertEquals("bye", distributionRows.get(0).getWord());
		assertEquals(2, distributionRows.get(0).getFrequencyCount());
		assertEquals("hello", distributionRows.get(1).getWord());
		assertEquals(1, distributionRows.get(1).getFrequencyCount());
		assertEquals("world", distributionRows.get(2).getWord());
		assertEquals(1, distributionRows.get(2).getFrequencyCount());
	}

}
