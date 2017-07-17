package com.empire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ResultHelper {

	private String victoryResultPath;
	private String tieResultPath;

	public ResultHelper(String victoryResultPath, String tieResultPath) {
		this.victoryResultPath = victoryResultPath;
		this.tieResultPath = tieResultPath;

		File v_resultFile = new File(this.victoryResultPath);
		if (!v_resultFile.exists()) {
			try {
				v_resultFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			resetFile(v_resultFile);
		}

		File t_resultFile = new File(this.tieResultPath);
		if (!t_resultFile.exists()) {
			try {
				t_resultFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			resetFile(t_resultFile);
		}
	}

	public void resetFile(File file) {
		try (FileWriter fw = new FileWriter(file.getAbsolutePath());
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.print("");
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getRandomResult(Random random, boolean tie, int difference) {
		String result = "Error!";

		if (tie) {
			try (FileReader fr = new FileReader(tieResultPath); BufferedReader br = new BufferedReader(fr);) {

				int max = Integer.parseInt(String.valueOf(((char) br.read())));
				int resultIndex = random.nextInt(max) + 1; // TODO: add difference to the result so that worse losses are generated by bigger differences between rolls
														  // Not enough possible results currently
				br.readLine();
				br.readLine(); // TODO: Remove second br.readLine() call once there are enough results to add difference

				for (int i = 0; i < resultIndex; i++) {
					result = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		} else {

			try (FileReader fr = new FileReader(victoryResultPath); BufferedReader br = new BufferedReader(fr);) {

				int max = Integer.parseInt(String.valueOf(((char) br.read())));
				int resultIndex = random.nextInt(max) + 1; // TODO: add difference as above
				br.readLine();
				br.readLine(); // TODO: Remove as above

				for (int i = 0; i < resultIndex; i++) {
					result = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
}
