package lt.zerum8.wcj.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lt.zerum8.wcj.service.FilesService;
import lt.zerum8.wcj.service.WordBagService;
import lt.zerum8.wcj.service.WordCalculationService;
import lt.zerum8.wcj.dto.CalculationsResponseDto;
import lt.zerum8.wcj.dto.DistributionRow;

@RestController
public class WordCalulatorRestController {

	@Autowired
	private WordCalculationService wordCalculationService;
	@Autowired
	private FilesService filesService;
	@Autowired
	private WordBagService wordBagService;

	@PostMapping(value = "/process-files", produces = "application/zip")
	public void processMultipleFiles(@RequestParam("file") List<MultipartFile> files, OutputStream outputStream) {
		Map<String, List<DistributionRow>> bagsMapWithDistribtutions = processAllFilesAndReturnFinalResult(files);
		filesService.streamResultToResponse(outputStream, bagsMapWithDistribtutions);
	}

	@PostMapping(value = "/process-files-json")
	public List<CalculationsResponseDto> processMultipleFiles(HttpServletRequest request) {
		// Resolve multiparts manually because Spring want all files to be with the name
		// file.But requests from FE comes with the following format: file[0] file[1]..
		List<MultipartFile> files = new ArrayList<>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Set<Entry<String, MultipartFile>> setOfFiles = multipartRequest.getFileMap().entrySet();
		for (Entry<String, MultipartFile> entry : setOfFiles) {
			MultipartFile multipartFile = (MultipartFile) entry.getValue();
			files.add(multipartFile);
		}

		Map<String, List<DistributionRow>> bagsMapWithDistribtutions = processAllFilesAndReturnFinalResult(files);
		return bagsMapWithDistribtutions.entrySet().stream()
				.map(e -> new CalculationsResponseDto(e.getKey(), e.getValue())).collect(Collectors.toList());
	}

	private Map<String, List<DistributionRow>> processAllFilesAndReturnFinalResult(List<MultipartFile> files) {
		List<DistributionRow> distributionsList = wordCalculationService.processMultipartsToDistributions(files);
		return wordBagService.constructBagsMapForProvidedDistributionList(distributionsList);
	}

}