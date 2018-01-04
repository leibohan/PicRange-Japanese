package pintu.handle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HandleImage {
	
	public void deleteAll() {
		String pathName = this.getClass().getResource("/images/subImages/").getFile();
		File file = new File(pathName);
		if (file.isDirectory()) {
			String []fileArr = file.list();
			System.out.println(fileArr.length);
			if (fileArr.length > 0) {
				for (int i = 0; i < fileArr.length; i++) {
					String fileName = this.getClass().getResource("/images/subImages/").getFile() + fileArr[i];
					File f = new File(fileName);
					if (f.isFile()) 
						f.delete();
					System.out.println(fileName);
				}
			}
		}
		System.out.println(pathName);
	}
	
	public void cutImage(int size, int rowSize, int colSize, String prename, String imageName) {
		String pathName = this.getClass().getResource("/images/").getFile();
		String fileName = pathName + imageName;
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedImage bi = ImageIO.read(fis);
			
			fis.close();
			
			for (int r = 0; r < rowSize; r++) {
				for (int c = 0; c < colSize; c++) {
					String smallFileName = prename + "_" + (r * rowSize + c + 1) + ".jpg";
					String pathAndName = this.getClass().getResource("/images/subImages/").getFile() + smallFileName;
					BufferedImage smallImage = bi.getSubimage(c * size, r * size, size, size);
					FileOutputStream fos =new FileOutputStream(pathAndName);
					ImageIO.write(smallImage, "jpg", fos);
					fos.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HandleImage hi = new HandleImage();
		hi.cutImage(100, 5, 5, "12345", "1.jpg");

	}

}
