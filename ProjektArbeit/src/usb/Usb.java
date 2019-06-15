package usb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;

public class Usb {
	static File[] oldListRoot = File.listRoots();

	/**
	 * @return letters[i],The USB Drive Letter (string)
	 * 
	 */
	public static String FindUsbDrive() {
		String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		File[] drives = new File[letters.length];
		boolean[] isDrive = new boolean[letters.length];

// init the file objects and the initial drive state
		for (int i = 0; i < letters.length; ++i) {
			drives[i] = new File(letters[i] + ":/");

			isDrive[i] = drives[i].canRead();
		}

		// System.out.println("FindDrive: waiting for devices...");

// loop indefinitely
		while (true) {
// check each drive
			for (int i = 0; i < letters.length; ++i) {
				boolean pluggedIn = drives[i].canRead();

// if the state has changed output a message
				if (pluggedIn != isDrive[i]) {
					if (pluggedIn)

						return letters[i];

					else
						System.out.println("Drive " + letters[i] + " has been unplugged");

					isDrive[i] = pluggedIn;
					return letters[i];

				}
			}

// wait before looping
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				/* do nothing */ }

		}
	}

	/**
	 * @return pluggedin,überprüft ob USB Stick auf eine schnittstelle ist(true)
	 *         sonst false
	 */
	public static boolean IsUsbStickInPort() {
		String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
		File[] drives = new File[letters.length];
		boolean[] isDrive = new boolean[letters.length];

// init the file objects and the initial drive state
		for (int i = 0; i < letters.length; ++i) {
			drives[i] = new File(letters[i] + ":/");

			isDrive[i] = drives[i].canRead();
		}

		System.out.println("please plug in USB stick ...");

// loop indefinitely
		while (true) {
// check each drive
			for (int i = 0; i < letters.length; ++i) {
				boolean pluggedIn = drives[i].canRead();

// if the state has changed output a message
				if (pluggedIn != isDrive[i]) {
					if (pluggedIn) {
						// Drive " + letters[i] + " has been plugged in
						System.out.println("");
						return true;
					} else
						// Drive " + letters[i] + " has been unplugged
						System.out.println("");

					isDrive[i] = pluggedIn;
					return pluggedIn;

				}
			}

// wait before looping
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				/* do nothing */ }

		}
	}

	/**
	 * Not in used Alternative Method for Is USB Stick (Dongle) in Port Check every
	 * 3 second in Main/Test Method
	 */
	public static void CheckUsb() {

		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (File.listRoots().length > oldListRoot.length) {
						System.out.println("new drive detected");
						oldListRoot = File.listRoots();
						System.out.println("drive" + oldListRoot[oldListRoot.length - 1] + " detected");

					} else if (File.listRoots().length < oldListRoot.length) {
						System.out.println(oldListRoot[oldListRoot.length - 1] + " drive removed");

						oldListRoot = File.listRoots();

					}

				}
			}
		});
		t.start();
	}

	/**
	 *
	 * @param drive ,Usb Letter Drive
	 * @return result (USB serialnumber as String) works in both Linux / Windows
	 */
	public static String getSerialNumber(String drive) {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 
	 * get USB Stick Name
	 * 
	 */
	public static String getUsbName() throws IOException {
		for (FileStore store : FileSystems.getDefault().getFileStores()) {
			System.out.format("%-20s vsn:%s\n", store, store.getAttribute("volume:vsn"));
			System.out.println(store);
			System.out.println(store.getAttribute("volume:vsn"));
			return (store.name());
		}
		return null;

	}

	/**
	 * @param letter
	 * @return Serial(SN)
	 *  alternative Method to getSerialNumber
	 */
	public static String getSerialKey(String letter) throws Exception {
		String line = null;
		String serial = null;
		Process process = Runtime.getRuntime().exec("cmd /c vol " + letter + ":");
		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		while ((line = in.readLine()) != null) {
			if (line.toLowerCase().contains("serial number")) {
				String[] strings = line.split(" ");
				serial = strings[strings.length - 1];
			}
		}
		in.close();
		return serial;
	}
}
