package ex5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ex5File extends Ex5DirectoryEntry {

	private String prefix;

	/**
	 * 
	 * @param filename
	 *            The file that should be read when the file is searched.
	 * @throws FileNotFoundException
	 *             If the file does not exist
	 */
	Ex5File(String filename) throws FileNotFoundException {
		super(filename);
		this.prefix = "";
	}

	/**
	 * Sets the prefix that should be outputted before the line that contains
	 * the search term.
	 * 
	 * @param newPrefix
	 *            The new prefix.
	 */
	public void setPrefix(String newPrefix) {
		this.prefix = newPrefix;
	}

	/**
	 * Gets the prefix that should be outputted before the line that contains
	 * the search term.
	 * 
	 * @return Returns the prefix currently stored by the class.
	 */
	public String getPrefix() {
		return this.prefix;
	}

	@Override
	public void searchForString(String searchTerm) throws FileNotFoundException {
		File searchFile = new File(this.getFilename());

		// Open a stream to the file
		BufferedReader fileStream;
		fileStream = new BufferedReader(new FileReader(searchFile));

		String line;

		try {
			for (int i = 1; (line = fileStream.readLine()) != null; i++) {
				// Search string for searchTerm
				// Print the lines that contain searchTerm
				if (((line.contains(searchTerm)) && !invertedMatch)
						|| ((!line.contains(searchTerm)) && invertedMatch)) {
					System.out.print(this.getPrefix());

					// Only print line numbers if the option was set
					if (lineNumbers) {
						System.out.print("(" + i + ")");
					}

					// We should only print the colon if there is a line number
					// or a directory prefix
					if (lineNumbers || (this.getPrefix() != "")) {
						System.out.print(":");
					}

					String remaining = line;
					while (remaining.indexOf(searchTerm, 0) != -1) {
						// Print prefix of string
						System.out.print(remaining.substring(0,
								remaining.indexOf(searchTerm)));

						// Only print highlighting if the option is set
						if (highlight) {
							System.out.print("###");
						}
						// Print the searchTerm
						System.out.print(searchTerm);
						if (highlight) {
							System.out.print("###");
						}

						// Modify remaining to avoid printing the same data
						// again
						remaining = remaining.substring(remaining
								.indexOf(searchTerm) + searchTerm.length());
					}

					// Print the final remaining data of the string
					System.out.println(remaining);
				}
			}
		} catch (IOException e) {
			// Stop reading the file
		}

		// Close the file stream
		try {
			if (fileStream != null) {
				fileStream.close();
			}
		} catch (IOException e) {
			// Nothing we can do, just return
			return;
		}
	}

}
