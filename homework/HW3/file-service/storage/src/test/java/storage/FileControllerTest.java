package storage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import storage.domains.FileEntity;
import storage.repositories.FileRepository;
import storage.services.FileService;
import storage.web.dtos.FileResponse;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FileControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private FileService fileService;

    @MockitoSpyBean
    private FileRepository fileRepository;

    @Test
    @DisplayName("Успешное добавление тестового файла")
    void uploadFile_Success() throws Exception {
        var mockFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "TEst text".getBytes());

        var mockFileEntity = FileEntity.builder()
                                       .id(1)
                                       .name("test.txt")
                                       .path("/location/content/test.txt")
                                       .hash("qwertyuiop")
                                       .build();

        doReturn(mockFileEntity).when(fileService).saveFile(any(MultipartFile.class));

        var response = mockMvc.perform(multipart("/api/files/upload").file(mockFile))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        var fileResponse = objectMapper.readValue(response, FileResponse.class);
        assertAll(
                () -> assertEquals(fileResponse.hash(), mockFileEntity.getHash()),
                () -> assertEquals(fileResponse.id(), mockFileEntity.getId()),
                () -> assertEquals(fileResponse.name(), mockFileEntity.getName()),
                () -> assertEquals(fileResponse.path(), mockFileEntity.getPath())
        );
    }

    @Test
    @DisplayName("Успешное получение содержания файла")
    void getContent_Success() throws Exception {
        var mockFileEntity = FileEntity.builder().path("src/test/resources/data/testdata.txt").build();

        when(fileRepository.getFileById(any(Integer.class))).thenReturn(Optional.of(mockFileEntity));

        var response = mockMvc.perform(get("/api/files/0/content"))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        assertEquals("Example  data", response);
    }

    @Test
    @DisplayName("Успешное получение одинаковых файлов")
    void getSameCount_Success() throws Exception {
        doReturn(Optional.of(FileEntity.builder().hash("abc").build())).when(fileRepository)
                                                                       .getFileById(any(Integer.class));
        doReturn(List.of()).when(fileRepository).findAllByHash(any(String.class));

        var response = mockMvc.perform(get("/api/files/0/same"))
                              .andExpect(status().isOk())
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
        var resp = objectMapper.readValue(response, Integer.class);
        assertEquals(0, resp);
    }
}