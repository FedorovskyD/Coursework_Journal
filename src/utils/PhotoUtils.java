package utils;

import entity.Student;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PhotoUtils {
	private static final String PHOTO_DIR = "photos/";
	private static PhotoUtils instance;

	private PhotoUtils() {} // Конструктор скрыт, чтобы нельзя было создать объекты этого класса извне.

	public static PhotoUtils getInstance() {
		if (instance == null) {
			instance = new PhotoUtils();
		}
		return instance;
	}

	public void savePhoto(Student student, File photoFile) throws IOException {
		String photoPath = PHOTO_DIR + student.getId() + ".jpg";
		Files.copy(photoFile.toPath(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);
		student.setPhotoPath(photoPath);
	}

	public ImageIcon loadPhoto(Student student) {
		String photoPath = student.getPhotoPath();
		return new ImageIcon(photoPath);
	}
}

