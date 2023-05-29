package utils;

import entity.Student;
import gui.StudentCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
/**
 * Утилита PhotoUtils предоставляет методы для работы с фотографиями студентов.
 */
public class PhotoUtils {
	private static final String PHOTO_DIR = "photos";
	private static PhotoUtils instance;

	private PhotoUtils() {} // Конструктор скрыт, чтобы нельзя было создать объекты этого класса извне.
	/**
	 * Получает экземпляр класса PhotoUtils.
	 *
	 * @return экземпляр класса PhotoUtils
	 */
	public static PhotoUtils getInstance() {
		if (instance == null) {
			instance = new PhotoUtils();
		}
		return instance;
	}
	/**
	 * Сохраняет фотографию студента в указанном файле.
	 *
	 * @param student    объект Student
	 * @param photoFile  файл фотографии
	 * @throws IOException если произошла ошибка ввода-вывода
	 */
	public void savePhoto(Student student, File photoFile) throws IOException {
		File photosDirectory = checkPhotosDirectory();
		if (photosDirectory != null) {
			String photoFileName = student.getId() + "_" + student.getLastName() + ".jpg";
			File destinationFile = new File(photosDirectory, photoFileName);
			Files.copy(photoFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			student.setPhotoPath(new File(photoFileName));
		}
	}
	/**
	 * Загружает фотографию студента и возвращает объект ImageIcon.
	 *
	 * @param student объект Student
	 * @return объект ImageIcon с загруженной фотографией студента, либо null, если фотография не найдена
	 */
	public ImageIcon loadPhoto(Student student) {
		File photosDirectory = checkPhotosDirectory();
		File photoPath = student.getPhotoPath();
		if(student.getPhotoPath()!=null) {
			File destinationFile = new File(photosDirectory, photoPath.getPath());
			if(destinationFile.exists()) {
				return new ImageIcon(destinationFile.getPath());
			}
		}
		return null;
	}
	/**
	 * Проверяет наличие директории для хранения фотографий и возвращает соответствующий объект File.
	 * Если директория отсутствует, она будет создана.
	 *
	 * @return объект File для директории фотографий, либо null, если создание директории не удалось
	 */
	public  File checkPhotosDirectory() {
		String jarPath = PhotoUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String jarDirectory = new File(jarPath).getParent();
		String photosDirectoryPath = jarDirectory + File.separator + PHOTO_DIR;

		File photosDirectory = new File(photosDirectoryPath);
		if (!photosDirectory.exists()) {
			if (photosDirectory.mkdir()) {
				System.out.println("Папка 'photos' создана.");
			} else {
				System.out.println("Не удалось создать папку 'photos'.");
				return null;
			}
		}
		return photosDirectory;
	}
	/**
	 * Загружает иконку из файла с указанным именем и возвращает объект ImageIcon.
	 *
	 * @param fileName имя файла с иконкой
	 * @return объект ImageIcon с загруженной иконкой, либо null, если загрузка не удалась
	 */
	public ImageIcon loadImageIconFromProperties(String fileName){
		ClassLoader classLoader = PhotoUtils.class.getClassLoader();
		java.net.URL iconURL = classLoader.getResource(fileName);
		if (iconURL != null) {
			return new ImageIcon(iconURL);
		} else {
			System.err.println("Не удалось загрузить иконку: ");
			return null;
		}
	}
}

