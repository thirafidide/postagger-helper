import java.util.*;
import java.io.*;

public class POSTaggerHelper {

	public void olahFileCorpus(File corpus, String namaFileOutput) throws FileNotFoundException{

		Scanner scanner = new Scanner(corpus);
		PrintWriter writer = new PrintWriter(namaFileOutput);

		int lineProcessed = 0;
		while (scanner.hasNextLine()) {
			String inputLine = scanner.nextLine();
			// System.out.println("process (" + ++lineProcessed + "):" + inputLine);
			System.out.printf("\r%s line processed", ++lineProcessed);

			if (inputLine.equals("	") || inputLine.equals("")) {
				writer.println();
				continue;
			}

			String[] inputLineArray = inputLine.split("	");
			String kata = inputLineArray[0].replace(" ", "-");
			String tag = inputLineArray[1];

			writer.print(kata + "_" + tag + " ");
		}

		scanner.close();
		writer.close();

	}

	public void splitCorpus(File corpus, int totalTrainingPerCat) throws Exception {

		Scanner scanner = new Scanner(corpus);
		// PrintWriter writerTraining = new PrintWriter(namaFileTraining);
		// PrintWriter writerTesting = new PrintWriter(namaFileTesting);

		List<String> corpusLines = new ArrayList<String>();

		while (scanner.hasNextLine()) 
			corpusLines.add(scanner.nextLine());

		int currenCat = 0;
		Random rng = new Random();
		while (corpusLines.size() > 0) {
			int randomIndex = rng.nextInt(corpusLines.size());
			String corpusLine = corpusLines.get(randomIndex);


		}

		scanner.close();
		writerTraining.close();
		writerTesting.close();
	}

	public static void main(String[] argv) throws Exception {

		String command = argv[0];

		switch(command) {
			case("--corpus-process") :

				String namaFileInput = argv[1];
				String namaFileOutput = argv[2];
		
				File input = new File(namaFileInput);
		
				POSTaggerHelper.olahFileCorpus(input, namaFileOutput);

				break;

			case("--get-training-testing") :

				String namaFileCorpus = argv[1];
				String namaFileTraining = argv[2];
				String namaFileTesting = argv[3];
				int totalTrainingLine = Integer.parseInt(argv[4]);

				POSTaggerHelper.splitCorpus(new File(namaFileCorpus), namaFileTraining, namaFileTesting, totalTrainingLine);

				break;

			default :
				System.out.println("unknown command");
				break;
		}
	}
}