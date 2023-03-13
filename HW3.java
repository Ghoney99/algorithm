import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

class Word_Frequency {
	String word;
	int frequency;

	Word_Frequency(String word, int frequency) {
		this.word = word;
		this.frequency = frequency;
	}

	public String toString() {
		return word + "(" + frequency + ")";

	}
}

class Word_Word_Frequency {
	String word1;
	String word2;
	int frequency;

	Word_Word_Frequency(String word1, String word2, int frequency) {
		if (word1.compareTo(word2) > 0) {
			this.word1 = word1;
			this.word2 = word2;
		} else {
			this.word1 = word2;
			this.word2 = word1;
		}
		this.frequency = frequency;
	}

	public String toString() {
		return "[" + word1 + ", " + word2 + "]" + "(" + frequency + ")";

	}
}

public class HW3 {

	public static void main(String[] args) {
		HashSet<String> stopword = new HashSet<String>();
		HashMap<String, Integer> tok_k = new HashMap<String, Integer>();
		String fileName;
		int wordNum;

		System.out.print("파일 이름, 단어 쌍의 수? ");
		String filenameWordNum = new Scanner(System.in).nextLine();
		System.out.println();

		String filenameWordNumSplit[] = filenameWordNum.split(" ");
		fileName = filenameWordNumSplit[0];
		wordNum = Integer.valueOf(filenameWordNumSplit[1]);

		File fileStopword = new File("stop.txt");
		try {
			FileReader filereader = new FileReader(fileStopword);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				stopword.add(line);
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}

		PriorityQueue<Word_Frequency> frequencyQueue = new PriorityQueue<Word_Frequency>(wordNum + 1,
				new Comparator<Word_Frequency>() {
					public int compare(Word_Frequency obj1, Word_Frequency obj2) {
						return obj1.frequency <= obj2.frequency ? -1 : +1;
					}
				});

		try {
			Scanner scan = new Scanner(new File(fileName));
			scan.useDelimiter("[.?!]");
			while (scan.hasNext()) {
				String str = scan.next();
				str = str.toLowerCase();
				str = str.replace(System.getProperty("line.separator"), "");
				String words[] = str.split(" |\\W* ");
				for (String word : words) {
					if (!stopword.contains(word) || word.equals(" ")) {
						if (tok_k.containsKey(word)) {
							tok_k.put(word, tok_k.get(word) + 1);
						} else {
							tok_k.put(word, 1);
						}
						
						
						
						
						
						
						
			
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Set<String> keyset = tok_k.keySet();
		for (String key : keyset) {
			if (frequencyQueue.size() == wordNum + 1)
				frequencyQueue.poll();
			frequencyQueue.add(new Word_Frequency(key, tok_k.get(key)));
		}
		ArrayList<Word_Frequency> tempArray = new ArrayList<Word_Frequency>();
		frequencyQueue.poll();
		for (int i = 0; i < wordNum + 1; i++)
			tempArray.add(frequencyQueue.poll());
		System.out.print("Tok-k 문자열:");
		for (int i = wordNum - 1; i >= 0; i--)
			System.out.print(" " + tempArray.get(i).toString());
	}
}
