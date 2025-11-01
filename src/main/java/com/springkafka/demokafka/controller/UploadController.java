package com.springkafka.demokafka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 9/21/2025
 * @Time: 11:12 PM
 */

@RestController
@RequestMapping("/api/upload")
@CrossOrigin("http://localhost:5173")
public class UploadController {
    private final String UPLOAD_DIR = "uploads";

//    @PostMapping("/chunk")
//    public ResponseEntity<String> uploadChunk(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("uploadId") String uploadId,
//            @RequestParam("chunkIndex") int chunkIndex) throws IOException {
//
//        File dir = new File(UPLOAD_DIR, uploadId);
//        if (!dir.exists()) dir.mkdirs();
//
//        File chunkFile = new File(dir, chunkIndex + ".part");
//        // copy trực tiếp stream tránh lỗi temp bị xóa
//        Files.copy(file.getInputStream(), chunkFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        return ResponseEntity.ok("Chunk " + chunkIndex + " uploaded");
//    }
//
//    @PostMapping("/merge")
//    public ResponseEntity<?> mergeChunks(
//            @RequestParam("uploadId") String uploadId,
//            @RequestParam("fileName") String fileName,
//            @RequestParam("totalChunks") int totalChunks) {
//        try {
//            Path uploadDir = Paths.get(UPLOAD_DIR, uploadId);
//            Path mergedFile = Paths.get(UPLOAD_DIR, fileName);
//
//            if (Files.exists(mergedFile)) {
//                Files.delete(mergedFile);
//            }
//
//            try (OutputStream outputStream = Files.newOutputStream(
//                    mergedFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
//                for (int i = 0; i < totalChunks; i++) {
//                    Path chunkPath = uploadDir.resolve(i + ".part");
//                    Files.copy(chunkPath, outputStream);
//                }
//            }
//
//            // cleanup
//            for (int i = 0; i < totalChunks; i++) {
//                Files.deleteIfExists(uploadDir.resolve(i + ".part"));
//            }
//            Files.deleteIfExists(uploadDir);
//
//            return ResponseEntity.ok("✅ File merged successfully: " + fileName);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("❌ Error: " + e.getMessage());
//        }
//    }

    // Thư mục lưu file upload
    @PostMapping("/upload-normal")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chưa chọn file để upload!");
        }

        try {
            // Tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Lấy tên file gốc
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

            // Tạo đường dẫn lưu file
            Path path = Paths.get(UPLOAD_DIR + originalFilename);

            // Lưu file
            Files.write(path, file.getBytes());

            return ResponseEntity.ok("Upload thành công: " + originalFilename);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi upload file: " + e.getMessage());
        }
    }

    // Giữ thông tin chunk đã upload để resume
    private final Set<String> uploadedChunks = new HashSet<>();
    /** Upload chunk trực tiếp vào file đích */
    @PostMapping("/chunk")
    public ResponseEntity<String> uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploadId") String uploadId,
            @RequestParam("chunkIndex") int chunkIndex,
            @RequestParam("chunkSize") int chunkSize) {

        try {
            Path dir = Paths.get(UPLOAD_DIR);
            if (!Files.exists(dir)) Files.createDirectories(dir);

            Path filePath = dir.resolve(uploadId + ".tmp");

            try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "rw")) {
                long offset = (long) chunkIndex * chunkSize;
                raf.seek(offset);
                raf.write(file.getBytes());
            }

            uploadedChunks.add(uploadId + "-" + chunkIndex);

            return ResponseEntity.ok("Chunk " + chunkIndex + " uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload chunk lỗi: " + e.getMessage());
        }
    }

    /** Kiểm tra chunk đã upload để resume */
    @GetMapping("/status")
    public ResponseEntity<Set<Integer>> getUploadedChunks(@RequestParam("uploadId") String uploadId) {
        Set<Integer> chunks = new HashSet<>();
        uploadedChunks.forEach(s -> {
            if (s.startsWith(uploadId + "-")) {
                chunks.add(Integer.parseInt(s.split("-")[1]));
            }
        });
        return ResponseEntity.ok(chunks);
    }

    /** Hoàn tất upload, rename file */
    @PostMapping("/finish")
    public ResponseEntity<String> finishUpload(@RequestParam("uploadId") String uploadId,
                                               @RequestParam("fileName") String fileName) {
        try {
            Path tmpFile = Paths.get(UPLOAD_DIR, uploadId + ".tmp");
            Path finalFile = Paths.get(UPLOAD_DIR, fileName);
            Files.move(tmpFile, finalFile, StandardCopyOption.REPLACE_EXISTING);

            // Xóa thông tin chunk đã upload
            uploadedChunks.removeIf(s -> s.startsWith(uploadId + "-"));

            return ResponseEntity.ok("✅ File upload hoàn tất: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Finish upload lỗi: " + e.getMessage());
        }
    }
}
