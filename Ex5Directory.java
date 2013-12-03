package ex5;

import java.io.File;
import java.io.FileNotFoundException;

public class Ex5Directory extends Ex5DirectoryEntry {

	private final String prefix;

	/**
	 * 
	 * @param filename
	 *            The file that will be searched when the searchForString method
	 *            is called.
	 * @param prefix
	 *            The prefix that should be outputted before the searchForString
	 *            output.
	 * @throws FileNotFoundException
	 *             If the file does not exist.
	 */
	Ex5Directory(String filename, String prefix) throws FileNotFoundException {
		super(filename);
		this.prefix = prefix;
	}

	/**
	 * 
	 * @return Returns the prefix that is outputted before the searchForString
	 *         output.
	 */
	public String getPrefix() {
		return this.prefix;
	}

	@Override
	public void searchForString(String searchTerm) {
		// Get a list of files and directories in the directory
		File directory = new File(this.getFilename());
		File[] fileList = directory.listFiles();

		// Create variables that will hold temporary Ex5File and Ex5Directory
		// classes
		Ex5File tempFile;
		Ex5Directory tempDirectory;

		// Deal with every file and directory by creating new Ex5 classes
		// and invoking their searchForString methods
		for (int i = 0; i < fileList.length; i++) {
			try {
				if (fileList[i].isFile()) {
					// Re-using the Ex5File class avoids code redundancy but
					// makes it a lot harder to include the directory/file:
					// prefix in the output
					tempFile = new Ex5File(fileList[i].getAbsolutePath());
					tempFile.setPrefix(this.getPrefix() + fileList[i].getName());
					tempFile.searchForString(searchTerm);
				} else {
					tempDirectory = new Ex5Directory(
							fileList[i].getAbsolutePath(), this.getPrefix()
									+ fileList[i].getName() + "/");
					tempDirectory.searchForString(searchTerm);
				}
			} catch (FileNotFoundException e) {
				// File/Directory was not found, print an error explaining why
				System.out.println(e.getMessage());
			}
		}
	}

}
