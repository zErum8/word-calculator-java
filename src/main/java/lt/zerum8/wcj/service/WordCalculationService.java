package lt.zerum8.wcj.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lt.zerum8.wcj.dto.DistributionRow;

@Service
public class WordCalculationService {

	private static final Logger LOG = LoggerFactory.getLogger(WordCalculationService.class);

	private final FilesService filesService;
	private final UtilityService utilityService;

	public WordCalculationService(FilesService filesService, UtilityService utilityService) {
		this.filesService = filesService;
		this.utilityService = utilityService;
	}

	public List<DistributionRow> processMultipartsToDistributions(List<MultipartFile> files) {
		long startTime = System.nanoTime();

		LOG.debug("Starting processing files. Files count - {}", files.size());
		// Run through all files, collect lines to list
		List<String> linesOfAllFiles = filesService.processMultipartFileToLines(files);
		LOG.debug("Done processing files to lines. Lines count - {}", linesOfAllFiles.size());

		// Run through all lines, collect words to list
		List<String> wordsOfAllFiles = linesOfAllFiles.parallelStream().map(utilityService::splitStringToWords)
				.flatMap(List::parallelStream).filter(w -> !StringUtils.isEmpty(w)).collect(Collectors.toList());
		LOG.debug("Done streaming lines to words. Words count - {}", wordsOfAllFiles.size());

		// Run through all words, compute frequency for every word
		Map<String, LongAdder> wordDistributionMap = new ConcurrentHashMap<>();
		wordsOfAllFiles.parallelStream()
				.forEach(n -> wordDistributionMap.computeIfAbsent(n, k -> new LongAdder()).increment());
		LOG.debug("Done streaming words to distribution map. Map size (unique words) - {}", wordDistributionMap.size());

		// Transform to distribution list
		List<DistributionRow> distributionsList = wordDistributionMap.entrySet().parallelStream()
				.map(e -> new DistributionRow(e.getKey(), e.getValue().intValue())).collect(Collectors.toList());
		LOG.debug("Process completed in {} ns.", System.nanoTime() - startTime);
		return distributionsList;
	}

}
