package usb;

public class Usbtest {

	/**
	 * USB Class Test
	 * 
	 */
	public static void main(String[] args) throws Exception {

		/**
		 * if (Usb.IsUsbStickInPort()) System.out.println("USB Stick has been plugged
		 * in"); System.out.println("Check/Insert your USB Stick");
		 */

		String d = Usb.FindUsbDrive();
		System.out.println("USB stick has been in Drive " + d + " plugged in");
		String sn = Usb.getSerialNumber(d);

		javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, sn, "Serial Number of USB stick :",
				javax.swing.JOptionPane.DEFAULT_OPTION);
		System.out.println("SN : " + sn);
		System.out.println("USB Stick Serial Number 2 is: "+Usb.getSerialKey(d));
		

	}
}