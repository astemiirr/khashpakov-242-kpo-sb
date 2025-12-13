package analysis;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import analysis.repositories.StatisticRepository;
import analysis.services.AnalysisService;
import analysis.services.FileService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class AnalysisServiceTest {
    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private StatisticRepository statisticRepository;

    @Autowired
    @InjectMocks
    private FileService fileService;

    @Autowired
    @InjectMocks
    private AnalysisService analysisService;

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateWordCloud_Success() {
        byte[] mockImage = new byte[]{0x1, 0x2, 0x3, 0x4, 0x5};
        doReturn("Test text").when(restTemplate).getForObject(any(String.class), Mockito.any());
        var url = String.format(
                "https://quickchart.io/wordcloud?text=%s&format=png&width=800&height=600",
                URLEncoder.encode("Test text", StandardCharsets.UTF_8)
        );

        doReturn(new ResponseEntity<byte[]>(mockImage, HttpStatusCode.valueOf(200)))
                .when(restTemplate)
                .getForEntity(eq(url), Mockito.any());

        byte[] result = analysisService.generateWordCloud(1);

        Assertions.assertArrayEquals(mockImage, result);
    }

    @Test
    void analyzeFile_Success() {
        when(statisticRepository.getByFileId(any(Integer.class))).thenReturn(Optional.empty());
        when(statisticRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        String text = "Test text\n\nPar2\n    \nPar 3";
        doReturn(text).when(restTemplate).getForObject(any(String.class), Mockito.any());

        var result = analysisService.analyze(0);

        assertAll(
                () -> assertEquals(3, result.getParagraphs()),
                () -> assertEquals(5, result.getWords()),
                () -> assertEquals(26, result.getSymbols())
        );
    }
}