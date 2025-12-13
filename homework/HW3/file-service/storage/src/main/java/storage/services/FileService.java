package storage.services;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storage.domains.FileEntity;
import storage.repositories.FileRepository;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    public void save(Path path, MultipartFile file) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
    }

    public FileEntity saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "_" + originalFilename;

        String shortPath = uploadDirectory + "/" + filename;

        Path path = Paths.get(shortPath);
        save(path, file);

        var hash = calculateFileHash(file.getBytes());

        FileEntity entity = new FileEntity();
        entity.setName(originalFilename);
        entity.setPath(shortPath);
        entity.setHash(hash);
        entity = fileRepository.save(entity);

        return entity;
    }

    public String getFileHash(Integer fileId) {
        var entity = fileRepository.getFileById(fileId);
        if (entity.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        return entity.get().getHash();
    }

    public String getContentById(Integer fileId) throws IOException {
        var entity = fileRepository.getFileById(fileId);
        if (entity.isEmpty()) {
            throw new IOException("File not found");
        }
        var path = Paths.get(entity.get().getPath());
        return FileUtil.readAsString(path.toFile());
    }

    public Optional<FileEntity> getFileById(Integer fileId) {
        return fileRepository.getFileById(fileId);
    }

    private String calculateFileHash(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content);
            return String.format("%064x", new BigInteger(1, hashBytes));
        } catch (NoSuchAlgorithmException e) {
            // В теории недостижимо
            return null;
        }
    }
}