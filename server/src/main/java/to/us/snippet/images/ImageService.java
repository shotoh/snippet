package to.us.snippet.images;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import to.us.snippet.exceptions.InvalidRequestException;

@Service
public class ImageService {
	private final String imagePath;

	@Autowired
	public ImageService(@Value("${IMAGE_PATH:}") String imagePath) {
		this.imagePath = imagePath;
	}

	public String saveImage(MultipartFile file) {
		if (file.isEmpty()) {
			throw new InvalidRequestException("file", "File is empty");
		}
		String type = file.getContentType();
		String originalName = file.getOriginalFilename();
		if (type == null || !type.startsWith("image")) {
			throw new InvalidRequestException("file", "File is not an image");
		}
		if (originalName == null) {
			throw new InvalidRequestException("file", "Invalid file name");
		}
		Path uploadPath = Paths.get(imagePath);
		if (!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(StringUtils.cleanPath(originalName));
		Path path = Paths.get(imagePath + fileName);
		try {
			Files.write(path, file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return fileName;
	}

	public void deleteImage(String pathName) {
		if (pathName == null) return;
		Path path = Paths.get(imagePath + pathName);
		try {
			Files.delete(path);
		} catch (NoSuchFileException ignored) {
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
