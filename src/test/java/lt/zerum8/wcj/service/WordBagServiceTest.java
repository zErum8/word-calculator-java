package lt.zerum8.wcj.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import lt.zerum8.wcj.dao.BagDao;
import lt.zerum8.wcj.dto.DistributionRow;
import lt.zerum8.wcj.entity.Bag;

public class WordBagServiceTest {

	private BagDao bagDao;

	@Before
	public void setupMock() {
		bagDao = Mockito.mock(BagDao.class);
	}

	@Test
	public void whenDistributionListIsEmpty_andAvailableBagsAvailable_resultContainsAvailableBagsWithEmptyValues()
			throws IOException {
		// prepare
		List<Bag> bags = Arrays.asList(new Bag("AB", 'A', 'B'));
		when(bagDao.findAll()).thenReturn(bags);

		// act
		WordBagService wordBagService = new WordBagService(bagDao);
		Map<String, List<DistributionRow>> bagsMap = wordBagService
				.constructBagsMapForProvidedDistributionList(new ArrayList<>());

		// assert
		assertEquals(1, bagsMap.size());
		assertEquals(0, bagsMap.get("AB").size());
	}

	@Test
	public void whenDistributionListContainsRecord_andAvailableBagsAvailable_andRecordIsNotInABag_thenTwoBags_availableAndUndefined()
			throws IOException {
		// prepare
		List<Bag> bags = Arrays.asList(new Bag("AB", 'A', 'B'));
		when(bagDao.findAll()).thenReturn(bags);

		// act
		WordBagService wordBagService = new WordBagService(bagDao);
		Map<String, List<DistributionRow>> bagsMap = wordBagService
				.constructBagsMapForProvidedDistributionList(Arrays.asList(new DistributionRow("tom", 3)));

		// assert
		assertEquals(2, bagsMap.size());
		assertEquals(0, bagsMap.get("AB").size());
		assertEquals(1, bagsMap.get("undefined").size());
		assertEquals("tom", bagsMap.get("undefined").get(0).getWord());
	}

	@Test
	public void whenDistributionListContainsRecord_andAvailableBagsAvailable_andRecordIsABag_thenOneBagFilled()
			throws IOException {
		// prepare
		List<Bag> bags = Arrays.asList(new Bag("AB", 'A', 'B'));
		when(bagDao.findAll()).thenReturn(bags);

		// act
		WordBagService wordBagService = new WordBagService(bagDao);
		Map<String, List<DistributionRow>> bagsMap = wordBagService
				.constructBagsMapForProvidedDistributionList(Arrays.asList(new DistributionRow("Atom", 3)));

		// assert
		assertEquals(1, bagsMap.size());
		assertEquals(1, bagsMap.get("AB").size());
		assertEquals("Atom", bagsMap.get("AB").get(0).getWord());
	}

	@Test
	public void whenDistributionListContainsRecords_andAvailableBagsAvailable_andRecordsAreInDifferentBags_thenAllBagsFilledCorrect()
			throws IOException {
		// prepare
		List<Bag> bags = Arrays.asList(new Bag("AB", 'A', 'B'), new Bag("CD", 'C', 'D'));
		when(bagDao.findAll()).thenReturn(bags);

		// act
		WordBagService wordBagService = new WordBagService(bagDao);
		Map<String, List<DistributionRow>> bagsMap = wordBagService
				.constructBagsMapForProvidedDistributionList(Arrays.asList(new DistributionRow("Atom", 3),
						new DistributionRow("Batom", 3), new DistributionRow("CCC", 3)));

		// assert
		assertEquals(2, bagsMap.size());
		assertEquals(2, bagsMap.get("AB").size());
		assertEquals(1, bagsMap.get("CD").size());
		assertEquals("CCC", bagsMap.get("CD").get(0).getWord());
	}

}
