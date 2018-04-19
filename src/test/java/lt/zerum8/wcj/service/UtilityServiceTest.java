package lt.zerum8.wcj.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class UtilityServiceTest {

	@Test
	public void whenFileIsMultiline_allWordsCountedCorrectly() throws IOException {
		// prepare
		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/multiline_file.txt"), "UTF-8");

		// act
		List<String> wordsList = new UtilityService("\\W+").splitStringToWords(xml);

		// assert
		Assert.assertEquals("Hello", wordsList.get(0));
		Assert.assertEquals("my", wordsList.get(1));
		Assert.assertEquals("name", wordsList.get(2));
		Assert.assertEquals("is", wordsList.get(3));
		Assert.assertEquals("Robert", wordsList.get(4));
		Assert.assertEquals("How", wordsList.get(5));
		Assert.assertEquals("are", wordsList.get(6));
		Assert.assertEquals("you", wordsList.get(7));
		Assert.assertEquals("today", wordsList.get(8));
		Assert.assertEquals("hi", wordsList.get(9));
	}

	@Test
	public void whenContentIsEmpty_thenReturnEmptyResultList() throws IOException {
		// act
		List<String> wordsList = new UtilityService("\\W+").splitStringToWords(null);

		// assert
		Assert.assertEquals(0, wordsList.size());
	}

}
