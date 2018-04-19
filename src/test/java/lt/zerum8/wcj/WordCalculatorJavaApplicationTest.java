package lt.zerum8.wcj;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WordCalculatorJavaApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@SuppressWarnings("deprecation")
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "text.txt", "text/plain",
				"I love testing haha".getBytes());
		MvcResult mvcResult = mockMvc.perform(fileUpload("/process-files").file(multipartFile))
				.andExpect(status().isOk()).andReturn();

		List<String> fileNames = new ArrayList<>();
		byte[] bytes = mvcResult.getResponse().getContentAsByteArray();
		try (ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(bytes))) {
			ZipEntry entry = null;
			while ((entry = zipStream.getNextEntry()) != null) {
				fileNames.add(entry.getName());
				zipStream.closeEntry();
			}
		}

		Collections.sort(fileNames);
		assertEquals(4, fileNames.size());
		assertEquals("data-for-bag-name-AG.csv", fileNames.get(0));
		assertEquals("data-for-bag-name-HN.csv", fileNames.get(1));
		assertEquals("data-for-bag-name-OU.csv", fileNames.get(2));
		assertEquals("data-for-bag-name-VZ.csv", fileNames.get(3));
	}

}
