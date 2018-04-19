package lt.zerum8.wcj.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVWriter;
import lt.zerum8.wcj.exception.InternalWordCalculatorAppException;
import lt.zerum8.wcj.dto.DistributionRow;

@Service
public class FilesService {

	private static final Logger LOG = LoggerFactory.getLogger(FilesService.class);
	private static final String FILE_NAME_PATTERN = "data-for-bag-name-%BAG_NAME%.csv";

	public List<String> processMultipartFileToLines(List<MultipartFile> files) {
		return files.parallelStream().map(f -> {
			try {
				return new BufferedReader(new InputStreamReader(f.getInputStream())).lines()
						.collect(Collectors.toList());
			} catch (IOException e) {
				LOG.error("Exception occured during processing multipart file", e);
				throw new InternalWordCalculatorAppException();
			}
		}).flatMap(List::parallelStream).collect(Collectors.toList());
	}

	public void streamResultToResponse(OutputStream outputStream,
			Map<String, List<DistributionRow>> bagsMapWithDistribtutions) {
		try (ZipOutputStream zos = new ZipOutputStream(outputStream)) {
			for (Map.Entry<String, List<DistributionRow>> e : bagsMapWithDistribtutions.entrySet()) {
				ZipEntry entry = new ZipEntry(FILE_NAME_PATTERN.replace("%BAG_NAME%", e.getKey()));
				zos.putNextEntry(entry);

				@SuppressWarnings("resource")
				CSVWriter writer = new CSVWriter(new OutputStreamWriter(zos));
				for (DistributionRow row : e.getValue()) {
					writer.writeNext(new String[] { row.getWord(), String.valueOf(row.getFrequencyCount()) });
				}
				writer.flush();
				zos.closeEntry();
			}
		} catch (IOException e) {
			LOG.error("Exception occured while generating response:", e);
			throw new InternalWordCalculatorAppException();
		}
	}

}
