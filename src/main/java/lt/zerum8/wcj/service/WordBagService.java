package lt.zerum8.wcj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lt.zerum8.wcj.dao.BagDao;
import lt.zerum8.wcj.dto.DistributionRow;
import lt.zerum8.wcj.entity.Bag;

@Service
public class WordBagService {

	private static final Logger LOG = LoggerFactory.getLogger(WordBagService.class);
	private static final String BAG_NAME_UNDEFINED = "undefined";

	private final BagDao bagDao;

	public WordBagService(BagDao bagDao) {
		this.bagDao = bagDao;
	}

	public Map<String, List<DistributionRow>> constructBagsMapForProvidedDistributionList(
			List<DistributionRow> distributionsList) {
		// take all required bags from database
		List<Bag> availableBags = bagDao.findAll();

		Map<String, List<DistributionRow>> bagsMap = new ConcurrentHashMap<>();
		// run through distributions list, group to bags by first character
		bagsMap = distributionsList.parallelStream().collect(
				Collectors.groupingByConcurrent(e -> getBagFromAvailableBagsForWord(availableBags, e.getWord())));
		LOG.debug("Done packing distributions to bags. Bags map size - {}", bagsMap.size());

		// ensure all bags taken
		for (Bag bag : availableBags) {
			// if bag is not present in the bagsMap, add it
			if (!bagsMap.containsKey(bag.getBagName())) {
				bagsMap.put(bag.getBagName(), new ArrayList<>());
			}
		}
		// return constructed bagsMap
		return bagsMap;
	}

	private String getBagFromAvailableBagsForWord(List<Bag> availableBags, String word) {
		// take first char
		char firstCharInLowerCase = word.toLowerCase().charAt(0);
		for (Bag bag : availableBags) {
			// if char is within bag's interval, take that bag
			if (Character.toLowerCase(bag.getCharFrom()) <= firstCharInLowerCase
					&& Character.toLowerCase(bag.getCharTill()) >= firstCharInLowerCase) {
				return bag.getBagName();
			}
		}
		LOG.warn("Bag was not found for word - {}", word);
		return BAG_NAME_UNDEFINED;
	}

}
