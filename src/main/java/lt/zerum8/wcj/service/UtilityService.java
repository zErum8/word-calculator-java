package lt.zerum8.wcj.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UtilityService {

	private static final Logger LOG = LoggerFactory.getLogger(UtilityService.class);

	private final String wordSplittingPattern;

	public UtilityService(@Value("${lt.zerum8.wcj.config.word-splitting-pattern}") String wordSplittingPattern) {
		LOG.debug("Using wordSplittingPattern - {}", wordSplittingPattern);
		this.wordSplittingPattern = wordSplittingPattern;
	}

	public List<String> splitStringToWords(String content) {
		if (StringUtils.isEmpty(content)) {
			return Arrays.asList();
		}
		return Arrays.asList(content.split(wordSplittingPattern));
	}
}
