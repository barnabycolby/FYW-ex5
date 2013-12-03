package ex5;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class Ex5DirectoryEntry {
	// Internal fields
	String filename;

	// Option variables
	protected static boolean lineNumbers = false;
	protected static boolean highlight = false;
	protected static boolean invertedMatch = false;

	/**
	 * 
	 * @param filename
	 *            The filename of the file/directory that is to be searched.
	 * @throws FileNotFoundException
	 *             Thrown if the file does not exist.
	 */
	public Ex5DirectoryEntry(String filename) throws FileNotFoundException {
		// Check whether the file/directory exists
		File file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}

		this.filename = filename;
	}

	/**
	 * A factory method that determines whether the provided filename is a file
	 * or directory and returns the appropriate class.
	 * 
	 * @param fileName
	 *            The filename of the file/directory.
	 * @return Returns an Ex5File class constructed with the fileName argument
	 *         if it described a file or an Ex5Directory class constructed with
	 *         the fileName argument if it described a directory.
	 * @throws FileNotFoundException
	 *             If the file/directory described by fileName could not be
	 *             found.
	 */
	public static Ex5DirectoryEntry createFromFilename(String fileName)
			throws FileNotFoundException {
		// Create a File class using fileName
		File file = new File(fileName);

		// Query file to determine whether fileName refers to a file or a
		// directory
		if (file.isFile()) {
			// Create an Ex5File class and return it
			return new Ex5File(fileName);
		} else if (file.isDirectory()) {
			// Create an Ex5Directory class and return it
			return new Ex5Directory(fileName, "");
		} else {
			// File doesn't exist or cannot be found, throw a
			// FileNotFoundException
			throw new FileNotFoundException("File/Directory " + fileName
					+ " could not be found.");
		}
	}

	/**
	 * 
	 * @return Returns the string contained by the internal filename variable
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * An abstract method that should search the file/directory that the class
	 * it was called from was constructed with, and print out the lines
	 * containing the search term (if the invert match option has not been set).
	 * The output should also take into account any other options which may
	 * affect the output to be printed.
	 * 
	 * @param searchTerm
	 *            The search term to look in the file/directory for.
	 * @throws FileNotFoundException
	 *             Thrown if the file cannot be found.
	 */
	public abstract void searchForString(String searchTerm)
			throws FileNotFoundException;

	private static void printUsageInstructions() {
		System.out.println("USAGE:");
		System.out
				.println("======================================================");
		System.out.println("Ex5DirectoryEntry options searchTerm filename/s");
		System.out.println();
		System.out.println("Options");
		System.out
				.println("------------------------------------------------------");
		System.out
				.println("-h    Highlight the searchterm by surrounding with #'s");
		System.out.println();
		System.out.println("-help (--help) Print out these instructions");
		System.out.println();
		System.out
				.println("-i    Invert search, show lines that do not contain");
		System.out.println("      the search term");
		System.out.println();
		System.out.println("-l    Show line numbers in output");
	}

	/**
	 * The main function
	 * 
	 * @param args
	 *            The command line arguments
	 * @throws FileNotFoundException
	 *             If the filename provided as an argument to the command line
	 *             describes a file which cannot be read.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// EXTRA FEATURES:
		// Searching multiple files or directories at once
		// Feature selection by utilizing command line arguments
		// Line numbers (-l)
		// Highlighted search term (highlighted with three #'s either side) (-h)
		// Inverted line matching (-i)
		// Print out usage instructions (-help) (--help)

		// Check that the correct number or arguments was supplied
		if (args.length < 2) {
			// Print usage instructions and return;
			printUsageInstructions();
			return;
		}

		int i;

		// For each option...
		optionLoop: for (i = 0; i < (args.length - 2); i++) {
			// Work out what the option was
			switch (args[i]) {
			case "-h":
				// Set the variable indicating that the search term should be
				// highlighted in the output
				highlight = true;
				break;
			case "-help":
			case "--help":
				printUsageInstructions();
				return;
			case "-i":
				// Set the variable indicating that the line matching should be
				// inverted
				invertedMatch = true;
				break;
			case "-l":
				// Set the variable indicating that line numbers should be
				// outputted
				lineNumbers = true;
				break;
			default:
				// Argument was not recognized it must be a filename or an
				// invalid argument, continue to next section
				break optionLoop;
			}
		}

		// The first argument should be the searchTerm
		String searchTerm = args[i];

		// A variable to hold the directoryEntry
		Ex5DirectoryEntry directoryEntry;

		// Iterate through the files/directories provided at the command line
		for (i += 1; i < args.length; i++) {
			directoryEntry = createFromFilename(args[i]);
			directoryEntry.searchForString(searchTerm);

			System.out.println();
		}
	}
}
