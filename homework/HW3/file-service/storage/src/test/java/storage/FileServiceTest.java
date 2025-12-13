package storage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.web.multipart.MultipartFile;
import storage.domains.FileEntity;
import storage.repositories.FileRepository;
import storage.services.FileService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FileServiceTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @MockitoSpyBean
    FileRepository fileRepository;

    @MockitoSpyBean
    private FileService fileService;

    @Test
    @DisplayName("Успешное сохранение тестового файла")
    void saveFile_Success() throws Exception {
        var mockFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "TEst text".getBytes());


        Mockito.doNothing().when(fileService).save(any(Path.class), any(MultipartFile.class));

        var fileEntity = fileService.saveFile(mockFile);

        assertAll(
                () -> assertNotNull(fileEntity.getId()),
                () -> assertEquals(fileEntity.getHash(), fileService.getFileHash(fileEntity.getId())),
                () -> assertEquals(fileEntity.getName(), mockFile.getOriginalFilename()),
                () -> assertTrue(fileEntity.getPath().startsWith("location/content"))
        );
    }

    @Test
    @DisplayName("Исключение при сохранении тестового файла")
    void saveFile_Failure() throws Exception {
        var mockFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "TEst text".getBytes());

        Mockito.doThrow(new IOException()).when(fileService).save(any(Path.class), any(MultipartFile.class));

        var oldFilesCount = fileRepository.findAll().size();

        Assertions.assertThrows(IOException.class, () -> {fileService.saveFile(mockFile);});

        assertEquals(oldFilesCount, fileRepository.findAll().size());
    }

    @Test
    @DisplayName("Исключение при сохранении тестового файла")
    void loadFile_Success() throws Exception {
        var testEntity = FileEntity.builder().path("src/test/resources/data/testdata.txt").build();

        Mockito.when(fileRepository.getFileById(any(Integer.class))).thenReturn(Optional.of(testEntity));

        var data = fileService.getContentById(0);

        assertEquals("Example  data", data);
    }
}