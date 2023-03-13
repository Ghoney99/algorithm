import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;

class Sim_Name implements Comparable<Sim_Name> {
	double sim;
	String name;
	TreeSet<Integer> ts;

	public Sim_Name() {
	}

	public Sim_Name(double sim, String name, TreeSet<Integer> ts) {
		this.sim = sim;
		this.name = name;
		this.ts = ts;
	}

	public int compareTo(Sim_Name target) {
		return this.sim <= target.sim ? 1 : -1;
	}

}

public class HW2 {
	static double calcsimilar(String key, TreeSet<Integer> ts, TreeSet<Integer> origints) {
		TreeSet<Integer> tempitem = new TreeSet<Integer>();
		double similar = 0;
		tempitem = (TreeSet<Integer>) ts.clone();
		similar = tempitem.size() + origints.size();
		tempitem.retainAll(origints);
		similar = tempitem.size() / (similar - tempitem.size());
		return similar;
	}

	static void counting(LinkedList<Integer> ll, int itemnum) {
		Collections.sort(ll);
		int[][] counting = new int[ll.peekLast()+1][2];
		for(int i=0;i<counting.length;i++)
			counting[i][0]=i;


		while (!ll.isEmpty()) {
			int itnum =ll.poll();
			counting[itnum][0]=itnum;
			counting[itnum][1]++;
			}

		for (int i = 0; i < counting.length - 1; i++) {
			int select = i;
			
			for (int j = i + 1; j < counting.length; j++) {
				if (counting[select][1] < counting[j][1])
					select = j;
				}
			int tmp1 = counting[i][0];
			counting[i][0] = counting[select][0];
			counting[select][0] = tmp1;
			int tmp2 = counting[i][1];
			counting[i][1] = counting[select][1];
			counting[select][1] = tmp2;
			
		}
		for (int i = 0; i < itemnum; i++)
			System.out.print(counting[i][0]+"("+counting[i][1]+") ");
	}
	
	public static void main(String[] args) {
		String filename;
		String username;
		int usernum;
		int itemnum;
		TreeSet<Integer> itemtree = new TreeSet<>();

		System.out.print("파일 이름, 사용자 이름, 사용자 수, 항목 수?");
		String str = new Scanner(System.in).nextLine();

		StringTokenizer st = new StringTokenizer(str);
		filename = st.nextToken();
		username = st.nextToken();
		usernum = Integer.valueOf(st.nextToken());
		itemnum = Integer.valueOf(st.nextToken());

		File file = new File(filename);
		try {
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				if (line.contains(username+" ")) {
					st = new StringTokenizer(line);
					username = st.nextToken();
					while (st.hasMoreTokens())
						itemtree.add(Integer.valueOf(st.nextToken()));
				}
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}
		PriorityQueue<Sim_Name> pq = new PriorityQueue<Sim_Name>(usernum + 1, Collections.reverseOrder());

		try {
			FileReader filereader2 = new FileReader(file);
			BufferedReader bufReader2 = new BufferedReader(filereader2);
			String line = "";
			while ((line = bufReader2.readLine()) != null) {

				st = new StringTokenizer(line);

				String key = st.nextToken();
				TreeSet<Integer> its = new TreeSet<>();
				double sim = 0;
				if (key.equals(username)) {
				} else {
					while (st.hasMoreTokens())
						its.add(Integer.valueOf(st.nextToken()));
					sim = calcsimilar(key, its, itemtree);
					if (pq.size() == usernum + 1)
						pq.poll();
					pq.add(new Sim_Name(sim, key, its));
				}
			}
			bufReader2.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			System.out.println(e);
		}
		pq.poll();
		LinkedList<Integer> ll = new LinkedList<Integer>();
		while (!pq.isEmpty()) {
			Sim_Name temp = pq.poll();
			temp.ts.removeAll(itemtree);
			ll.addAll(temp.ts);
		}
		counting(ll, itemnum);
	}
}
