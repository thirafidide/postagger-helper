import java.util.*;
import java.io.*;

public class POSTaggerHelper {

	public static void getAllTag(File corpus, String namaFileOutput) throws FileNotFoundException {

		Scanner scanner = new Scanner(corpus);
		PrintWriter writer = new PrintWriter(namaFileOutput);

		List<String> tags = new ArrayList<String>();

		int lineProcessed = 0;
		while (scanner.hasNextLine()) {
			String inputLine = scanner.nextLine();
			// System.out.println("process (" + ++lineProcessed + "):" + inputLine);
			System.out.printf("\r%s line processed", ++lineProcessed);

			if (inputLine.equals("	") || inputLine.equals("")) 
				continue;

			String[] inputLineArray = inputLine.split("	");
			String tag = inputLineArray[1].toUpperCase();

			if(!tags.contains(tag))
				tags.add(tag);
		}

		for (String tag : tags)
			writer.print(tag + ", ");

		scanner.close();
		writer.close();
	}

	public static void olahFileCorpus(File corpus, String namaFileOutput) throws FileNotFoundException{

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
			String kata = inputLineArray[0].replace(" ", "-").toLowerCase();
			String tag = inputLineArray[1].toUpperCase();

			writer.print(kata + "_" + tag + " ");
		}

		scanner.close();
		writer.close();

	}

	public static void splitCorpus(File corpus, int totalTrainingPerCat, String namaPrefixFileOutput) throws Exception {

		Scanner scanner = new Scanner(corpus);
		// PrintWriter writerTraining = new PrintWriter(namaFileTraining);
		// PrintWriter writerTesting = new PrintWriter(namaFileTesting);

		List<String> corpusLines = new ArrayList<String>();
		// int totalCat = Math.floor(corpusLines / (double) totalTrainingPerCat

		while (scanner.hasNextLine()) 
			corpusLines.add(scanner.nextLine());

		// List<String>[] corpusCat = new ArrayList<String>[)];

		// int currenCat = 0;
		// Random rng = new Random();
		// while (corpusLines.size() > 0) {
		// 	int randomIndex = rng.nextInt(corpusLines.size());
		// 	String corpusLine = corpusLines.remove(randomIndex);

		// 	corpusCat[currenCat].add(corpusLine);
		// 	if (corpusCat[currenCat].size() == totalTrainingPerCat)
		// 		currenCat++;
		// }
		Collections.shuffle(corpusLines);
		System.out.println("Corpus shuffled, building data ...");

		List<String> corpusLinesBackup = new ArrayList<String>(corpusLines);

		// Printwriter exp 1
		int totalCat = (int) Math.ceil(corpusLines.size() / (double) totalTrainingPerCat);
		ArrayList<PrintWriter> outputWriterExp1 = new ArrayList<PrintWriter>();
		for (int i = 0; i < totalCat; i++) {
			PrintWriter writer;

			if (i == 0)
				writer = new PrintWriter("data/" + namaPrefixFileOutput + "-testing-exp1");
			else
				writer = new PrintWriter("data/" + namaPrefixFileOutput + "-training-exp1-" + i*totalTrainingPerCat);

			outputWriterExp1.add(writer);
		}

		// Printwriter exp 2
		int totalCat2 = totalCat - 1;
		ArrayList<PrintWriter> outputWriterTestingExp2 = new ArrayList<PrintWriter>();
		ArrayList<PrintWriter> outputWriterTrainingExp2 = new ArrayList<PrintWriter>();
		for (int i = 1; i <= totalCat2; i++) {
			PrintWriter writerTraining;
			PrintWriter writerTesting;

			writerTesting = new PrintWriter("data/" + namaPrefixFileOutput + "-testing-exp2-" + i*totalTrainingPerCat);
			writerTraining = new PrintWriter("data/" + namaPrefixFileOutput + "-training-exp2-" + i*totalTrainingPerCat);

			outputWriterTestingExp2.add(writerTesting);
			outputWriterTrainingExp2.add(writerTraining);
		}

		int totalProcessed = 0;
		while(corpusLines.size() > 0) {
			String corpusLine = corpusLines.remove(0);

			if (totalProcessed < totalTrainingPerCat)
				outputWriterExp1.get(0).println(corpusLine);
			else 
				for (int i = 1; i < totalCat; i++) 
					if (i*totalTrainingPerCat > totalProcessed - totalTrainingPerCat)
						outputWriterExp1.get(i).println(corpusLine);

			for (int i = 0; i < totalCat2; i++)
				if ((i+1)*totalTrainingPerCat < totalProcessed && totalProcessed < (i+2)*totalTrainingPerCat)
					outputWriterTestingExp2.get(i).println(corpusLine);
				else
					outputWriterTrainingExp2.get(i).println(corpusLine);

			System.out.printf("\r%s line processed", ++totalProcessed);
		}

		scanner.close();
		for (PrintWriter writer : outputWriterExp1)
			writer.close();
		for (PrintWriter writer : outputWriterTestingExp2)
			writer.close();
		for (PrintWriter writer : outputWriterTrainingExp2)
			writer.close();
		// writerTraining.close();
		// writerTesting.close();
	}

	public static void main(String[] argv) throws Exception {

		String command = argv[0];

		switch(command) {
			case("--get-tags") :

				String namaFileInput1 = argv[1];
				String namaFileOutput1 = argv[2];
		
				File input1 = new File(namaFileInput1);
		
				POSTaggerHelper.getAllTag(input1, namaFileOutput1);

				break;

			case("--corpus-process") :

				String namaFileInput = argv[1];
				String namaFileOutput = argv[2];
		
				File input = new File(namaFileInput);
		
				POSTaggerHelper.olahFileCorpus(input, namaFileOutput);

				break;

			case("--get-training-testing") :

				String namaFileCorpus = argv[1];
				String namaPrefixFileOutput = argv[2];
				int totalTrainingLine = Integer.parseInt(argv[3]);

				POSTaggerHelper.splitCorpus(new File(namaFileCorpus), totalTrainingLine, namaPrefixFileOutput);

				break;

			default :
				System.out.println("unknown command");
				break;
		}
	}
}