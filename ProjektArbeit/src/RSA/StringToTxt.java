package RSA;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Save string into Txt file
 * 
 */

public class StringToTxt {
	public static void SaveToTxt(String text) { 
		 try {
	            Files.write(Paths.get("my-file.txt"), text.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        @SuppressWarnings("unused")
			File file = new File("my-file.txt"); 
	        System.out.println("Key/txt saved successfully to my-file.txt");
	}
	/**
	 * 
	 *  load/read Text from Txt file 
	 *  @return content.
	 */
	public static String ReadFromTxt() throws IOException { 
		 String content = null;
		    File file = new File("my-file.txt"); 
		    FileReader reader = null;
		    try {
		        reader = new FileReader(file);
		        char[] chars = new char[(int) file.length()];
		        reader.read(chars);
		        content = new String(chars);
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if(reader != null){
		            reader.close();
		        }
		    }
		    return content;
	}
	
    public static void main(String[] args) throws IOException {
    	SaveToTxt("I am Hamodi");       
    
          System.out.println(ReadFromTxt()); 
        
    }
}